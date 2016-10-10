package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.exception.ResponseException;

import java.util.List;

/**
 * Creates a {@link Response} to a {@link Request} by asking each {@link RequestHandler}, in order, until one is able to
 * service the request.
 * <p>
 * If no {@link RequestHandler} provides a {@link Response} a 501 error response will be returned.
 *
 * @author David Jessup
 */
public class ResponseFactory {

	private List<RequestHandler> requestHandlers;

	/**
	 * Constructs a new response factory and registers the provided {@link RequestHandler}s to handle generation of
	 * responses. The order of the handler list is significant as handlers will be asked in order if they can provide a
	 * response to a request.
	 *
	 * @param requestHandlers the list of request handlers to register for response generation.
	 */
	public ResponseFactory(List<RequestHandler> requestHandlers) {
		this.requestHandlers = requestHandlers;
	}

	/**
	 * Generates a response to a request by asking each of the registered {@link RequestHandler}s in turn if they can
	 * handle the request. If no handler opts to provide a response a 501-Not-Implemented response is returned.
	 *
	 * @param request the request to be responded to
	 * @return Returns a response to the request.
	 * @throws ResponseException if an exception is thrown by any of the {@link RequestHandler}s.
	 */
	public Response response(Request request) {

		Response response = null;

		try {
			for (RequestHandler handler : requestHandlers) {
				if (handler.canHandle(request)) {
					response = handler.handle(request);
					break;
				}
			}
		} catch (Exception e) {
			// Wrap the exception for upstream handling
			throw new ResponseException(e);
		}

		if (response == null) {
			response = ErrorResponse.NOT_IMPLEMENTED_501;
		}

		return response;
	}
}
