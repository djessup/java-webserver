package au.id.deejay.webserver.headers;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class HttpHeadersTest {

	private Headers headers;

	@Test
	public void testDefaultConstructor() throws Exception {
		headers = new HttpHeaders();

		assertThat(headers.headers(), is(equalTo(Collections.emptyList())));
	}

	@Test
	public void testConstructorWithValues() throws Exception {
		headers = new HttpHeaders(
				new HttpHeader("Test", "value"),
				new HttpHeader("Other", "value")
		);

		assertThat(headers.headers(), hasItems(
				new HttpHeader("Test", "value"),
				new HttpHeader("Other", "value")
		));
	}

	@Test
	public void testSize() throws Exception {
		headers = new HttpHeaders();

		assertThat(headers.size(), is(0));

		headers.add(new HttpHeader("Test", "value"));
		headers.add(new HttpHeader("Other", "value"));

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
		withHeaders();

		headers.add(new HttpHeader("Empty"));

		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));
		assertThat(headers.values("Empty"), is(equalTo(Collections.emptyList())));
		assertThat(headers.values("Missing"), is(nullValue()));
	}

	@Test
	public void testAddHeaderObject() throws Exception {
		headers = new HttpHeaders();

		headers.add(new HttpHeader("Test", "value1"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1"))));

		headers.add(new HttpHeader("Test", "value2"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));

		headers.add(new HttpHeader("Test", "value2"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2", "value2"))));

		headers.add(new HttpHeader("TEST", "value3", "value4"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2", "value2", "value3", "value4"))));
	}

	@Test
	public void testAddKeyValuePairHeader() throws Exception {
		headers = new HttpHeaders();

		headers.add("Test", "value1");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1"))));

		headers.add("Test", "value2");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));

		headers.add("TEST", "value2");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2", "value2"))));
	}

	@Test
	public void testAddKeyValuePairHeaderWithMultipleValues() throws Exception {
		headers = new HttpHeaders();

		headers.add("Test", "value1", "value2");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));

		headers.add("Test", "value2", "value3");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2", "value2", "value3"))));

		headers.add("TEST", "value4", "value5");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2", "value2", "value3", "value4", "value5"))));

	}

	@Test
	public void testSetHeaderObject() throws Exception {
		headers = new HttpHeaders();

		headers.set(new HttpHeader("Test", "value1"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1"))));

		headers.set(new HttpHeader("Test", "value2"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value2"))));

		headers.set(new HttpHeader("TEST", "value3"));
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value3"))));
	}

	@Test
	public void testSetKeyValuePairHeader() throws Exception {
		headers = new HttpHeaders();

		headers.set("Test", "value1");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1"))));

		headers.set("Test", "value2");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value2"))));

		headers.set("TEST", "value3");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value3"))));
	}


	@Test
	public void testSetKeyValuePairHeaderWithMultipleValues() throws Exception {
		headers = new HttpHeaders();

		headers.set("Test", "value1", "value2");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value1", "value2"))));

		headers.set("Test", "value3", "value4");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value3", "value4"))));

		headers.set("TEST", "value5", "value6");
		assertThat(headers.values("Test"), is(equalTo(Arrays.asList("value5", "value6"))));
	}

	@Test
	public void testRemove() throws Exception {
		withHeaders();

		assertThat(headers.contains("Test"), is(true));

		headers.remove("Test");
		assertThat(headers.contains("Test"), is(false));
	}

	@Test
	public void testRemoveIsCaseInsensitive() throws Exception {
		withHeaders();

		assertThat(headers.contains("Test"), is(true));

		headers.remove("TEST");
		assertThat(headers.contains("Test"), is(false));
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
	public void testToString() throws Exception {
		withHeaders();

		assertThat(headers.toString(), is(equalTo("Test: value1,value2\r\nOther: value\r\n")));
	}

	@Test
	public void testSimpleEquals() throws Exception {
		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);

		assertThat(headers1.equals(headers2), is(true));

		Headers headers3 = new HttpHeaders(new HttpHeader("Different", "value"));
		assertThat(headers1.equals(headers3), is(false));
	}

	@Test
	public void testEqualsIgnoresCaseOfHeaderNames() throws Exception {
		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("TEST", "value1", "value2"),
				new HttpHeader("OTHER", "value1", "value2")
		);

		assertThat(headers1.equals(headers2), is(true));
	}

	@Test
	public void testEqualsIgnoresOrderOfHeaderValues() throws Exception {
		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		);

		assertThat(headers1.equals(headers2), is(true));
	}

	@Test
	public void testSimpleHashCode() throws Exception {

		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		);

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}


	@Test
	public void testHashCodeIgnoresCaseOfHeaderNames() throws Exception {
		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("TEST", "value1", "value2"),
				new HttpHeader("OTHER", "value1", "value2")
		);

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}

	@Test
	public void testHashcodeIgnoresOrderOfHeaderValues() throws Exception {
		Headers headers1 = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value1", "value2")
		);
		Headers headers2 = new HttpHeaders(
				new HttpHeader("Test", "value2", "value1"),
				new HttpHeader("Other", "value2", "value1")
		);

		assertThat(headers1.hashCode(), is(equalTo(headers2.hashCode())));
	}

	private void withHeaders() {
		headers = new HttpHeaders(
				new HttpHeader("Test", "value1", "value2"),
				new HttpHeader("Other", "value")
		);
	}
}