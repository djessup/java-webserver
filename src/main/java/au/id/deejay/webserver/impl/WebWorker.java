package au.id.deejay.webserver.impl;

import au.id.deejay.webserver.request.RequestFactory;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.ResponseFactory;
import au.id.deejay.webserver.response.ResponseWriter;
import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author David Jessup
 */
public class WebWorker implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(WebWorker.class);

	private List<RequestHandler> requestHandlers;
	private final Socket client;
	private final RequestFactory requestFactory;
	private final ResponseFactory responseFactory;

	public WebWorker(Socket client, RequestFactory requestFactory, ResponseFactory responseFactory) {
		this.client = client;
		this.requestFactory = requestFactory;
		this.responseFactory = responseFactory;
	}

	@Override
	public void run() {
		Request request;
		Response response;

		try {
			request = requestFactory.request(client.getInputStream());
			response = responseFactory.response(request);
		} catch (IOException e) {
			LOG.info("Bad request from " + client.getRemoteSocketAddress().toString(), e);
			response = ErrorResponse.BAD_REQUEST_400;
		}

		try {
			writeResponse(client.getOutputStream(), response);
		} catch (IOException e) {
			LOG.error("Failed to send response", e);
		} finally {
			if (!client.isClosed()) {
				try {
					client.close();
				} catch (IOException e) {
					LOG.error("Failed to close client connection", e);
				}
			}
		}
	}

	private void writeResponse(OutputStream outputStream, Response response) {
		new ResponseWriter(outputStream).writeResponse(response);
	}
}
