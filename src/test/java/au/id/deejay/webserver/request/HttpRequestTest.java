package au.id.deejay.webserver.request;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpVersion;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.headers.Headers;
import au.id.deejay.webserver.headers.HttpHeader;
import au.id.deejay.webserver.headers.HttpHeaders;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author David Jessup
 */
public class HttpRequestTest {

	private Request request;

	@Test
	public void testBasicConstructor() throws Exception {
		HttpMethod method = HttpMethod.GET;
		URI uri = new URI("/test.html");
		HttpVersion version = HttpVersion.HTTP_1_1;

		RequestLine requestLine = new RequestLine(method, uri, version);
		Headers headers = new HttpHeaders(new HttpHeader("Test", "value"));
		String body = "<h1>Hello world!</h1>";

		request = new HttpRequest(requestLine, headers, body);

		assertThat(request.method(), is(equalTo(method)));
		assertThat(request.uri(), is(equalTo(uri)));
		assertThat(request.version(), is(equalTo(version)));
		assertThat(request.headers(), is(equalTo(headers)));
	}

	@Test
	public void testBodyStreamContainsBodyString() throws Exception {
		String body = "Test\nbody";
		request = new HttpRequest(mock(RequestLine.class), mock(Headers.class), body);

		String streamContents = IOUtils.toString(request.stream(), StandardCharsets.UTF_8);

		assertThat(streamContents, is(equalTo(streamContents)));
	}

}