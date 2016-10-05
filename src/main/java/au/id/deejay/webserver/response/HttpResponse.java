package au.id.deejay.webserver.response;

import au.id.deejay.webserver.headers.HttpHeaders;
import au.id.deejay.webserver.request.HttpVersion;
import au.id.deejay.webserver.spi.Headers;
import au.id.deejay.webserver.spi.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author David Jessup
 */
public class HttpResponse implements Response {

	private HttpStatus status;
	private Headers headers;
	private String body;
	private HttpVersion version;

	public HttpResponse(HttpStatus status, HttpVersion version) {
		this(status, new HttpHeaders(), "", version);
	}

	public HttpResponse(HttpStatus status, String body, HttpVersion version) {
		this(status, new HttpHeaders(), body, version);
	}

	public HttpResponse(HttpStatus status, Headers headers, String body, HttpVersion version) {
		this.status = status;
		this.headers = headers;
		this.body = body;
		this.version = version;
	}

	@Override
	public InputStream body() {
		return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public Headers headers() {
		return headers;
	}

	@Override
	public HttpVersion version() {
		return version;
	}

	@Override
	public HttpStatus status() {
		return status;
	}
}
