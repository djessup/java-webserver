package au.id.deejay.webserver;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Facilitates reading values of interest supplied via command line arguments.
 *
 * @author David Jessup
 */
public class CommandLineOptions {

	/**
	 * Default port the web server will be bound.
	 */
	private static final int DEFAULT_PORT = 0;

	/**
	 * Default document root used.
	 */
	private static final String DEFAULT_DOCROOT = "./docroot";

	/**
	 * Default max number of worker threads.
	 */
	private static final int DEFAULT_MAX_THREADS = 20;

	/**
	 * Default request timeout.
	 */
	private static final int DEFAULT_TIMEOUT = 10;
	private final OptionSpec<Integer> timeout;
	private final OptionSpec<Integer> maxThreads;
	private final OptionSpec<Void> help;
	private final OptionParser parser;
	private final OptionSet options;
	private OptionSpec<Integer> port;
	private OptionSpec<String> docroot;

	/**
	 * Parses an array of command line options.
	 *
	 * @param args An array of command line arguments to parse
	 */
	public CommandLineOptions(String... args) {
		// Create the parser
		parser = new OptionParser();

		// Define the options
		port = withPort();
		docroot = withDocroot();
		timeout = withTimeout();
		maxThreads = withMaxThreads();
		help = withHelp();

		// Parse the supplied args
		options = parser.parse(args);
	}

	/**
	 * Builds the "port" option
	 */
	private OptionSpec<Integer> withPort() {
		return parser.acceptsAll(Arrays.asList("port", "p"),
								 "Port to bind the web server to. Can be any unused port in the range 0-65535")
				.withRequiredArg()
				.ofType(Integer.class)
				.defaultsTo(DEFAULT_PORT);
	}

	/**
	 * Builds the "docroot" option
	 */
	private OptionSpec<String> withDocroot() {
		return parser.acceptsAll(Arrays.asList("docroot", "d"),
								 "Path to the document root containing the files the server will serve")
				.withRequiredArg()
				.defaultsTo(DEFAULT_DOCROOT);
	}

	/**
	 * Builds the "timeout" option
	 */
	private OptionSpec<Integer> withTimeout() {
		return parser.acceptsAll(Arrays.asList("timeout", "t"),
								 "Request timeout (in seconds). Requests which take longer than this will be terminated")
				.withRequiredArg()
				.ofType(Integer.class)
				.defaultsTo(DEFAULT_TIMEOUT);
	}

	/**
	 * Builds the "maxthreads" option
	 */
	private OptionSpec<Integer> withMaxThreads() {
		return parser.acceptsAll(Arrays.asList("maxthreads", "m"),
								 "Maximum number of worker threads that may be spawned to service client requests")
				.withRequiredArg()
				.ofType(Integer.class)
				.defaultsTo(DEFAULT_MAX_THREADS);
	}

	/**
	 * Builds the "help" (usage) option
	 */
	private OptionSpec<Void> withHelp() {
		return parser.acceptsAll(Arrays.asList("help", "h"),
								 "Display help/usage information")
				.forHelp();
	}

	/**
	 * Gets the port option.
	 *
	 * @return Returns the port.
	 */
	public int port() {
		return options.valueOf(port);
	}

	/**
	 * Gets the docroot option.
	 *
	 * @return Returns the docroot.
	 */
	public String docroot() {
		return options.valueOf(docroot);
	}

	/**
	 * Gets the timeout option.
	 *
	 * @return Returns the timeout.
	 */
	public int timeout() {
		return options.valueOf(timeout);
	}

	/**
	 * Gets the max threads option.
	 *
	 * @return Returns the max threads.
	 */
	public int maxThreads() {
		return options.valueOf(maxThreads);
	}

	/**
	 * Gets the help flag option.
	 *
	 * @return Returns the help flag.
	 */
	public boolean help() {
		return options.has(help);
	}

	/**
	 * Writes the CLI options help to an {@link OutputStream}.
	 *
	 * @param sink the stream to write to
	 * @throws IOException if there is a problem writing to the stream.
	 */
	public void printHelpOn(OutputStream sink) throws IOException {
		parser.printHelpOn(sink);
	}
}
