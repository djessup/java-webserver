package au.id.deejay.webserver.server;

import au.id.deejay.webserver.exception.ServerException;
import au.id.deejay.webserver.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The executor maintains a thread pool for worker threads servicing individual client connections.
 *
 * @author David Jessup
 */
public class WebServerExecutor implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(WebServerExecutor.class);

	private int port;
	private int timeout;
	private int maxThreads;
	private final ResponseFactory responseFactory;

	private ExecutorService threadPool;
	private boolean running;

	public WebServerExecutor(int port, int timeout, int maxThreads, ResponseFactory responseFactory) {
		this.port = port;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
		this.responseFactory = responseFactory;

		running = false;
	}

	@Override
	public void run() {
		running = true;

		threadPool = Executors.newFixedThreadPool(maxThreads);

		ServerSocket serverSocket = serverSocket();
		while (running()) {
			// Wait for a client connection
			Socket client = acceptConnection(serverSocket);

			if (client != null) {
				// Assign the connection to a worker
				Runnable worker = assignWorker(client);

				// Queue the worker for execution
				threadPool.execute(worker);
			}
		}

		LOG.info("Shutting down web server executor.");

		try {
			threadPool.shutdown();
			threadPool.awaitTermination(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("Worker thread pool was interrupted while shutting down. Some client connections may have been terminated prematurely.", e);
			Thread.currentThread().interrupt();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				LOG.error("Failed to release server port", e);
			}
		}

		LOG.info("Web server executor has shutdown.");
	}

	public synchronized boolean running() {
		return running;
	}

	public synchronized void stop() {
		running = false;
	}

	private Runnable assignWorker(Socket client) {
		try {
			client.setSoTimeout(timeout * 1000);
		} catch (SocketException e) {
			LOG.warn("Unable to set socket timeout", e);
		}
		return new WebWorker(client, responseFactory);
	}

	private ServerSocket serverSocket() {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new ServerException("Failed to open server socket on port " + port, e);
		}
	}

	private Socket acceptConnection(ServerSocket serverSocket) {
		try {
			return serverSocket.accept();
		} catch (IOException e) {
			LOG.info("Error accepting a client connection", e);
			return null;
		}
	}

}
