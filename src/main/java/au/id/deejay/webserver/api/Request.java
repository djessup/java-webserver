package au.id.deejay.webserver.api;

import java.net.URI;

/**
 * An HTTP request message.
 *
 * @author David Jessup
 */
public interface Request extends HttpMessage {

	/**
	 * Gets the request method (e.g. GET).
	 *
	 * @return Returns the request method.
	 */
	HttpMethod method();

	/**
	 * Gets the URI being requested.
	 *
	 * @return Returns the request URI.
	 */
	URI uri();
}
