package au.id.deejay.webserver.io;

import au.id.deejay.webserver.StringCollectorOutputStream;
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.exception.ResponseException;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static au.id.deejay.webserver.MessageConstants.CRLF;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
public class ResponseWriterTest {

	private Response response;

	@Test
	public void testWriteResponse() throws Exception {
		withMockResponse();

		OutputStream outputStream = new StringCollectorOutputStream();
		ResponseWriter responseWriter = new ResponseWriter(outputStream);

		responseWriter.writeResponse(response);

		String expectedOutput = "HTTP/1.1 200 OK" + CRLF
				+ "Content-type: text/plain" + CRLF
				+ CRLF
				+ "Hello world!";
		
		assertThat(outputStream.toString(), is(equalTo(expectedOutput)));
	}

	@Test(expected = ResponseException.class)
	public void testBrokenOutputStreamThrowsException() throws Exception {
		withMockResponse();
		OutputStream outputStream = new ExplodingOutputStream();
		ResponseWriter responseWriter = new ResponseWriter(outputStream);

		responseWriter.writeResponse(response);
	}

	@Test
	public void testWriteIsPassedThroughToUnderlyingStream() throws Exception {
		OutputStream outputStream = mock(OutputStream.class);
		ResponseWriter responseWriter = new ResponseWriter(outputStream);
		responseWriter.write(new char[]{}, 0, 0);

		verify(outputStream).write(any(), anyInt(), anyInt());
	}

	@Test
	public void testFlushIsPassedThroughToUnderlyingStream() throws Exception {
		OutputStream outputStream = mock(OutputStream.class);
		ResponseWriter responseWriter = new ResponseWriter(outputStream);
		responseWriter.flush();

		verify(outputStream).flush();
	}

	@Test
	public void testCloseIsPassedThroughToUnderlyingStream() throws Exception {
		OutputStream outputStream = mock(OutputStream.class);
		ResponseWriter responseWriter = new ResponseWriter(outputStream);
		responseWriter.close();

		verify(outputStream).close();
	}

	private void withMockResponse() {
		response = mock(Response.class);
		when(response.status()).thenReturn(HttpStatus.OK_200);
		when(response.headers()).thenReturn(new HttpHeaders(new HttpHeader("Content-type", "text/plain")));
		when(response.version()).thenReturn(HttpVersion.HTTP_1_1);
		when(response.stream()).thenReturn(new ByteArrayInputStream("Hello world!".getBytes(UTF_8)));
	}

	/**
	 * A deliberately broken OutputStream for testing purposes.
	 */
	private class ExplodingOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
			throw new IOException();
		}
	}

}