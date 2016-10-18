# Java Web Server

A simple multi-threaded web server written in Java and implementing the HTTP/1.1 specification.

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
This will start the server using the default settings, with a randomly assigned available port, a connection timeout of 10 seconds, up to 20 worker threads, and a document root at `./docroot`.

With the server running you can browse the docroot by visiting `http://localhost:<port>/` or see an overview of the current configuration at `http://localhost:<port>/serverInfo`.


You can configure the port, timeout, worker threads and other settings with the corresponding option. For example, to run the server on port 80 with a document root at `/var/www/html` you would use the following:

```bash
java -jar target/java-webserver-1.0.0.jar --port 80 --docroot /var/www/html
```

To display all the options and usage information:

```bash
java -jar target/java-webserver-1.0.0.jar --help
```

## Extending

The server handles requests by delegating them to various `RequestHandler`s, which are registered at startup time. The server behaviour can be readily extended by adding new handlers and registering them.

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
Implementations simply answer "yes" or "no" when asked if they know how to handle a particular request, then provide the response.

There are several out-the-box response types  like `FileResponse`, `RedirectResponse` and `ErrorResponse`, and it is straightforward to add others for more specific applications.

The handlers are registered in the main loop of `au.id.deejay.webserver.App`, in order of priority (highest first). An example can been seen in `ServerInfoHandler`, which provides a simple summary of the server configuration and uptime when a visitor browses to `/serverInfo`

## Acknowledgements

* Sample content courtesy of [Twitter Bootstrap](https://getbootstrap.com), licensed under The MIT License.
* Favicon courtesy of [FamFamFam Silk icons collection](http://www.famfamfam.com/lab/icons/silk/), licensed under Creative Commons Attribution 3.0 License
* And, of course, all Maven dependencies are courtesy of their respective owners.