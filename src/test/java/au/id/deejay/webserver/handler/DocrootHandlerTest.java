package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.response.DirectoryListingResponse;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.RedirectResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
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
						UTF_8.toString()
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

	@Test
	public void testSlashlessDirectoryRequestsAreRedirected() throws Exception {
		withHandler();
		withMockGetSlashlessDirectoryRequest();

		Response response = requestHandler.handle(request);

		assertThat(response, is(instanceOf(RedirectResponse.class)));
		assertThat(response.status(), is(HttpStatus.MOVED_PERMANENTLY_301));
		assertThat(response.headers().value("Location"), is("/dir/"));
	}

	@Test
	public void testDirectoryRequestsServeIndexFiles() throws Exception {
		withHandler();
		withMockGetDirectoryRequest();

		Response response = requestHandler.handle(request);

		assertThat(response.status(), is(HttpStatus.OK_200));
		assertThat(IOUtils.toString(response.stream(), UTF_8), containsString("Hello world"));
	}

	@Test
	public void testDirectoryWithoutIndexReturnsListing() throws Exception {
		withHandler();
		withMockGetNoIndexFileDirectoryRequest();

		Response response = requestHandler.handle(request);

		assertThat(response, is(instanceOf(DirectoryListingResponse.class)));
	}

	@Test
	public void testDirectoryWithoutIndexAndNoListingsReturns403Error() throws Exception {
		withNoListingHandler();
		withMockGetNoIndexFileDirectoryRequest();

		Response response = requestHandler.handle(request);

		assertThat(response, is(instanceOf(ErrorResponse.class)));
		assertThat(response.status(), is(HttpStatus.FORBIDDEN_403));
	}

	private void withHandler() throws Exception {
		String docroot = URLDecoder.decode(getClass().getResource("/docroot").getFile(), UTF_8.toString());
		requestHandler = new DocrootHandler(docroot, Collections.singletonList("index.html"), true);
	}

	private void withNoListingHandler() throws Exception {
		String docroot = URLDecoder.decode(getClass().getResource("/docroot").getFile(), UTF_8.toString());
		requestHandler = new DocrootHandler(docroot, Collections.singletonList("index.html"), false);
	}

	private void withMockGetIndexRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/index.html"));
	}

	private void withMockGetSlashlessDirectoryRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/dir"));
	}

	private void withMockGetNoIndexFileDirectoryRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/noindexdir/"));
	}

	private void withMockGetDirectoryRequest() throws Exception {
		request = mock(Request.class);
		when(request.method()).thenReturn(HttpMethod.GET);
		when(request.uri()).thenReturn(new URI("/dir/"));
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