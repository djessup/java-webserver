package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author David Jessup
 */
public class DocrootHandlerTest {

	private Request request;

	private DocrootHandler requestHandler;

	@Test(expected = IllegalStateException.class)
	public void testUnreadableDocroot() throws Exception {
		new DocrootHandler("/not/a/real/directory", null, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonDirectoryDocroot() throws Exception {
		new DocrootHandler(getDocrootFile("/index.html").getPath(), null, false);
	}

	@Test
	public void testCanHandle() throws Exception {
		withHandler();

		withMockGetIndexRequest();
		assertThat(requestHandler.canHandle(request), is(true));

		withMockPostFileRequest();
		assertThat(requestHandler.canHandle(request), is(false));
	}

	@Test
	public void testHandle() throws Exception {
		withHandler();
		withMockGetIndexRequest();

		File indexFile = getDocrootFile("/index.html");

		Response response = requestHandler.handle(request);

		assertThat(response.status(), is(HttpStatus.OK_200));
		assertThat(IOUtils.contentEquals(response.stream(), new FileInputStream(indexFile)), is(true));
	}

	private File getDocrootFile(String path) throws Exception {
		return new File(
				URLDecoder.decode(
						getClass()
								.getResource("/docroot" + path)
								.getFile(),
						StandardCharsets.UTF_8.toString()
				)
		);
	}

	@Test
	public void testHandleNonExistingFile() throws Exception {
		withHandler();
		withMockGetNonExistingFileRequest();

		Response response = requestHandler.handle(request);

		assertThat(response.status(), is(HttpStatus.NOT_FOUND_404));
	}

	private void withHandler() throws Exception {
		String docroot = URLDecoder.decode(getClass().getResource("/docroot").getFile(), StandardCharsets.UTF_8.toString());
		requestHandler = new DocrootHandler(docroot, Collections.singletonList("index.html"), true);
	}

	private void withMockGetIndexRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/index.html"));
	}

	private void withMockGetNonExistingFileRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/missing.html"));
	}

	private void withMockPostFileRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.POST);
		when(request.uri()).thenReturn(new URI("/path/to/file.txt"));
	}

}