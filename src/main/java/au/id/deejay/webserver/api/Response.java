package au.id.deejay.webserver.api;

/**
 * Represents a single HTTP response.
 *
 * @author David Jessup
 */
public interface Response extends HttpMessage {
	HttpStatus status();
}
