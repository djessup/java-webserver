package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.exception.ResponseException;
import org.apache.tika.Tika;

import java.io.*;

/**
 * An HTTP response which streams a file back to the client in the response body.
 *
 * @author David Jessup
 */
public class FileResponse extends HttpResponse {

	private final File file;

	/**
	 * Creates a new {@link FileResponse}.
	 *
	 * @param file    the file to be streamed in the response body.
	 * @param version the HTTP version of the response.
	 */
	public FileResponse(File file, HttpVersion version) {
		super(HttpStatus.OK_200, version);
		this.file = file;

		if (!file.canRead()) {
			throw new ResponseException("Requested file does not exist or cannot be read: " + file.getPath());
		}

		headers().set("Content-length", String.valueOf(file.length()));

		try {
			headers().set("Content-type", contentType(file));
		} catch (IOException e) {
			throw new ResponseException("Failed to detect MIME type of " + file.getPath(), e);
		}
	}

	private String contentType(File file) throws IOException {
		Tika tika = new Tika();
		return tika.detect(file);
	}

	@Override
	public InputStream stream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ResponseException("Requested file does not exist", e);
		}
	}
}
