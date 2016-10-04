package au.id.deejay.webserver.response;

import au.id.deejay.webserver.headers.HttpHeaders;
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

	public HttpResponse(HttpStatus status) {
		this(status, new HttpHeaders(), "");
	}

	public HttpResponse(HttpStatus status, String body) {
		this(status, new HttpHeaders(), body);
	}

	public HttpResponse(HttpStatus status, Headers headers, String body) {
		this.status = status;
		this.headers = headers;
		this.body = body;
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
	public HttpStatus status() {
		return status;
	}
}
