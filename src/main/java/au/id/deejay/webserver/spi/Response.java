package au.id.deejay.webserver.spi;

import au.id.deejay.webserver.response.HttpStatus;

/**
 * Represents a single HTTP response.
 *
 * @author David Jessup
 */
public interface Response extends HttpMessage {
	HttpStatus status();
}
