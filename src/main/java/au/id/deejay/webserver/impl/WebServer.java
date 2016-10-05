package au.id.deejay.webserver.impl;

import au.id.deejay.webserver.request.RequestFactory;
import au.id.deejay.webserver.response.ResponseFactory;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author David Jessup
 */
public class WebServer implements Server {

	private static final Logger LOG = LoggerFactory.getLogger(WebServer.class);

	private int port;
	private int timeout;
	private int maxThreads;
	private List<RequestHandler> requestHandlers;

	private boolean running;

	private WebServerExecutor executor;
	private Thread executorThread;

	/**
	 * @param port            The port number to bind the server to.
	 * @param timeout         Request timeout (in seconds). Requests which take longer than this will be terminated.
	 * @param maxThreads      The maximum number of worker threads to use for servicing requests.
	 * @param requestHandlers A list of handlers to use to service requests. The order of the list determines the
	 *                        priority of the handlers (handlers appearing first will be given first opportunity to
	 *                        handle requests).
	 * @throws IllegalArgumentException if the port number is outside the valid range (i.e. 0-65535)
	 */
	public WebServer(int port, int timeout, int maxThreads, List<RequestHandler> requestHandlers) {

		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Port must be in the range 0-65535.");
		}

		if (maxThreads <= 0) {
			throw new IllegalArgumentException("Max threads must be greater than zero.");
		}

		if (requestHandlers == null || requestHandlers.isEmpty()) {
			throw new IllegalArgumentException("At least one request handler must be provided, otherwise the server won't be able to do anything!");
		}

		this.port = port;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
		this.requestHandlers = requestHandlers;
	}

	@Override
	public synchronized void start() {

		if (running()) {
			throw new IllegalStateException("Server is already running.");
		}

		LOG.info("Starting web server on port {} with {} worker threads.", port, maxThreads);

		RequestFactory requestFactory = new RequestFactory();
		ResponseFactory responseFactory = new ResponseFactory(requestHandlers);

		executor = new WebServerExecutor(port, timeout, maxThreads, requestFactory, responseFactory);

		executorThread = new Thread(executor);
		executorThread.start();

		running = true;
	}

	@Override
	public synchronized void stop() {

		if (!running()) {
			throw new IllegalStateException("Server isn't running.");
		}

		LOG.info("Stopping server");

		executor.stop();
		running = false;

		LOG.info("Server stopped.");

	}

	@Override
	public synchronized boolean running() {
		return running;
	}

}
