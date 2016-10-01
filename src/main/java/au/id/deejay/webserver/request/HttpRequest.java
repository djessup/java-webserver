package au.id.deejay.webserver.request;

import au.id.deejay.webserver.spi.Headers;
import au.id.deejay.webserver.spi.HttpMethod;
import au.id.deejay.webserver.spi.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;

/**
 * @author David Jessup
 */
public class HttpRequest implements Request {

	private final RequestLine requestLine;
	private Headers headers;
	private String body;

	public HttpRequest(Socket socket) throws IOException {
		BufferedReader clientReader = new BufferedReader(
				new InputStreamReader(
						socket.getInputStream()
				)
		);

		// First line is the request line
		requestLine = new RequestLine(clientReader.readLine().trim());
	}

	@Override
	public String body() {
		return body;
	}

	@Override
	public Headers headers() {
		return headers;
	}

	@Override
	public HttpMethod method() {
		return requestLine.method();
	}

	@Override
	public URI uri() {
		return requestLine.uri();
	}

	@Override
	public HttpVersion version() {
		return requestLine.version();
	}
}


/*

 Request-Line   = Method SP Request-URI SP HTTP-Version CRLF

        Request       = Request-Line              ; Section 5.1
                        *(( general-header        ; Section 4.5
                         | request-header         ; Section 5.3
                         | entity-header ) CRLF)  ; Section 7.1
                        CRLF
                        [ message-body ]          ; Section 4.3


       extension-method = token
 */