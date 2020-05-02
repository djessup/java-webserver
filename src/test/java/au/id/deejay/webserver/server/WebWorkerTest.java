package au.id.deejay.webserver.server;

import au.id.deejay.webserver.StringCollectorOutputStream;
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.exception.ResponseException;
import au.id.deejay.webserver.io.ResponseWriter;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.HttpResponse;
import au.id.deejay.webserver.response.ResponseFactory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static au.id.deejay.webserver.MessageConstants.CRLF;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
public class WebWorkerTest {

	private Socket socket;
	private ResponseFactory responseFactory;
	private WebWorker worker;
	private String requestString = "";
	private OutputStream responseStream;
	private Response response;


	@Test
	public void testConstructor() throws Exception {
		withSocket();
		withResponseFactory();

		new WebWorker(socket, responseFactory);
	}

	@Test
	public void testValidRequestIsHandledByResponseFactory() throws Exception {
		withResponseFactory();
		withValidRequest();
		withSocket();
		withWorker();

		worker.run();

		verify(responseFactory).response(any());
		assertResponseWasSentToClient();
	}

	@Test
	public void testWorkerAbortsIfClientSocketIsBroken() throws Exception {
		withBrokenSocket();
		withResponseFactory();
		withWorker();

		worker.run();

		verifyZeroInteractions(responseFactory);
	}

	@Test
	public void testBadRequestReturns400Error() throws Exception {
		withResponseFactory();
		withBadRequest();
		withSocket();
		withWorker();

		worker.run();

		assertResponseWasSentToClient(Collections.singletonList(ErrorResponse.BAD_REQUEST_400));
	}

	@Test
	public void testWorkerAbortsIfClientSocketTimesOut() throws Exception {
		withResponseFactory();
		withValidRequest();
		withTimeoutSocket();
		withWorker();

		worker.run();

		verifyZeroInteractions(responseFactory);
	}

	@Test
	public void testErrorGeneratingResponseReturns500Error() throws Exception {
		withBrokenResponseFactory();
		withValidRequest();
		withSocket();
		withWorker();

		worker.run();

		assertResponseWasSentToClient(ErrorResponse.INTERNAL_SERVER_ERROR_500);
	}

	@Test
	public void testKeepAliveBehaviour() throws Exception {
		withResponseFactory();
		withKeepAliveRequests();
		withSocket();
		withWorker();

		worker.run();

		assertResponseWasSentToClient(Arrays.asList(this.response, this.response));
	}


	private void assertResponseWasSentToClient() {
		assertResponseWasSentToClient(this.response);
	}

	private void assertResponseWasSentToClient(Response response) {
		assertResponseWasSentToClient(Collections.singletonList(response));
	}

	private void assertResponseWasSentToClient(Collection<Response> responses) {
		OutputStream outputStream = new StringCollectorOutputStream();
		ResponseWriter responseWriter = new ResponseWriter(outputStream);
		for (Response response : responses) {
			responseWriter.writeResponse(response);
		}

		assertThat(responseStream.toString(), is(equalTo(outputStream.toString())));
	}

	private void withValidRequest() throws Exception {
		requestString = "GET /index.html HTTP/1.1" + CRLF
			+ "Connection: close" + CRLF
			+ CRLF;

		response = new HttpResponse(HttpStatus.OK_200, "Test response", HttpVersion.HTTP_1_1);
	}

	private void withKeepAliveRequests() throws Exception {
		requestString = "GET /index.html HTTP/1.1" + CRLF
			+ "Connection: keep-alive" + CRLF
			+ CRLF
			+ "GET /index.html HTTP/1.1" + CRLF
			+ "Connection: close" + CRLF
			+ CRLF;

		response = new HttpResponse(HttpStatus.OK_200, "Test response", HttpVersion.HTTP_1_1);
	}

	private void withBadRequest() throws Exception {
		requestString = "hurr durr" + CRLF
			+ "derp derp derp" + CRLF
			+ CRLF;
	}

	private void withSocket() throws Exception {
		socket = mock(Socket.class);
		responseStream = new StringCollectorOutputStream();

		when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8)));
		when(socket.getOutputStream()).thenReturn(responseStream);
	}

	private void withResponseFactory() {
		responseFactory = mock(ResponseFactory.class);
		when(responseFactory.response(any(Request.class))).thenAnswer(invocationOnMock -> this.response);
	}

	private void withBrokenResponseFactory() {
		responseFactory = mock(ResponseFactory.class);
		doAnswer(invocationOnMock -> { throw new ResponseException(); }).when(responseFactory).response(any());
	}

	private void withWorker() {
		worker = new WebWorker(socket, responseFactory);
	}

	private void withBrokenSocket() throws Exception {
		socket = mock(Socket.class);
		when(socket.getInputStream()).thenThrow(IOException.class);
		when(socket.getOutputStream()).thenThrow(IOException.class);
	}

	private void withTimeoutSocket() throws Exception {
		socket = mock(Socket.class);
		InputStream inputStream = mock(InputStream.class);
		when(inputStream.read(any(), anyInt(), anyByte())).thenThrow(SocketTimeoutException.class);
		when(socket.getInputStream()).thenReturn(inputStream);
		responseStream = new StringCollectorOutputStream();
		when(socket.getOutputStream()).thenReturn(responseStream);
	}

}