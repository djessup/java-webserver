package au.id.deejay.webserver.headers;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author David Jessup
 */
public class HttpHeader implements Header {

	private String name;
	private List<String> values;

	public HttpHeader(String name) {
		this(name, new ArrayList<>());
	}

	public HttpHeader(String name, String value) {
		this(name, Collections.singletonList(value));
	}

	public HttpHeader(String name, String... values) {
		this(name, Arrays.asList(values));
	}

	public HttpHeader(String name, Collection<String> values) {

		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Name must not be empty.");
		}

		this.name = name;
		this.values = new ArrayList<>(values);
	}

	@Nonnull
	@Override
	public String name() {
		return name;
	}

	@CheckForNull
	@Override
	public String value() {
		return !values.isEmpty() ? values.get(0) : null;
	}

	@Nonnull
	@Override
	public List<String> values() {
		return Collections.unmodifiableList(values);
	}

	@Nonnull
	@Override
	public Header add(String value) {
		values.add(value);
		return this;
	}

	@Nonnull
	@Override
	public Header add(String... values) {
		Collections.addAll(this.values, values);
		return this;
	}

	@Nonnull
	@Override
	public Header set(String value) {
		this.values.clear();
		this.values.add(value);
		return this;
	}

	@Nonnull
	@Override
	public Header set(String... values) {
		this.values.clear();
		Collections.addAll(this.values, values);
		return this;
	}

	@Nonnull
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
