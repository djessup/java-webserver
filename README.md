# Java Web Server 

A simple multi-threaded web server written in Java and implementing the HTTP/1.1 specification.

[![Build Status](https://travis-ci.org/djessup/java-webserver.svg?branch=master)](https://travis-ci.org/djessup/java-webserver)
[![SonarQube Coverage](https://img.shields.io/sonar/http/sonarqube.com/au.id.deejay:java-webserver/coverage.svg)](https://sonarqube.com/dashboard?id=au.id.deejay%3Ajava-webserver)
[![Quality Gate](http://sonarqube.com/api/badges/gate?key=au.id.deejay:java-webserver)](https://sonarqube.com/dashboard?id=au.id.deejay%3Ajava-webserver)

## Features

* Simple file serving
* Configurable connection thread pooling
* Keep-alive (persistent) connections
* Extensible request handlers
* Access and error logging
* Automatic directory listings
* Server info summary

## Requirements

* Java 8
* Maven 3

## Build

To build the project just run the Maven `package` goal:

```bash
mvn clean package
```

This will produce an executable JAR in the `target` directory.

## Usage

Start the server:

```bash
java -jar target/java-webserver-1.0.0.jar
```
This will start the server using the default settings: a randomly assigned available port, a connection timeout of 10 seconds, up to 20 worker threads, and a document root at `./docroot`.

With the server running you can browse the docroot by visiting `http://localhost:<port>/` or see an overview of the current configuration at `http://localhost:<port>/serverInfo`.


You can configure the port, timeout, worker threads and other settings with the corresponding option. For example, to run the server on port 80 with a document root at `/var/www/html` you would use the following:

```bash
java -jar target/java-webserver-1.0.0.jar --port 80 --docroot /var/www/html
```

To display all the options and usage information:

```bash
java -jar target/java-webserver-1.0.0.jar --help
```

## How it works

The server is designed to be extensible and easy to understand and use, and code is written to be expressive and well 
documented. 

The operation of the server can be broken down into a few key components responsible for handling client connections, 
parsing requests, and generating responses. Besides these there are various utilities to facilitate these functions. 

### Handling client connections

The centre of connection handling is the `WebServerExecutor`, which maintains the main server socket bound to the port 
the server is running on. It continuously listens for new client connections and as soon as one is made it delegates it 
to a `WebWorker`. The `WebWorkers` are queued in a thread pool, to be executed as soon as a thread becomes available.

The `WebServerExecutor` is designed to do the minimal amount of work needed to delegate a request to a worker to 
maximise throughput.
 
Each `WebWorker` is "disposable" and is only responsible for a single client connection. In some cases the connection 
might only last for a single request-response cycle, or in the case of persistent or "keep-alive" connections (the 
default for HTTP/1.1) the worker may service any number of requests. The details of how workers decide how to respond to
a request is explained further down.

### Parsing requests and generating responses

Client connections are streamed through a `RequestReader`, which reads a stream of HTTP request data into `Request` 
objects for further processing. 

To decide how to respond to a request `WebWorkers` rely on `RequestHandlers`, which are at the centre of defining the 
server's behaviour. Each handler indicates if it knows how to handle a given request, and if the handler agrees to 
handle a request it is then asked to provide a response. Since there could be any number of `RequestHandlers` 
registered, `WebWorkers` deal with handlers via the `ResponseFactory`, rather than talking to each handler directly.

For the sake of simplicity of the implementation, the default `RequestHandlers` are hand-defined in the application's
"main" class (`App`). In a commercial scenario they would instead be configured via a configuration file, similar to how
Apache HTTPD modules are configured.

After the the worker has generated a response, it streams it back to the client with the help of a `ResponseWriter`,
which is essentially the reverse of `RequestReader` in that is writes `Response` to an output stream.

This design makes implementing new server behaviours very easy, since handlers need not be concerned about either the 
client connection lifecycle, or the logistics of reading or writing HTTP messages. 

There are several out-of-the-box `Response` types for serving files (`FileResponse`), sending errors (`ErrorResponse`), 
redirects (`RedirectResponse`) and listing directory contents (`DirectoryListingResponse`). The `Response` uses a 
stream-based system for response bodies, which aid performance particularly when serving large files since they do not 
need to be read into memory and can instead be streamed directly to the client.

### Application management

The entry point to the application is `App`, which handles the reading of CLI options and uses them to create a new 
`WebServer` instance. It also creates and configures the default `RequestHandler`s (see above for rationale).
 
Superficially, `WebServer` and `WebServerExecutor` may seem similar, however `WebServer` is actually the controller for 
`WebServerExecutor`, since listening for client connections is a blocking operation and `WebServer` and is designed to 
be non-blocking so consumers can easily start and stop individual servers without worrying spawning and managing the
threads to run them in. Starting and stopping a new server is as simple as: 

```java 
WebServer server = new WebServer(port, timeout, threads, handlers);
server.start();
// do something
server.stop();
```

## Extending

The server handles requests by delegating them to various `RequestHandler`s, which are registered at startup time. The 
server behaviour can be readily extended by adding new handlers and registering them. For more information about how
handlers are used see "How it works" above.

The handler specification is simple:
```java
public interface RequestHandler {

	/**
	 * Checks if the handler is able to provide a response to a particular request.
	 *
	 * @param request the request to be handled.
	 * @return Returns true if the handler can provide a response to this request, or false if it cannot.
	 */
	boolean canHandle(Request request);

	/**
	 * Provides a response to a request.
	 *
	 * @param request the request to provide a response for
	 * @return Returns the response to the request.
	 */
	Response handle(Request request);
}
```
Implementations simply answer "yes" or "no" when asked if they know how to handle a particular request, then provide a
response.

There are several out-the-box `Response` types  like `FileResponse`, `RedirectResponse` and `ErrorResponse`, and it is 
straightforward to add others for more specific applications.

The handlers are registered in the main loop of `App` in order of priority (highest first).

### Example

Let's create a new handler that will print the current server time when someone visits `/time`.

First, we'll create a new `Response` type that outputs the current time as plain text. We could use `HttpResponse` 
directly, but this way we can keep the code cleaner and easier to test.

```java
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeResponse implements Response {

	private final String timeString;

	public CurrentTimeResponse() {
		// Convert the current time into a string
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		timeString = "The time is " + LocalDateTime.now().format(formatter);
	}

	@Override
	public HttpStatus status() {
		return HttpStatus.OK_200;
	}

	@Override
	public InputStream stream() {
		return new ByteArrayInputStream(timeString.getBytes());
	}

	@Override
	public Headers headers() {
		return new HttpHeaders(
				new HttpHeader("Content-type", "text/plain"),
				new HttpHeader("Content-length", String.valueOf(timeString.length())));
	}

	@Override
	public HttpVersion version() {
		return HttpVersion.HTTP_1_0;
	}
}
```

Now we'll create a `RequestHandler` to capture requests to `/time` and respond using our new response type:

```java
import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.response.CurrentTimeResponse;

public class CurrentTimeHandler implements RequestHandler {

	private static final String PATH = "/time";

	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET && PATH.equals(request.uri().getPath());
	}

	@Override
	public Response handle(Request request) {
		return new CurrentTimeResponse();
	}
}
```

Finally, we'll register the new handler in `App#main()`, along with the handlers already there:

```java
// Configure request handlers
RequestHandler serverInfoHandler = new ServerInfoHandler(...);
RequestHandler docrootHandler = new DocrootHandler(...);
RequestHandler timeHandler = new CurrentTimeHandler(); // our new handler

List<RequestHandler> requestHandlers = Arrays.asList(timeHandler, serverInfoHandler, docrootHandler);
```

Now if we start the server and visit `http://localhost/time` we should see something like:

```
The time is 5:35 PM
```

And that's all there to adding new server functionality. For a more full-featured example, check out `DocrootHandler`.

## Acknowledgements

* Sample content courtesy of [Twitter Bootstrap](https://getbootstrap.com), licensed under The MIT License.
* Favicon courtesy of [FamFamFam Silk icons collection](http://www.famfamfam.com/lab/icons/silk/), licensed under Creative Commons Attribution 3.0 License
* And, of course, all Maven dependencies are courtesy of their respective owners.
