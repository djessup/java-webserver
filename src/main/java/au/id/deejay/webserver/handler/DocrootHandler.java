package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.FileResponse;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.Response;

import java.io.File;

/**
 * A request handler which handles GET requests by serving files from within a folder.
 *
 * e.g. Using a DocrootHandler with docroot = /path/to/docroot and a request like GET /some/file.html the
 * DocrootHandler would attempt to respond with /path/to/docroot/some/file.html.
 *
 * @author David Jessup
 */
public class DocrootHandler implements RequestHandler {

	private final File docroot;

	public DocrootHandler(String docroot) {
		this.docroot = new File(docroot);

		if (!this.docroot.canRead()) {
			throw new IllegalStateException("Unable to read docroot: " + docroot);
		}

		if (!this.docroot.isDirectory()) {
			throw new IllegalArgumentException(docroot + " is not a directory");
		}
	}

	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET;
	}

	@Override
	public Response handle(Request request) {
		File file = child(request.uri().getPath());

		if (file.exists()) {
			return new FileResponse(file, request.version());
		}

		return ErrorResponse.NOT_FOUND_404;
	}

	private File child(String name) {
		return new File(docroot, name);
	}
}
