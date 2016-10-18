package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.exception.ResponseException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * An HTTP response which lists the contents of a directory.
 *
 * @author David Jessup
 */
public class DirectoryListingResponse extends HttpResponse {

	private final File directory;
	private String html;

	/**
	 * Creates a new {@link DirectoryListingResponse}.
	 *
	 * @param directory the directory which will be listed by the response
	 * @param version the HTTP version of the response
	 */
	public DirectoryListingResponse(File directory, HttpVersion version) {
		super(HttpStatus.OK_200, version);
		this.directory = directory;

		if (!directory.canRead()) {
			throw new ResponseException("Requested directory does not exist or cannot be read: " + directory.getPath());
		}

		html = listing();
		headers().set("Content-length", String.valueOf(html.length()));
		headers().set("Content-type", "text/html");
	}

	private String listing() {

		StringBuilder output = new StringBuilder();

		output.append("<h1>Directory listing for: ")
			.append(directory.getPath())
			.append("</h1>")
			.append("<ol>");

		listFile(output, "[ Up ]", "../");
		for (File file : directory.listFiles()) {
			listFile(output, file.getName(), file.getName() + (file.isDirectory() ? "/" : ""));
		}

		output.append("</ol>");

		return output.toString();
	}

	private void listFile(StringBuilder html, String name, String path) {
		html.append("<li><a href=\"./")
			.append(path)
			.append("\">")
			.append(name)
			.append("</a></li>");
	}

	@Override
	public InputStream stream() {
		return new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
	}
}
