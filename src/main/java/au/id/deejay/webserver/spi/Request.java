package au.id.deejay.webserver.spi;

import au.id.deejay.webserver.request.HttpMethod;

import java.net.URI;

/**
 * Represents a single HTTP request.
 *
 * @author David Jessup
 */
public interface Request extends HttpMessage {
	HttpMethod method();
	URI uri();
}
