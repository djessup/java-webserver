package au.id.deejay.webserver.request;

import au.id.deejay.webserver.exception.RequestParseException;
import au.id.deejay.webserver.spi.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author David Jessup
 */
public class RequestLine {

	private HttpMethod method;
	private URI uri;
	private HttpVersion httpVersion;

	public RequestLine(HttpMethod method, URI uri, HttpVersion httpVersion) {
		this.method = method;
		this.uri = uri;
		this.httpVersion = httpVersion;
	}

	public RequestLine(String rawRequestLine) {
		parseRequestLine(rawRequestLine);
	}

	private void parseRequestLine(String rawRequestLine) {
		String[] requestLineParts = rawRequestLine.split(" ", 3);
		if (requestLineParts.length != 3) {
			throw new RequestParseException("Invalid request line, must be in the format \"Method Request-URI HTTP-Version\" as specified in RFC2616: " + rawRequestLine);
		}

		method = HttpMethod.valueOf(requestLineParts[0].toUpperCase());

		try {
			uri = new URI(requestLineParts[1]);
		} catch (URISyntaxException e) {
			throw new RequestParseException("Invalid request URI", e);
		}

		httpVersion = new HttpVersion(requestLineParts[2]);
	}

	public HttpMethod method() {
		return method;
	}

	public URI uri() {
		return uri;
	}

	public HttpVersion version() {
		return httpVersion;
	}
}
