package au.id.deejay.webserver.io;

import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.exception.ResponseException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static au.id.deejay.webserver.MessageConstants.CRLF;

/**
 * Writes {@link Response}s to an {@link OutputStream}.
 *
 * @author David Jessup
 */
public class ResponseWriter extends Writer {

	private OutputStream outputStream;

	/**
	 * Creates a new {@link ResponseWriter} that will stream {@link Response} objects to the provided {@link
	 * OutputStream}.
	 *
	 * @param outputStream the output stream {@link Response}s will be written to.
	 */
	public ResponseWriter(OutputStream outputStream) {
		super(outputStream);
		this.outputStream = outputStream;
	}

	/**
	 * Writes a {@link Response} to the output stream.
	 *
	 * @param response the response to write
	 */
	public void writeResponse(Response response) {
		try {
			writeStatusLine(response);
			writeHeaders(response);
			writeBody(response);
		} catch (IOException e) {
			throw new ResponseException("Failed to write response", e);
		}
	}

	private void writeStatusLine(Response response) throws IOException {
		write(statusLine(response));
	}

	private void writeHeaders(Response response) throws IOException {
		write(response.headers().toString());
		write(CRLF);
	}

	private void writeBody(Response response) throws IOException {
		InputStream responseStream = response.stream();
		IOUtils.copy(responseStream, outputStream);
		responseStream.close();
	}

	private String statusLine(Response response) {
		return response.version().toString() + " " + response.status().code() + " " + response.status().description() + CRLF;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		outputStream.write(new String(cbuf).getBytes(StandardCharsets.UTF_8), off, len);
	}

	@Override
	public void flush() throws IOException {
		outputStream.flush();
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
