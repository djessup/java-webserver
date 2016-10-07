package au.id.deejay.webserver.api;

/**
 * Handles a {@link Request} and returns a response.
 *
 * @author David Jessup
 */
public interface RequestHandler {
	boolean canHandle(Request request);
	Response handle(Request request);
}
