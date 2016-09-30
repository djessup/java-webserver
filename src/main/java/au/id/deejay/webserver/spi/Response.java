package au.id.deejay.webserver.spi;

/**
 * Represents a single HTTP response.
 *
 * @author David Jessup
 */
public interface Response extends HttpMessage {
	int status();
}
