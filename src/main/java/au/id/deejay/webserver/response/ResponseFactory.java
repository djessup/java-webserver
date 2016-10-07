package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.Response;

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

	public ResponseFactory(List<RequestHandler> requestHandlers) {
		this.requestHandlers = requestHandlers;
	}

	public Response response(Request request) {

		Response response = null;

		for (RequestHandler handler : requestHandlers) {
			if (handler.canHandle(request)) {
				response = handler.handle(request);
			}
		}

		if (response == null) {
			response = ErrorResponse.NOT_IMPLEMENTED_501;
		}

		return response;
	}
}
