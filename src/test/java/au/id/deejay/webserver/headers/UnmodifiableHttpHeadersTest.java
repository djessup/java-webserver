package au.id.deejay.webserver.headers;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
@SuppressWarnings("Duplicates")
public class UnmodifiableHttpHeadersTest {

	private UnmodifiableHttpHeaders headers;

	@Test
	public void testConstructor() throws Exception {
		headers = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value"),
				new HttpHeader("Other", "value")
		));

		assertThat(headers.headers(), hasItems(
				new HttpHeader("Test", "value"),
				new HttpHeader("Other", "value")
		));
	}

	@Test
	public void testSize() throws Exception {
		withHeaders();

		assertThat(headers.size(), is(2));
	}

	@Test
	public void testContains() throws Exception {
		withHeaders();

		assertThat(headers.contains("Test"), is(true));
		assertThat(headers.contains("TEST"), is(true));
		assertThat(headers.contains("Other"), is(true));
		assertThat(headers.contains("Missing"), is(false));
	}

	@Test
	public void testValue() throws Exception {
		withHeaders();

		assertThat(headers.value("Test"), is(equalTo("value1")));
		assertThat(headers.value("Other"), is(equalTo("value")));
		assertThat(headers.value("Missing"), is(nullValue()));
	}

	@Test
	public void testValues() throws Exception {
		headers = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value"),
				new HttpHeader("Empty")
		));

		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));
		assertThat(headers.values("Other"), is(equalTo(Arrays.asList("value"))));
		assertThat(headers.values("Empty"), is(equalTo(Collections.emptyList())));
		assertThat(headers.values("Missing"), is(nullValue()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddSingleHeaderObjectThrowsException() throws Exception {
		withHeaders();

		headers.add(new HttpHeader("Test"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddSingleKeyValuePairHeaderThrowsException() throws Exception {
		withHeaders();

		headers.add("Test", "value");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddMultipleKeyValuePairHeaderThrowsException() throws Exception {
		withHeaders();

		headers.add("Test", "value3", "value4");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetSingleHeaderObjectThrowsException() throws Exception {
		withHeaders();

		headers.set(new HttpHeader("Test"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetSingleKeyValuePairHeaderThrowsException() throws Exception {
		withHeaders();

		headers.set("Test", "value");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetMultipleKeyValuePairHeaderThrowsException() throws Exception {
		withHeaders();

		headers.set("Test", "value3", "value4");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveThrowsException() throws Exception {
		withHeaders();

		headers.remove("Test");
	}

	@Test
	public void testHeader() throws Exception {
		withHeaders();

		assertThat(headers.header("Test"), is(equalTo(new HttpHeader("Test", "value1", "value2"))));
		assertThat(headers.header("TEST"), is(equalTo(new HttpHeader("Test", "value1", "value2"))));
	}

	@Test
	public void testNames() throws Exception {
		withHeaders();

		assertThat(headers.names(), hasItems("test", "other"));
	}

	@Test
	public void testSimpleEquals() throws Exception {
		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));

		assertThat(headers1.equals(headers2), is(true));

		Headers headers3 = new UnmodifiableHttpHeaders(new HttpHeaders(new HttpHeader("Different", "value")));
		assertThat(headers1.equals(headers3), is(false));
	}

	@Test
	public void testEqualsIgnoresCaseOfHeaderNames() throws Exception {
		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("TEST", "value1", "value2"),
				new HttpHeader("OTHER", "value1", "value2")
		));

		assertThat(headers1.equals(headers2), is(true));
	}

	@Test
	public void testEqualsIgnoresOrderOfHeaderValues() throws Exception {
		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		));

		assertThat(headers1.equals(headers2), is(true));
	}

	@Test
	public void testSimpleHashCode() throws Exception {

		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		));

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}


	@Test
	public void testHashCodeIgnoresCaseOfHeaderNames() throws Exception {
		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("TEST", "value1", "value2"),
				new HttpHeader("OTHER", "value1", "value2")
		));

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}

	@Test
	public void testHashcodeIgnoresOrderOfHeaderValues() throws Exception {
		Headers headers1 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		));
		Headers headers2 = new UnmodifiableHttpHeaders(new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		));

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}

	@Test
	public void testToString() throws Exception {
		withHeaders();

		assertThat(headers.toString(), is(equalTo("Test: value1,value2\r\nOther: value\r\n")));
	}


	private void withHeaders() {
		headers = new UnmodifiableHttpHeaders(
				new HttpHeaders(
						new HttpHeader("Test", "value1", "value2"),
						new HttpHeader("Other", "value")
				)
		);
	}
}