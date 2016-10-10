package au.id.deejay.webserver.io;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.exception.RequestException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static au.id.deejay.webserver.MessageConstants.CRLF;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author David Jessup
 */
public class RequestReaderTest {

	private RequestReader requestReader;

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Test
	public void testReadIsPassedThroughToUnderlyingStream() throws Exception {
		InputStream inputStream = mock(InputStream.class);
		requestReader = new RequestReader(inputStream);
		requestReader.read(new char[]{}, 0, 0);

		verify(inputStream).read(any(), anyInt(), anyInt());
	}

	@Test
	public void testCloseIsPassedThroughToUnderlyingStream() throws Exception {
		InputStream inputStream = mock(InputStream.class);
		requestReader = new RequestReader(inputStream);
		requestReader.close();

		verify(inputStream).close();
	}

	@Test
	public void testReadValidRequestWithoutEntityBody() throws Exception {
		String rawRequest = "GET /index.html HTTP/1.1" + CRLF
				+ "Connection: keep-alive" + CRLF
				+ CRLF;

		InputStream inputStream = new ByteArrayInputStream(rawRequest.getBytes(UTF_8));

		requestReader = new RequestReader(inputStream);

		Request request = requestReader.readRequest();

		assertThat(request.method(), is(HttpMethod.GET));
		assertThat(request.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(request.version(), is(HttpVersion.HTTP_1_1));
	}

	@Test
	public void testReadValidRequestWithEntityBody() throws Exception {
		String rawRequest = "POST /index.html HTTP/1.1" + CRLF
				+ "Content-length: 11" + CRLF
				+ "Connection: close" + CRLF
				+ CRLF
				+ "Entity-body";

		InputStream inputStream = new ByteArrayInputStream(rawRequest.getBytes(UTF_8));

		requestReader = new RequestReader(inputStream);

		Request request = requestReader.readRequest();

		assertThat(request.method(), is(HttpMethod.POST));
		assertThat(request.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(request.version(), is(HttpVersion.HTTP_1_1));
		assertThat(IOUtils.contentEquals(request.stream(), new ByteArrayInputStream("Entity-body".getBytes(StandardCharsets.UTF_8))), is(true));
	}

	@Test(expected = RequestException.class)
	public void testReadRequestWithInvalidHeaderThrowsException() throws Exception {
		String rawRequest = "GET /index.html HTTP/1.1" + CRLF
				+ "Bad-Header" + CRLF
				+ CRLF;

		InputStream inputStream = new ByteArrayInputStream(rawRequest.getBytes(UTF_8));

		requestReader = new RequestReader(inputStream);

		Request request = requestReader.readRequest();
	}

	@Test(expected = SocketTimeoutException.class)
	public void testSocketTimeoutsArePropagated() throws Exception {
		InputStream inputStream = mock(InputStream.class);
		when(inputStream.read(any(), anyInt(), anyInt())).thenThrow(SocketTimeoutException.class);
		requestReader = new RequestReader(inputStream);
		requestReader.readRequest();
	}
}