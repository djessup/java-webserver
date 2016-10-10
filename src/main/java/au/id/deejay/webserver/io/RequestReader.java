package au.id.deejay.webserver.io;

import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.exception.RequestException;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;
import au.id.deejay.webserver.headers.Header;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.request.HttpRequest;
import au.id.deejay.webserver.request.RequestLine;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author David Jessup
 */
public class RequestReader extends Reader {

	private static final Logger LOG = LoggerFactory.getLogger(RequestReader.class);

	private InputStream inputStream;

	public RequestReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return inputStream.read(new String(cbuf).getBytes(StandardCharsets.UTF_8), off, len);
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	/**
	 * Reads data from the input stream into a {@link Request} object.
	 *
	 * @return Returns the Request object read from the stream.
	 * @throws RequestException if there was a problem reading or decoding the request from the input stream.
	 */
	public Request readRequest() {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

		try {
			RequestLine requestLine = readRequestLine(inputReader);
			Headers headers = readHeaders(inputReader);

			// FIXME: Full entity-body support as per RFC2616.
			String body = "";
			if (headers.contains("Content-length") || headers.contains("Transfer-Encoding")) {
				body = readBody(inputReader);
			}

			return new HttpRequest(requestLine, headers, body);
		} catch (IOException e) {
			throw new RequestException("Unable to parse input stream into a Request object.", e);
		}
	}

	private RequestLine readRequestLine(BufferedReader inputReader) throws IOException {
		return new RequestLine(inputReader.readLine());
	}

	/**
	 * TODO: support multi-line header values (additional lines start with a space or tab character)
	 */
	private Headers readHeaders(BufferedReader inputReader) throws IOException {
		Headers headers = new HttpHeaders();

		// Subsequent lines are request headers until a blank line is encountered
		if (inputReader.ready()) {
			String line = inputReader.readLine();
			while (line != null && !"".equals(line)) {
				headers.add(parseHeaderLine(line));
				line = inputReader.readLine();
			}
		}

		return headers;
	}

	private String readBody(BufferedReader inputReader) throws IOException {
		return IOUtils.toString(inputReader);
	}

	private Header parseHeaderLine(String line) {
		int splitPos = line.indexOf(':');

		if (splitPos == -1) {
			throw new IllegalArgumentException("Malformed header: " + line);
		}

		String name = line.substring(0, splitPos).trim();
		String[] values = line.substring(splitPos + 1, line.length())
			.split(",");

		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		}

		return new HttpHeader(name, values);
	}
}
