package au.id.deejay.webserver.server;

import au.id.deejay.webserver.request.RequestReader;
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
	private final Socket client;
	private final ResponseFactory responseFactory;
	private List<RequestHandler> requestHandlers;

	public WebWorker(Socket client, ResponseFactory responseFactory) {
		this.client = client;
		this.responseFactory = responseFactory;
	}

	@Override
	public void run() {
		Request request;
		Response response;

		try {
			InputStream inputStream = client.getInputStream();
			request = readRequest(inputStream);
			response = responseFactory.response(request);
		} catch (IOException e) {
			LOG.info("Bad request from " + client.getRemoteSocketAddress().toString(), e);
			response = ErrorResponse.BAD_REQUEST_400;
		}

		try {
			OutputStream outputStream = client.getOutputStream();
			writeResponse(outputStream, response);
		} catch (Exception e) {
			LOG.error("Failed to send response", e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				LOG.error("Failed to close client connection", e);
			}
		}
	}

	private Request readRequest(InputStream inputStream) {
		return new RequestReader(inputStream).readRequest();
	}

	private void writeResponse(OutputStream outputStream, Response response) throws IOException {
		new ResponseWriter(outputStream).writeResponse(response);
	}
}
