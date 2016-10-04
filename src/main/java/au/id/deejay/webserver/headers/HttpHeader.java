package au.id.deejay.webserver.headers;

import au.id.deejay.webserver.spi.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author David Jessup
 */
public class HttpHeader implements Header {

	private String name;
	private List<String> values;

	public HttpHeader(String name) {
		this.name = name;
		this.values = new ArrayList<>();
	}

	public HttpHeader(String name, String value) {
		this.name = name;
		this.values = new ArrayList<>();
		this.values.add(value);
	}

	public HttpHeader(String name, String... values) {
		this(name, Arrays.asList(values));
	}

	public HttpHeader(String name, List<String> values) {
		this.name = name;
		this.values = values;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String value() {
		return !values.isEmpty() ? values.get(0) : null;
	}

	@Override
	public List<String> values() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public Header add(String value) {
		values.add(value);
		return this;
	}

	@Override
	public Header add(String... values) {
		Collections.addAll(this.values, values);
		return this;
	}

	@Override
	public Header set(String value) {
		this.values.clear();
		this.values.add(value);
		return this;
	}

	@Override
	public Header set(String... values) {
		this.values().clear();
		Collections.addAll(this.values, values);
		return this;
	}

	@Override
	public Header remove(String value) {
		values.remove(value);
		return this;
	}

	@Override
	public String toString() {
		return name + ": " + String.join(",", values);
	}
}
