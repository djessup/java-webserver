package au.id.deejay.webserver.response;

import au.id.deejay.webserver.spi.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static au.id.deejay.webserver.MessageConstants.CRLF;

/**
 * Writes a response to an {@link OutputStream}
 * @author David Jessup
 */
public class ResponseWriter extends Writer {

	private static final Logger LOG = LoggerFactory.getLogger(ResponseWriter.class);

	private OutputStream outputStream;

	public ResponseWriter(OutputStream outputStream) {
		super(outputStream);
		this.outputStream = outputStream;
	}

	public void writeResponse(Response response) {
		try {
			write(statusLine(response));
			write(response.headers().toString());
			write(CRLF);
			IOUtils.copy(response.body(), outputStream);
		} catch (IOException e) {
			LOG.warn("Failed to write response", e);
			// TODO rethrow exception instead
		}
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


//Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
/*
 Full-Response   = Status-Line
				 *( General-Header
				  | Response-Header
				  | Entity-Header )
				 CRLF
				 [ Entity-Body ]
*/