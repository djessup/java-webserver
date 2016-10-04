package au.id.deejay.webserver.response;

import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Response;

import java.util.List;

/**
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
