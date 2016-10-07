package au.id.deejay.webserver.request;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.HttpVersion;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class RequestLineTest {

	@Test
	public void testRawStringConstructor() throws Exception {
		RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.0");

		assertThat(requestLine.method(), is(HttpMethod.GET));
		assertThat(requestLine.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(requestLine.version(), is(equalTo(HttpVersion.HTTP_1_0)));
	}

	@Test
	public void testExplicitArgumentsConstructor() throws Exception {
		RequestLine requestLine = new RequestLine(HttpMethod.GET, new URI("/index.html"), HttpVersion.HTTP_1_0);

		assertThat(requestLine.method(), is(HttpMethod.GET));
		assertThat(requestLine.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(requestLine.version(), is(equalTo(HttpVersion.HTTP_1_0)));
	}

	@Test
	public void testStarUriIsConsideredValidInRawString() throws Exception {
		new RequestLine("OPTIONS * HTTP/1.1");
	}

	@Test
	public void testExcessWhitespaceIsIgnoredInRawString() throws Exception {
		RequestLine requestLine = new RequestLine("  GET   /index.html   HTTP/1.0  ");

		assertThat(requestLine.method(), is(HttpMethod.GET));
		assertThat(requestLine.uri(), is(equalTo(new URI("/index.html"))));
		assertThat(requestLine.version(), is(equalTo(HttpVersion.HTTP_1_0)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRawStringThrowsException() throws Exception {
		new RequestLine("GET /index.html");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHttpMethodIsCaseSensitive() throws Exception {
		new RequestLine("get /index.html HTTP/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMethodInRawStringThrowsException() throws Exception {
		new RequestLine("BAD /index.html HTTP/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidUriInRawStringThrowsException() throws Exception {
		new RequestLine("GET @#$% HTTP/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidVersionInRawStringThrowsException() throws Exception {
		new RequestLine("GET /index.html HTTP/badversion");
	}

	@Test
	public void testHashCode() throws Exception {
		RequestLine requestLine1 = new RequestLine("GET /index.html HTTP/1.0");
		RequestLine requestLine2 = new RequestLine("GET /index.html HTTP/1.0");

		assertThat(requestLine1.hashCode(), is(equalTo(requestLine2.hashCode())));
		assertThat(requestLine1.hashCode(), is(not(equalTo(HttpVersion.HTTP_1_1.hashCode()))));
	}

	@Test
	public void testEquals() throws Exception {
		RequestLine requestLine1 = new RequestLine("GET /index.html HTTP/1.0");
		RequestLine requestLine2 = new RequestLine("GET /index.html HTTP/1.0");

		assertThat(requestLine1.equals(requestLine2), is(true));
	}
}