package au.id.deejay.webserver.api;

/**
 * Handles a {@link Request} and returns a response.
 *
 * @author David Jessup
 */
public interface RequestHandler {

	/**
	 * Checks if the handler is able to provide a response to a particular request.
	 *
	 * @param request the request to be handled.
	 * @return Returns true if the handler can provide a response to this request, or false if it cannot.
	 */
	boolean canHandle(Request request);

	/**
	 * Provides a response to a request.
	 *
	 * @param request the request to provide a response for
	 * @return Returns the response to the request.
	 */
	Response handle(Request request);
}
