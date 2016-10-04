package au.id.deejay.webserver.request;

import au.id.deejay.webserver.spi.Headers;
import au.id.deejay.webserver.spi.HttpMethod;
import au.id.deejay.webserver.spi.Request;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author David Jessup
 */
public class HttpRequest implements Request {

	private final RequestLine requestLine;
	private Headers headers;
	private String body;

	public HttpRequest(InputStream inputStream) throws IOException {

		BufferedReader clientReader = new BufferedReader(
				new InputStreamReader(inputStream)
		);

		// First line is the request line
		requestLine = new RequestLine(clientReader.readLine().trim());

		// Subsequent lines are request headers until a blank line is encountered
		String line;
		while (!"".equals(line = clientReader.readLine()) && line != null) {
			// TODO: support multi-line header values (additional lines start with a space or tab character)
			parseHeaderLine(line);
		}

		StringBuilder requestBody = new StringBuilder();
		while ((line = clientReader.readLine()) != null) {
			requestBody.append(line)
					.append("\n");
		}
	}

	private void parseHeaderLine(String line) {
		int splitPos = line.indexOf(':');

		if (splitPos == -1) {
			throw new IllegalArgumentException("Malformed header encountered: " + line);
		}

		String name = line.substring(0, splitPos).trim();
		String[] values = line.substring(splitPos + 1, line.length())
				.split(",");

		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		}

		headers.add(name, values);
	}

	@Override
	public InputStream body() {
		return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public Headers headers() {
		return headers;
	}

	@Override
	public HttpMethod method() {
		return requestLine.method();
	}

	@Override
	public URI uri() {
		return requestLine.uri();
	}

	@Override
	public HttpVersion version() {
		return requestLine.version();
	}
}
