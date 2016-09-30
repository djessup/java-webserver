package au.id.deejay.webserver.spi;

import java.net.URI;

/**
 * Represents a single HTTP request.
 *
 * @author David Jessup
 */
public interface Request extends HttpMessage {
	String method();
	URI uri();
}
