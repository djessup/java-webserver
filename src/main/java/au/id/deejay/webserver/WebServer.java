package au.id.deejay.webserver;

import au.id.deejay.webserver.spi.Server;

/**
 * @author David Jessup
 */
public class WebServer implements Server {

	private int port;
	private String docroot;
	private int timeout;
	private int maxThreads;

	/**
	 * @param port       The port number to bind the server to (e.g. port 80).
	 * @param docroot    Path to the docroot directory containing files the server can serve.
	 * @param timeout    Request timeout (in seconds). Requests which take longer than this will be terminated.
	 * @param maxThreads The maximum number of threads to spawn for servicing requests.
	 * @throws IllegalArgumentException if the port number is outside the valid range (i.e. 0-65535)
	 */
	public WebServer(int port, String docroot, int timeout, int maxThreads) {
		this.port = port;
		this.docroot = docroot;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}
}
