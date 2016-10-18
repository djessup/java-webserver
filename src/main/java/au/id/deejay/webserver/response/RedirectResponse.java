package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;

/**
 * An response which sends a 301 redirect to the specified location.
 *
 * @author David Jessup
 */
public class RedirectResponse extends HttpResponse {

	private final String destination;

	/**
	 * Creates a new {@link RedirectResponse}.
	 *
	 * @param destination the location to redirect to
	 * @param version     the HTTP version of the response
	 */
	public RedirectResponse(String destination, HttpVersion version) {
		super(HttpStatus.MOVED_PERMANENTLY_301, version);
		this.destination = destination;
	}

	@Override
	public Headers headers() {
		return new HttpHeaders(new HttpHeader("Location", destination));
	}
}
