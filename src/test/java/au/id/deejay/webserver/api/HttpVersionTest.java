package au.id.deejay.webserver.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class HttpVersionTest {

	@Test
	public void testStaticVersions() throws Exception {
		assertThat(HttpVersion.HTTP_1_0.major(), is(equalTo(1)));
		assertThat(HttpVersion.HTTP_1_0.minor(), is(equalTo(0)));

		assertThat(HttpVersion.HTTP_1_1.major(), is(equalTo(1)));
		assertThat(HttpVersion.HTTP_1_1.minor(), is(equalTo(1)));
	}

	@Test
	public void testExplicitConstructor() throws Exception {
		HttpVersion version = new HttpVersion(1, 0);

		assertThat(version.major(), is(equalTo(1)));
		assertThat(version.minor(), is(equalTo(0)));
	}

	@Test
	public void testStringConstructor() throws Exception {
		HttpVersion version = new HttpVersion("HTTP/1.0");

		assertThat(version.major(), is(equalTo(1)));
		assertThat(version.minor(), is(equalTo(0)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringConstructorWithInvalidString() throws Exception {
		new HttpVersion("invalid version");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringConstructorWithInvalidProtocol() throws Exception {
		new HttpVersion("FTP/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringConstructorWithInvalidVersion() throws Exception {
		new HttpVersion("HTTP/badversion");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringConstructorWithInvalidMajorVersion() throws Exception {
		new HttpVersion("HTTP/bad.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringConstructorWithInvalidMinorVersion() throws Exception {
		new HttpVersion("HTTP/1.bad");
	}

	@Test
	public void testCompare() throws Exception {
		assertThat(HttpVersion.HTTP_1_0.compareTo(HttpVersion.HTTP_1_0), is(equalTo(0)));
		assertThat(HttpVersion.HTTP_1_1.compareTo(HttpVersion.HTTP_1_0), is(equalTo(1)));
		assertThat(HttpVersion.HTTP_1_0.compareTo(HttpVersion.HTTP_1_1), is(equalTo(-1)));
		assertThat(new HttpVersion(2, 0).compareTo(HttpVersion.HTTP_1_1), is(equalTo(1)));
	}

	@Test
	public void testEquals() throws Exception {
		assertThat(HttpVersion.HTTP_1_0.equals(HttpVersion.HTTP_1_0), is(true));
		assertThat(HttpVersion.HTTP_1_0.equals(HttpVersion.HTTP_1_1), is(false));
		assertThat(HttpVersion.HTTP_1_0.equals("not a version object"), is(false));
	}

	@Test
	public void testHashCode() throws Exception {
		HttpVersion version1 = new HttpVersion(1, 0);
		HttpVersion version2 = new HttpVersion(1, 0);

		assertThat(version1.hashCode(), is(equalTo(version2.hashCode())));
		assertThat(version1.hashCode(), is(not(equalTo(HttpVersion.HTTP_1_1.hashCode()))));
	}

	@Test
	public void testToString() throws Exception {
		assertThat(HttpVersion.HTTP_1_0.toString(), is(equalTo("HTTP/1.0")));
	}
}