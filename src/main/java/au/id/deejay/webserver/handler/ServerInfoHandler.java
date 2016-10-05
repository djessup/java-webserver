package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.response.HttpResponse;
import au.id.deejay.webserver.response.HttpStatus;
import au.id.deejay.webserver.spi.HttpMethod;
import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Response;

import java.text.MessageFormat;

/**
 * A request handler which provides a brief summary of the server's runtime configuration when a GET request is made
 * to /serverInfo
 *
 * @author David Jessup
 */
public class ServerInfoHandler implements RequestHandler {

	private static final String SERVER_INFO_PATH = "/serverInfo";

	private int port;
	private int timeout;
	private int maxThreads;
	private String docroot;
	private long startTime;

	public ServerInfoHandler(int port, int timeout, int maxThreads, String docroot, long startTime) {
		this.port = port;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
		this.docroot = docroot;
		this.startTime = startTime;
	}

	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET && SERVER_INFO_PATH.equals(request.uri().getPath());
	}

	@Override
	public Response handle(Request request) {

		long uptime = (System.currentTimeMillis() - startTime) / 1000;

		return new HttpResponse(HttpStatus.OK_200,
								MessageFormat.format(
										"Serving files from {0} on port {1} with {2} threads and a timeout of {3} seconds.\nUptime: {4}s",
										docroot,
										String.valueOf(port),
										maxThreads,
										timeout,
										uptime
								),
								request.version()
		);
	}
}
