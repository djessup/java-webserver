package au.id.deejay.webserver.request;

import au.id.deejay.webserver.spi.HttpMethod;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class RequestLineTest {

	@Test
	public void testStringConstructor() throws Exception {
		RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.0");

		assertThat(requestLine.method(), is(HttpMethod.GET));
		assertThat(requestLine.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(requestLine.version(), is(equalTo(HttpVersion.HTTP_1_0)));
	}
}