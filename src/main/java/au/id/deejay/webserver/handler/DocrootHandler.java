package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.RequestHandler;
import au.id.deejay.webserver.spi.Response;

import java.io.File;

/**
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
		return "GET".equals(request.method());
	}

	@Override
	public Response handle(Request request) {
		File file = child(request.uri().getPath());
		return null;
	}

	private File child(String name) {
		return new File(docroot, name);
	}
}
