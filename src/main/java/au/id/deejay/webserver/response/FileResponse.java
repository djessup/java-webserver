package au.id.deejay.webserver.response;

import au.id.deejay.webserver.exception.ResponseException;
import au.id.deejay.webserver.request.HttpVersion;

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
			throw new ResponseException("Failed to detect MIME type of " + file.getPath());
		}
	}

	@Override
	public InputStream body() {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			return inputStream;
		} catch (FileNotFoundException e) {
			throw new ResponseException("Requested file does not exist", e);
		} catch (IOException e) {
			throw new ResponseException("Failed to read requested file", e);
		}
	}
}
