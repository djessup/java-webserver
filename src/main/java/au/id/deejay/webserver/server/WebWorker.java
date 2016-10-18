package au.id.deejay.webserver.server;

import au.id.deejay.webserver.api.HttpMessage;
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
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * A {@link WebWorker} is a worker thread that handle a single client connection before terminating. It will read
 * requests from the client's input stream and generate responses to them using it's {@link ResponseFactory}, for as
 * long as the HTTP connection is alive.
 *
 * @author David Jessup
 */
public class WebWorker implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(WebWorker.class);

	private static final String CONNECTION_HEADER = "Connection";

	private final Socket client;
	private final ResponseFactory responseFactory;
	private boolean keepAlive = false;

	/**
	 * Creates a new {@link WebWorker}.
	 *
	 * @param client          the client socket this worker will service
	 * @param responseFactory the response factory to use to generate {@link Response}s.
	 */
	public WebWorker(Socket client, ResponseFactory responseFactory) {
		this.client = client;
		this.responseFactory = responseFactory;
		this.keepAlive = true;
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
			return;
		}

		RequestReader requestReader = new RequestReader(inputStream);
		ResponseWriter responseWriter = new ResponseWriter(outputStream);

		while (isKeepAliveConnection()) {

			Request request;
			Response response;

			try {
				// Read in the request and update the thread's keep-alive status
				request = requestReader.readRequest();
				keepAliveConnection(shouldKeepAlive(request));

				response = generateResponse(request);

				// Log the request
				LOG.info("({}) - \"{} {} {}\" {} {}",
						 client.getRemoteSocketAddress(),
						 request.method(),
						 request.uri(),
						 request.version(),
						 response.status().code(),
						 response.headers().value("Content-length"));

			} catch (RequestException e) {
				LOG.error("Bad request from " + client.getRemoteSocketAddress(), e);
				response = ErrorResponse.BAD_REQUEST_400;
			} catch (SocketTimeoutException e) {
				// Allow the connection to drop
				LOG.trace("Client connection timed out", e);
				return;
			}

			// If the client has requested keep-alive, check that the response hasn't overridden it
			if (isKeepAliveConnection()) {
				keepAliveConnection(shouldKeepAlive(response));
			}

			writeResponse(responseWriter, response);
		}
	}

	private void closeConnection() {
		try {
			client.close();
		} catch (IOException e) {
			LOG.error("Failed to close client connection", e);
		}
	}

	private boolean isKeepAliveConnection() {
		return keepAlive;
	}

	private void keepAliveConnection(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	private boolean shouldKeepAlive(HttpMessage message) {
		List<String> connectionValues = message.headers().values(CONNECTION_HEADER);
		if (connectionValues != null) {
			if (connectionValues.contains("keep-alive")) {
				return true;
			} else if (connectionValues.contains("close")) {
				return false;
			}
		}

		return HttpVersion.HTTP_1_1.compareTo(message.version()) <= 0;
	}

	private Response generateResponse(Request request) {
		try {
			return responseFactory.response(request);
		} catch (Exception e) {
			LOG.error("Error generating response message", e);
			return ErrorResponse.INTERNAL_SERVER_ERROR_500;
		}
	}

	private void writeResponse(ResponseWriter responseWriter, Response response) {
		try {
			responseWriter.writeResponse(response);
		} catch (ResponseException e) {
			LOG.error("Unable to send response", e);
		}
	}
}
