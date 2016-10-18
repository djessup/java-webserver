package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author David Jessup
 */
public class ServerInfoHandlerTest {

	private Request request;
	private RequestHandler requestHandler;

	@Test
	public void testHandle() throws Exception {
		withHandler();
		withMockServerInfoRequest();

		Response response = requestHandler.handle(request);

		String responseBody = IOUtils.toString(response.stream(), StandardCharsets.UTF_8);

		assertThat(response.status(), is(HttpStatus.OK_200));
		assertThat(response.version(), is(equalTo(request.version())));
		assertThat(responseBody, containsString("/path/to/docroot"));
		assertThat(responseBody, containsString("8080"));
		assertThat(responseBody, containsString("8"));
		assertThat(responseBody, containsString("10s"));
	}

	@Test
	public void testCanHandle() throws Exception {
		withHandler();

		withMockServerInfoRequest();
		assertThat(requestHandler.canHandle(request), is(true));

		withMockGetIndexRequest();
		assertThat(requestHandler.canHandle(request), is(false));

		withMockPostFileRequest();
		assertThat(requestHandler.canHandle(request), is(false));
	}

	private void withHandler() {
		requestHandler = new ServerInfoHandler(8080, 10, 8, "/path/to/docroot", System.currentTimeMillis());
	}

	private void withMockServerInfoRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/serverInfo"));
	}

	private void withMockGetIndexRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/index.html"));
	}

	private void withMockPostFileRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.POST);
		when(request.uri()).thenReturn(new URI("/path/to/file.txt"));
	}
}