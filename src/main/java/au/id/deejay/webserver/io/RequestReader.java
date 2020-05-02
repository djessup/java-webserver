package au.id.deejay.webserver.io;

import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.exception.RequestException;
import au.id.deejay.webserver.headers.Header;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;
import au.id.deejay.webserver.request.HttpRequest;
import au.id.deejay.webserver.request.RequestLine;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * Reads {@link Request}s from an {@link InputStream}.
 *
 * @author David Jessup
 */
public class RequestReader extends Reader {

	private final BufferedReader inputReader;
	private final InputStream inputStream;

	/**
	 * Constructs a new {@link RequestReader} that deserialises the provided {@link InputStream} in {@link Request}
	 * objects.
	 *
	 * @param inputStream the input stream to read requests from
	 */
	public RequestReader(InputStream inputStream) {
		this.inputStream = inputStream;
		this.inputReader = new BufferedReader(new InputStreamReader(inputStream));
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
	 * @throws RequestException       if there was a problem reading or decoding the request from the input stream.
	 * @throws SocketTimeoutException if the socket connection times out while waiting for data. This can happen as
	 *                                part of normal operation when reading from a "keep-alive" connection, or due to
	 *                                abnormal conditions like a broken network connection.
	 */
	public Request readRequest() throws SocketTimeoutException {
		try {
			RequestLine requestLine = readRequestLine();
			Headers headers = readHeaders();

			// TODO: Full entity-body support as per RFC2616.
			String body = "";
			if (headers.contains("Content-length") || headers.contains("Transfer-Encoding")) {
				body = readBody();
			}

            return new HttpRequest(requestLine, headers, body);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException("Unable to parse input stream into a Request object.", e);
        }
    }

	private RequestLine readRequestLine() throws IOException {
		return new RequestLine(inputReader.readLine());
	}

	// TODO: support multi-line header values (additional lines start with a space or tab character)
	private Headers readHeaders() throws IOException {
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

	private String readBody() throws IOException {
		// FIXME: This will read everything available, instead it should read Content-length bytes
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
