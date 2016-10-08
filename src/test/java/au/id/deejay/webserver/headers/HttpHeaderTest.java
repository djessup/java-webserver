package au.id.deejay.webserver.headers;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author David Jessup
 */
public class HttpHeaderTest {

	private Header header;

	@Test
	public void testNoValueConstructor() throws Exception {
		header = new HttpHeader("Test");

		assertThat(header.name(), is(equalTo("Test")));
		assertThat(header.values(), is(equalTo(Collections.emptyList())));
	}

	@Test
	public void testSingleValueConstructor() throws Exception {
		header = new HttpHeader("Test", "value");

		assertThat(header.name(), is(equalTo("Test")));
		assertThat(header.values(), is(equalTo(Collections.singletonList("value"))));
	}

	@Test
	public void testArrayValuesConstructor() throws Exception {
		header = new HttpHeader("Test", "value1", "value2", "value3");

		assertThat(header.name(), is(equalTo("Test")));
		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value2", "value3"))));
	}

	@Test
	public void testCollectionValuesConstructor() throws Exception {
		header = new HttpHeader("Test", Sets.newSet("value1", "value2", "value3"));

		assertThat(header.name(), is(equalTo("Test")));
		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value2", "value3"))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullNameThrowsException() throws Exception {
		new HttpHeader(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBlankNameThrowsException() throws Exception {
		new HttpHeader("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyNameThrowsException() throws Exception {
		new HttpHeader(" ");
	}

	@Test
	public void testSingleValue() throws Exception {
		header = new HttpHeader("Test", "value");

		assertThat(header.value(), is(equalTo("value")));
	}

	@Test
	public void testMultipleValuesReturnsFirstValue() throws Exception {
		header = new HttpHeader("Test", Sets.newSet("value1", "value2", "value3"));

		assertThat(header.value(), is(equalTo("value1")));
	}

	@Test
	public void testNoValuesReturnsNull() throws Exception {
		header = new HttpHeader("Test");

		assertThat(header.value(), is(nullValue()));
	}

	@Test
	public void testValues() throws Exception {
		header = new HttpHeader("Test", "value1", "value2", "value3");

		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value2", "value3"))));
	}

	@Test
	public void testNoValuesReturnsEmptyList() throws Exception {
		header = new HttpHeader("Test");

		assertThat(header.values(), is(equalTo(Collections.emptyList())));
	}

	@Test
	public void testAddSingleValue() throws Exception {
		header = new HttpHeader("Test");

		header.add("value1");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1"))));

		header.add("value2");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value2"))));
	}

	@Test
	public void testAddMultipleValues() throws Exception {
		header = new HttpHeader("Test");

		header.add("value1");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1"))));

		header.add("value2", "value3");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value2", "value3"))));
	}

	@Test
	public void testSetSingleValue() throws Exception {
		header = new HttpHeader("Test");

		header.set("value1");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1"))));

		header.set("value2");
		assertThat(header.values(), is(equalTo(Arrays.asList("value2"))));
	}

	@Test
	public void testSetMultipleValues() throws Exception {
		header = new HttpHeader("Test");

		header.set("value1");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1"))));

		header.set("value2", "value3");
		assertThat(header.values(), is(equalTo(Arrays.asList("value2", "value3"))));
	}

	@Test
	public void testRemoveValue() throws Exception {
		header = new HttpHeader("Test", "value1", "value2", "value3");

		header.remove("value2");
		assertThat(header.values(), is(equalTo(Arrays.asList("value1", "value3"))));
	}

	@Test
	public void testSingleValueToString() throws Exception {
		header = new HttpHeader("Test", "value");

		assertThat(header.toString(), is(equalTo("Test: value")));
	}

	@Test
	public void testMultipleValuesToString() throws Exception {
		header = new HttpHeader("Test", "value1", "value2", "value3");

		assertThat(header.toString(), is(equalTo("Test: value1,value2,value3")));
	}

	@Test
	public void testNoValueToString() throws Exception {
		header = new HttpHeader("Test");

		assertThat(header.toString(), is(equalTo("Test: ")));
	}

	@SuppressWarnings("EqualsBetweenInconvertibleTypes")
	@Test
	public void testSimpleEquals() throws Exception {
		header = new HttpHeader("Test", "value1", "value2");
		
		assertThat(header.equals(new HttpHeader("Test", "value1", "value2")), is(true));
		assertThat(header.equals(new HttpHeader("Other", "value1", "value2")), is(false));
		assertThat(header.equals("Not a Header object"), is(false));
	}

	@Test
	public void testEqualsMatchesOtherInterfaceImplementations() throws Exception {
		header = new HttpHeader("Test", "value1", "value2");
		
		assertThat(header.equals(new UnmodifiableHttpHeader(new HttpHeader("Test", "value1", "value2"))), is(true));
	}

	@Test
	public void testEqualsIgnoresCaseOfName() throws Exception {
		header = new HttpHeader("Test", "value1", "value2");

		assertThat(header.equals(new HttpHeader("TEST", "value1", "value2")), is(true));
	}

	@Test
	public void testEqualsIgnoresOrderOfValues() throws Exception {
		header = new HttpHeader("Test", "value1", "value2");

		assertThat(header.equals(new HttpHeader("Test", "value2", "value1")), is(true));
	}

	@Test
	public void testSimpleHashCode() throws Exception {
		Header header1 = new HttpHeader("Test", "value1", "value2");
		Header header2 = new HttpHeader("Test", "value1", "value2");

		assertThat(header1.hashCode(), is(equalTo(header2.hashCode())));
		assertThat(header1.hashCode(), is(not(equalTo(new HttpHeader("Other", "value1", "value2")))));
	}

	@Test
	public void testHashCodeWithDifferentCaseNames() throws Exception {
		Header header1 = new HttpHeader("Test", "value1", "value2");
		Header header2 = new HttpHeader("TEST", "value1", "value2");

		assertThat(header1.hashCode(), is(equalTo(header2.hashCode())));
	}

	@Test
	public void testHashCodeWithDifferentOrderValues() throws Exception {
		Header header1 = new HttpHeader("Test", "value1", "value2");
		Header header2 = new HttpHeader("Test", "value2", "value1");

		assertThat(header1.hashCode(), is(equalTo(header2.hashCode())));
	}
}