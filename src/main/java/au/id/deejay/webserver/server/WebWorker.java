package au.id.deejay.webserver.server;

import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.exception.RequestException;
import au.id.deejay.webserver.exception.ResponseException;
import au.id.deejay.webserver.io.RequestReader;
import au.id.deejay.webserver.io.ResponseWriter;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author David Jessup
 */
public class WebWorker implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(WebWorker.class);

	private static String CONNECTION_HEADER = "Connection";

	private final Socket client;
	private final ResponseFactory responseFactory;
	private boolean persistConnection = false;

	public WebWorker(Socket client, ResponseFactory responseFactory) {
		this.client = client;
		this.responseFactory = responseFactory;
		this.persistConnection = true;
	}

	@Override
	public void run() {
		try {
			handleConnection();
		} catch (Exception e) {
			LOG.error("An unhandled exception was caught while attempting to handle a client connection.", e);
		} finally {
			closeConnection();
		}
	}

	private void handleConnection() {
		InputStream inputStream;
		OutputStream outputStream;

		try {
			inputStream = client.getInputStream();
			outputStream = client.getOutputStream();
		} catch (IOException e) {
			LOG.error("Failed to acquire I/O streams from client connection", e);
			closeConnection();
			return;
		}

		RequestReader requestReader = new RequestReader(inputStream);
		ResponseWriter responseWriter = new ResponseWriter(outputStream);

		while (persistConnection()) {

			Request request;
			Response response;

			try {
				request = readRequest(requestReader);
				response = generateResponse(request);
			} catch (RequestException e) {
				LOG.info("Bad request from " + client.getRemoteSocketAddress().toString(), e);
				response = ErrorResponse.BAD_REQUEST_400;
			}

			// Check if the response is forcing the connection closed, or if it's an error response close it anyway
			if (persistConnection()
				&& (response.headers().contains(CONNECTION_HEADER)
				&& response.headers().values(CONNECTION_HEADER).contains("close"))
				|| response instanceof ErrorResponse) {
				persistConnection(false);
			}

			writeResponse(responseWriter, response);
		}
	}

	private Request readRequest(RequestReader requestReader) {
		Request request = requestReader.readRequest();

		// Use persistent connections if requested, or by default for HTTP/1.1+
		if (HttpVersion.HTTP_1_1.compareTo(request.version()) >= 0
			|| (
			request.headers().contains(CONNECTION_HEADER)
				&& request.headers().values(CONNECTION_HEADER).contains("keep-alive"))) {
			persistConnection(true);
		}

		// Never use persistent connections if explicitly disabled
		if (request.headers().contains(CONNECTION_HEADER)
			&& request.headers().values(CONNECTION_HEADER).contains("close")) {
			persistConnection(false);
		}

		return request;
	}

	private Response generateResponse(Request request) {
		try {
			return responseFactory.response(request);
		} catch (Exception e) {
			LOG.error("Error generating response message", e);
			return ErrorResponse.INTERNAL_SERVER_ERROR_500;
		}
	}

	private boolean persistConnection() {
		return persistConnection;
	}

	private void persistConnection(boolean persistConnection) {
		this.persistConnection = persistConnection;
	}

	private void writeResponse(ResponseWriter responseWriter, Response response) {
		try {
			responseWriter.writeResponse(response);
		} catch (ResponseException e) {
			LOG.error("Unable to send response", e);
		}
	}

	private void closeConnection() {
		try {
			client.close();
		} catch (IOException e) {
			LOG.error("Failed to close client connection", e);
		}
	}
}
