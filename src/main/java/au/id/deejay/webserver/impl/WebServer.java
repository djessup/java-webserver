package au.id.deejay.webserver.impl;

import au.id.deejay.webserver.spi.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author David Jessup
 */
public class WebServer implements Server {

	private static final Logger LOG = LoggerFactory.getLogger(WebServer.class);

	private int port;
	private String docroot;
	private int timeout;
	private int maxThreads;

	private boolean running;

	private WebServerExecutor executor;

	/**
	 * @param port       The port number to bind the server to.
	 * @param docroot    Path to the docroot directory containing files the server can serve.
	 * @param timeout    Request timeout (in seconds). Requests which take longer than this will be terminated.
	 * @param maxThreads The maximum number of threads to spawn for servicing requests.
	 * @throws IllegalArgumentException if the port number is outside the valid range (i.e. 0-65535)
	 */
	public WebServer(int port, String docroot, int timeout, int maxThreads) {

		// TODO validate options

		this.port = port;
		this.docroot = docroot;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
	}

	@Override
	public synchronized void start() {

		if (running()) {
			throw new IllegalStateException("Server is already running");
		}

		LOG.info("Starting web server on port {} with {} worker threads", port, maxThreads);

		executor = new WebServerExecutor(port, docroot, timeout, maxThreads);

		Thread executorThread = new Thread(executor);
		executorThread.start();

		running = true;
	}

	@Override
	public synchronized void stop() {

		if (!running()) {
			throw new IllegalStateException("Server isn't running");
		}

		LOG.info("Stopping web server");

		executor.stop();
		running = false;

		LOG.info("Server stopped");

	}

	@Override
	public synchronized boolean running() {
		return running;
	}

}
