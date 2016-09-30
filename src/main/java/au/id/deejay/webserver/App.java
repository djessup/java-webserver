package au.id.deejay.webserver;


import au.id.deejay.webserver.spi.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application bootstrap class.
 *
 * Parses and validates options from the command line then uses them to create a new server instance.
 */
public class App {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    /**
     * Default port the web server will be bound to if none is specified via the command line.
     * Valid ports are 0-65535.
     */
    private static final int DEFAULT_PORT = 80;

    private static final String DEFAULT_DOCROOT = "./docroot";
    private static final int DEFAULT_MAX_THREADS = 8;
    private static final int DEFAULT_TIMEOUT = 30;

    /**
     * Command line flag to display help/usage.
     */
    private static final String FLAG_HELP = "--help";
    /**
     * Abbreviated command line flag to display help/usage.
     */
    private static final String FLAG_HELP_SHORT = "-h";

    /**
     * Main application loop. Parses the command line args then tries to start a server instance.
     *
     * @param args String array of command line arguments
     */
    public static void main( String[] args ) {

        // Start with default values
        int port = DEFAULT_PORT;
        String docroot = DEFAULT_DOCROOT;
        int timeout = DEFAULT_TIMEOUT;
        int maxThreads = DEFAULT_MAX_THREADS;

        // Parse any any user-supplied arguments
        if (args.length > 0) {
            // Display help/usage info if the --help or -h flag is the first argument
            if (FLAG_HELP.equals(args[0]) || FLAG_HELP_SHORT.equals(args[0])) {
                printUsage();
                return;
            }

            // If the first arg isn't the help flag assume it's a port number
            try {
                port = parsePortArgument(args[0]);
            } catch (IllegalArgumentException iae) {
                LOG.error(iae.getMessage(), iae);
            }
        }

        // Start the web server
        Server server = new WebServer(port, docroot, timeout, maxThreads);
        server.start();
    }

    private static int parsePortArgument(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid port number. Must be an integer in the range 0-65535.", nfe);
        }
    }

    private static void printUsage() {
        // TODO: i18n?
        System.out.println("Java Web Server\n");
        System.out.println("\tA simple multi-threaded web server written in Java and implementing the HTTP/1.1 specification.\n");

        System.out.println("Usage:\n");
        System.out.println("\tjava -jar java-webserver.jar [port [docroot [timeout [maxThreads]]]]\n");

        System.out.println("Options:\n");
        System.out.println("\tport \t\t\tPort to bind the web server to. Can be any unused port in the range 0-65535. Default: " + DEFAULT_PORT);
        System.out.println("\tdocroot \t\tPath to the document root which contains the files the server will serve. Default: " + DEFAULT_DOCROOT);
        System.out.println("\ttimeout \t\tRequest timeout (in seconds). Requests which take longer than this will be terminated. Default: " + DEFAULT_TIMEOUT);
        System.out.println("\tmaxThreads \t\tMaximum number of worker threads that may be spawned to service client requests. Default: " + DEFAULT_MAX_THREADS);
        System.out.println("\t--help / -h \tDisplays usage information");
    }

}
