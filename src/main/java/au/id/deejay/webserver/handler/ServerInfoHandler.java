package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.*;
import au.id.deejay.webserver.response.HttpResponse;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * A request handler which provides a brief summary of the server's runtime configuration when a GET request is made
 * to "/serverInfo".
 *
 * @author David Jessup
 */
public class ServerInfoHandler implements RequestHandler {

	/**
	 * The path the server info will be served from
	 */
	private static final String SERVER_INFO_PATH = "/serverInfo";

	private int port;
	private int timeout;
	private int maxThreads;
	private String docroot;
	private long startTime;

	/**
	 * Creates a new {@link ServerInfoHandler}.
	 *
	 * @param port       the port the server is being run on
	 * @param timeout    the client timeout, in seconds
	 * @param maxThreads the maximum number of worker threads the server is configured to use
	 * @param docroot    the path to the server's document root
	 * @param startTime  the time the server was started (as reported by {@link System#currentTimeMillis()}
	 */
	public ServerInfoHandler(int port, int timeout, int maxThreads, String docroot, long startTime) {
		this.port = port;
		this.timeout = timeout;
		this.maxThreads = maxThreads;
		this.docroot = docroot;
		this.startTime = startTime;
	}

	/**
	 * Checks that the request is a GET request to /serverInfo.
	 *
	 * @param request the request to be handled.
	 * @return Returns true if the request is a GET request to the serverInfo path
	 */
	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET && SERVER_INFO_PATH.equals(request.uri().getPath());
	}

	/**
	 * Sends a simple summary of the server configuration and uptime.
	 *
	 * @param request the request to provide a response for
	 * @return Returns a 200 OK response containing a summary of the server.
	 */
	@Override
	public Response handle(Request request) {

		long uptime = (System.currentTimeMillis() - startTime) / 1000;

		// Breakdown uptime into days/hours/mins/secs
		long upDays = TimeUnit.SECONDS.toDays(uptime);
		long upHours = TimeUnit.SECONDS.toHours(uptime) - (upDays * 24);
		long upMins = TimeUnit.SECONDS.toMinutes(uptime) - (TimeUnit.SECONDS.toHours(uptime) * 60);
		long upSecs = TimeUnit.SECONDS.toSeconds(uptime) - (TimeUnit.SECONDS.toMinutes(uptime) * 60);

		String template = "<h1>Server info</h1>" +
				"<table border='1' width='50%'>" +
				"<tr>" +
				"<th width='30%'>Uptime</th>" +
				"<td width='70%'>{4}d {5}h {6}m {7}s</td>" +
				"</tr>" +
				"<tr>" +
				"<th>Port</th>" +
				"<td>{0}</td>" +
				"</tr>" +
				"<tr>" +
				"<th>Timeout</th>" +
				"<td>{1}s</td>" +
				"</tr>" +
				"<tr>" +
				"<th>Max worker threads</th>" +
				"<td>{2}</td>" +
				"</tr>" +
				"<tr>" +
				"<th>Document root</th>" +
				"<td>{3}</td>" +
				"</tr>" +
				"</table>";

		return new HttpResponse(HttpStatus.OK_200,
								MessageFormat.format(template, String.valueOf(port), timeout, maxThreads, docroot, upDays, upHours, upMins, upSecs),
								request.version());
	}
}
