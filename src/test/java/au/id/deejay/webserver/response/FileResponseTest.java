package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.exception.ResponseException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
public class FileResponseTest {

	private File file;
	private FileResponse response;

	@Test
	public void testStream() throws Exception {
		withIndexFile();
		withResponse();

		assertThat(response.status(), is(HttpStatus.OK_200));
		assertThat(response.version(), is(HttpVersion.HTTP_1_1));
		assertThat(IOUtils.contentEquals(response.stream(), new FileInputStream(file)), is(true));
	}

	@Test(expected = ResponseException.class)
	public void testMissingFileThrowsException() throws Exception {
		file = new File("/not/a/file");
		withResponse();
	}

	/**
	 * This is really an edge case that is unlikely to occur. For the stream() to throw an exception would mean the file
	 * existed and was valid at the time of construction, but was deleted before the response was streamed.
	 * Still, for the sake of test coverage...
	 */
	@Test(expected = ResponseException.class)
	public void testMissingFileStreamThrowsException() throws Exception {
		withIndexFile();
		withResponse();
		withInvalidFile();

		response.stream();
	}

	@Test
	public void testContentLengthHeader() throws Exception {
		withIndexFile();
		withResponse();

		assertThat(response.headers().contains("Content-length"), is(true));
		assertThat(response.headers().value("Content-length"), is(String.valueOf(file.length())));
	}

	@Test
	public void testContentTypeHeader() throws Exception {
		withIndexFile();
		withResponse();

		assertThat(response.headers().contains("Content-type"), is(true));
		assertThat(response.headers().value("Content-type"), is("text/html"));
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

	private void withIndexFile() throws Exception {
		file = spy(getDocrootFile("/index.html"));
	}

	private void withInvalidFile() throws Exception {
		// Cause File.isInvalid() to return false
		when(file.getPath()).thenReturn(new char[]{'\u0000'}.toString());
		file = new File("does-not-exist");
	}
	private void withResponse() {
		response = new FileResponse(file, HttpVersion.HTTP_1_1);
	}

}