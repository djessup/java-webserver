package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.exception.ResponseException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	@Test(expected = ResponseException.class)
	public void testMissingFileStreamThrowsException() throws Exception {
		withIndexFile();
		withResponse();

		// Simulate the file being removed in between the request construction and stream call with a
		// FileNotFoundException. This will propogate via the FileInputStream constructor, where the real exception
		// would be emitted from in a real-world situation.

		// Simply using an invalid path won't work because the FileResponse constructor will throw an exception before
		// stream() can be called.

		doThrow(FileNotFoundException.class).when(file).getPath();

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

	@Test(expected = ResponseException.class)
	public void testUndetectableContentTypeThrowException() throws Exception {
		withIndexFile();

		// Another cludged exception to simulate a real-world error state when attempting to determine the MIME type of
		// a file.
		when(file.toURI()).thenThrow(IOException.class);

		withResponse();
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

	private void withResponse() throws Exception {
		response = new FileResponse(file, HttpVersion.HTTP_1_1);
	}
}