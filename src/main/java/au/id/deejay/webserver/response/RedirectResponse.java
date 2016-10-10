package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;

/**
 * @author David Jessup
 */
public class RedirectResponse extends HttpResponse {
	private final String destination;

	public RedirectResponse(String destination, HttpVersion version) {
		super(HttpStatus.MOVED_PERMANENTLY_301, version);
		this.destination = destination;
	}

	@Override
	public Headers headers() {
		return new HttpHeaders(new HttpHeader("Location", destination));
	}
}
