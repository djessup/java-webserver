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
import java.io.InputStream;
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

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = client.getInputStream();
			outputStream = client.getOutputStream();

			request = requestFactory.request(inputStream);
			response = responseFactory.response(request);
		} catch (IOException e) {
			LOG.info("Bad request from " + client.getRemoteSocketAddress().toString(), e);
			response = ErrorResponse.BAD_REQUEST_400;
		}

		try {
			if (outputStream != null) {
				writeResponse(outputStream, response);
				outputStream.close();
				inputStream.close();
			}
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

	private void writeResponse(OutputStream outputStream, Response response) throws IOException {
		new ResponseWriter(outputStream).writeResponse(response);
	}
}
