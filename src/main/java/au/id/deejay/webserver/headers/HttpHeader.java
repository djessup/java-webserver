package au.id.deejay.webserver.headers;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * A single HTTP header.
 *
 * @author David Jessup
 */
public class HttpHeader implements Header {

	private String name;
	private List<String> values;

	/**
	 * Creates a new {@link Header} with no values.
	 *
	 * @param name the name of the header
	 * @throws IllegalArgumentException if the name is blank.
	 */
	public HttpHeader(String name) {
		this(name, new ArrayList<>());
	}

	/**
	 * Creates a new multi-valued {@link Header}.
	 *
	 * @param name   the name of the header
	 * @param values a collection of header values
	 * @throws IllegalArgumentException if the name is blank.
	 */
	public HttpHeader(String name, Collection<String> values) {

		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Name must not be empty.");
		}

		this.name = name;
		this.values = new ArrayList<>(values);
	}

	/**
	 * Creates a new single-values {@link Header}.
	 *
	 * @param name  the name of the header
	 * @param value the header value
	 * @throws IllegalArgumentException if the name is blank.
	 */
	public HttpHeader(String name, String value) {
		this(name, Collections.singletonList(value));
	}

	/**
	 * Creates a new multi-valued {@link Header}.
	 *
	 * @param name   the name of the header
	 * @param values an array of header values
	 * @throws IllegalArgumentException if the name is blank.
	 */
	public HttpHeader(String name, String... values) {
		this(name, Arrays.asList(values));
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

	/**
	 * Gets the {@link Header}'s hash code, taking into account case-insensitivity of the name and order-insensitivity
	 * of the values.
	 *
	 * @return Returns the hash code.
	 */
	@Override
	public int hashCode() {
		List<String> sortedValues = new ArrayList<>(values);
		sortedValues.sort(String::compareTo);
		return Objects.hash(name.toLowerCase(), sortedValues);
	}

	/**
	 * A header is considered equal if it has the same name (ignoring case) and same values (in any order).
	 *
	 * @param obj the other object to compare
	 * @return Returns true if the other object is a {@link Header} which has the same name and values.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Header
				&& name.equalsIgnoreCase(((Header) obj).name())
				&& values.size() == ((Header) obj).values().size()
				&& ((Header) obj).values().containsAll(values);
	}

	/**
	 * Outputs the header in RFC2616 format "Header-Name: header-value,additional-value"
	 *
	 * @return Returns the header name and values formatted as per RFC2616.
	 */
	@Nonnull
	@Override
	public String toString() {
		return name + ": " + String.join(",", values);
	}
}
