package au.id.deejay.webserver;


import au.id.deejay.webserver.impl.WebServer;
import au.id.deejay.webserver.spi.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main application bootstrap class.
 * <p>
 * Parses and validates options from the command line then uses them to create a new server instance.
 */
public class App {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * Main application loop. Parses the command line args then tries to start a server instance.
	 *
	 * @param args String array of command line arguments
	 */
	public static void main(String[] args) {

		CommandLineOptions options = new CommandLineOptions(args);

		// If the help options is set, print usage and exit.
		if (options.help()) {
			printUsage();
			return;
		}

		// Otherwise read the CLI options (or defaults, as defined in CommandLineOptions)
		int port = options.port();
		String docroot = options.docroot();
		int timeout = options.timeout();
		int maxThreads = options.maxThreads();

		// Start the web server
		Server server = new WebServer(port, docroot, timeout, maxThreads);
		server.start();
	}

	/**
	 * Displays usage information
	 */
	private static void printUsage() {
		System.out.println("Java Web Server");
		System.out.println("---------------");
		System.out.println("\tA simple multi-threaded web server written in Java and implementing the HTTP/1.1 specification.\n");

		System.out.println("Usage");
		System.out.println("-----");
		System.out.println("\tjava -jar java-webserver.jar [options]\n\n");

		try {
			new CommandLineOptions().printHelpOn(System.out);
		} catch (IOException e) {
			LOG.error("Unable to display help/usage information", e);
		}
	}

}
