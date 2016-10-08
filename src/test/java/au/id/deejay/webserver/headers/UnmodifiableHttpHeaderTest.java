package au.id.deejay.webserver.headers;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class UnmodifiableHttpHeaderTest {

	private Header header;

	@Test
	public void name() throws Exception {
		withTestHeader();

		assertThat(header.name(), is(equalTo("Test")));
	}

	@Test
	public void value() throws Exception {
		withTestHeader();

		assertThat(header.value(), is(equalTo("value")));
	}

	@Test
	public void values() throws Exception {
		withTestHeader();

		assertThat(header.values(), is(equalTo(Collections.singletonList("value"))));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddSingleThrowsException() throws Exception {
		withTestHeader();

		header.add("value");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddMultipleThrowsException() throws Exception {
		withTestHeader();

		header.add("value1", "value2");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetSingleThrowsException() throws Exception {
		withTestHeader();

		header.set("value");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetMultipleThrowsException() throws Exception {
		withTestHeader();

		header.set("value1", "value2");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveThrowsException() throws Exception {
		withTestHeader();

		header.remove("value");
	}

	@Test
	public void testToString() throws Exception {
		withTestHeader();

		assertThat(header.toString(), is(equalTo("Test: value")));
	}

	@Test
	public void testEquals() throws Exception {
		withTestHeader();

		assertThat(header.equals(new HttpHeader("Test", "value")), is(true));
		assertThat(header.equals(new HttpHeader("TEST", "value")), is(true));
		assertThat(header.equals(new UnmodifiableHttpHeader(new HttpHeader("Test", "value"))), is(true));
	}

	@Test
	public void testHashCode() throws Exception {
		Header header1 = new UnmodifiableHttpHeader(new HttpHeader("Test", "value1", "value2"));
		Header header2 = new UnmodifiableHttpHeader(new HttpHeader("Test", "value1", "value2"));

		assertThat(header1.hashCode(), is(equalTo(header2.hashCode())));
	}

	private void withTestHeader() {
		header = new UnmodifiableHttpHeader(new HttpHeader("Test", "value"));
	}
}