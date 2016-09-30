package au.id.deejay.webserver.spi;

/**
 * @author David Jessup
 */
public interface Server {

	/**
	 * Starts the web server.
	 *
	 * @throws IllegalStateException if the server is already running or is not correctly configured.
	 */
	void start();

	/**
	 * Gracefully stops the server, waiting for established connections to either complete or timeout.
	 *
	 * @throws IllegalStateException if the server is not started
	 */
	void stop();
}
