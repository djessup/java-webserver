package au.id.deejay.webserver.request;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpVersion;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Represents the request line portion of an HTTP request
 *
 * @author David Jessup
 */
public class RequestLine {

	private HttpMethod method;
	private URI uri;
	private HttpVersion httpVersion;

	/**
	 * Constructs a RequestLine using explicit values
	 *
	 * @param method      the HTTP method of the request
	 * @param uri         the URI being requested
	 * @param httpVersion the HTTP protocol version
	 */
	public RequestLine(HttpMethod method, URI uri, HttpVersion httpVersion) {
		this.method = method;
		this.uri = uri;
		this.httpVersion = httpVersion;
	}

	/**
	 * Constructs a new RequestLine instance from a raw request line string (e.g. "GET /index.html HTTP/1.1")
	 *
	 * @param rawRequestLine the raw request line
	 * @throws IllegalArgumentException if the request line is malformed or contains invalid data
	 */
	public RequestLine(String rawRequestLine) {
		parseRequestLine(rawRequestLine);
	}

	private void parseRequestLine(String rawRequestLine) {
		String[] requestLineParts = rawRequestLine.trim().split(" +", 3);
		if (requestLineParts.length != 3) {
			throw new IllegalArgumentException("Invalid request line, must be in the format \"Method Request-URI HTTP-Version\" as specified in RFC2616: " + rawRequestLine);
		}

		method = HttpMethod.valueOf(requestLineParts[0].trim());

		try {
			uri = new URI(requestLineParts[1]);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid request URI", e);
		}

		httpVersion = new HttpVersion(requestLineParts[2].trim());
	}

	@Override
	public int hashCode() {
		return Objects.hash(method, uri, httpVersion);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RequestLine
				&& this.method() == ((RequestLine) obj).method()
				&& this.uri().equals(((RequestLine) obj).uri())
				&& this.version().equals(((RequestLine) obj).version());
	}

	/**
	 * Gets the HTTP method of the request line.
	 *
	 * @return Returns the HTTP method.
	 */
	public HttpMethod method() {
		return method;
	}

	/**
	 * Gets the URI of the request line.
	 *
	 * @return Returns the URI.
	 */
	public URI uri() {
		return uri;
	}

	/**
	 * Gets the HTTP version of the request line.
	 *
	 * @return Returns the HTTP version.
	 */
	public HttpVersion version() {
		return httpVersion;
	}
}
