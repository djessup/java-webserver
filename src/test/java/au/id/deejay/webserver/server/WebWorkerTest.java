package au.id.deejay.webserver.server;

import au.id.deejay.webserver.StringCollectorOutputStream;
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.response.HttpResponse;
import au.id.deejay.webserver.response.ResponseFactory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	public void testValidRequestHandling() throws Exception {
		withSocket();
		withResponseFactory();
		withValidRequest();
		withWorker();

		worker.run();

		assertThat(response.status(), is(HttpStatus.OK_200));
	}

	private void withValidRequest() throws Exception {
		requestString = "GET /index.html HTTP/1.1\n" +
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
			"\n";

		response = new HttpResponse(HttpStatus.OK_200, "Test response", HttpVersion.HTTP_1_1);
		when(responseFactory.response(any())).thenReturn(response);
	}

	private void withSocket() throws Exception {
		socket = mock(Socket.class);
		responseStream = new StringCollectorOutputStream();

		when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8)));
		when(socket.getOutputStream()).thenReturn(responseStream);
	}

	private void withResponseFactory() {
		responseFactory = mock(ResponseFactory.class);
	}

	private void withWorker() {
		worker = new WebWorker(socket, responseFactory);
	}
}