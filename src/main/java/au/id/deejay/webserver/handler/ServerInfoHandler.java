package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.response.HttpResponse;
import au.id.deejay.webserver.response.HttpStatus;
import au.id.deejay.webserver.spi.HttpMethod;
import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Response;

import java.text.MessageFormat;

/**
 * @author David Jessup
 */
public class ServerInfoHandler implements RequestHandler {

	private static final String SERVER_INFO_PATH = "/serverInfo";

	private int port;
	private int timeout;
	private int maxThreads;
	private String docroot;
	private long startTime;

	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET && SERVER_INFO_PATH.equals(request.uri().getPath());
	}

	@Override
	public Response handle(Request request) {

		long uptime = (System.currentTimeMillis() - startTime) / 1000;

		return new HttpResponse(HttpStatus.OK_200,
								MessageFormat.format(
										"Serving files from {0} on port {1} with {2} threads and a timeout of {3} seconds.\nUptime: {4}",
										docroot,
										port,
										maxThreads,
										timeout,
										uptime
								)
		);
	}
}
