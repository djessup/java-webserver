package au.id.deejay.webserver.request;

import au.id.deejay.webserver.spi.Headers;
import au.id.deejay.webserver.spi.Request;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author David Jessup
 */
public class HttpRequest implements Request {

	private final RequestLine requestLine;
	private final Headers headers;
	private final String body;

	public HttpRequest(RequestLine requestLine, Headers headers, String body) {
		this.requestLine = requestLine;
		this.headers = headers;
		this.body = body;
	}
//
//	public HttpRequest(InputStream inputStream) throws IOException {
//
//		BufferedReader clientReader = new BufferedReader(
//				new InputStreamReader(inputStream)
//		);
//
//		if (clientReader.ready()) {
//			// First line is the request line
//			requestLine = new RequestLine(clientReader.readLine().trim());
//		}
//
//		headers = new HttpHeaders();
//
//		// Subsequent lines are request headers until a blank line is encountered
//		String line;
//		while (!"".equals(line = clientReader.readLine()) && line != null) {
//			// TODO: support multi-line header values (additional lines start with a space or tab character)
//			parseHeaderLine(line);
//		}
//
//		StringBuilder requestBody = new StringBuilder();
//
//		if (clientReader.ready()) {
//			while (!"".equals(line = clientReader.readLine()) && line != null) {
//				requestBody.append(line)
//						.append("\n");
//			}
//		}
//
//		body = requestBody.toString();
//	}
//
//	private void parseHeaderLine(String line) {
//		int splitPos = line.indexOf(':');
//
//		if (splitPos == -1) {
//			throw new IllegalArgumentException("Malformed header encountered: " + line);
//		}
//
//		String name = line.substring(0, splitPos).trim();
//		String[] values = line.substring(splitPos + 1, line.length())
//				.split(",");
//
//		for (int i = 0; i < values.length; i++) {
//			values[i] = values[i].trim();
//		}
//
//		headers.add(name, values);
//	}

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
