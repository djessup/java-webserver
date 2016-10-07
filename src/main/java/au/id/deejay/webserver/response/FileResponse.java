package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.exception.ResponseException;
import au.id.deejay.webserver.api.HttpVersion;

import java.io.*;
import java.nio.file.Files;

/**
 * @author David Jessup
 */
public class FileResponse extends HttpResponse {

	private final File file;

	public FileResponse(File file, HttpVersion version) {
		super(HttpStatus.OK_200, version);
		this.file = file;
		headers().set("Content-length", String.valueOf(file.length()));
		try {
			headers().set("Content-type", Files.probeContentType(file.toPath()));
		} catch (IOException e) {
			throw new ResponseException("Failed to detect MIME type of " + file.getPath(), e);
		}
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
