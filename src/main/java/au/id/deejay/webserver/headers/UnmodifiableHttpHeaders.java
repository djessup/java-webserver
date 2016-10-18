package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * An immutable collection of immutable headers.
 *
 * @author David Jessup
 */
public class UnmodifiableHttpHeaders implements Headers {

	private Headers origin;

	/**
	 * Creates a new {@link UnmodifiableHttpHeaders} collection from the provided {@link Headers}. The new headers
	 * collection contains the same data as the origin collection, but any attempt to modify it will throw an {@link
	 * UnsupportedOperationException}. Any {@link Header}s provided by this object will also be immutable.
	 *
	 * @param origin the existing header collection
	 */
	public UnmodifiableHttpHeaders(Headers origin) {
		this.origin = origin;
	}

	@Override
	public int size() {
		return origin.size();
	}

	@Override
	public boolean contains(String key) {
		return origin.contains(key);
	}

	@CheckForNull
	@Override
	public String value(String key) {
		return origin.value(key);
	}

	@CheckForNull
	@Override
	public List<String> values(String key) {
		return origin.values(key);
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers add(Header header) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers add(String key, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers add(String key, String... values) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers set(Header header) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers set(String key, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers set(String key, String... values) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Headers remove(String key) {
		throw new UnsupportedOperationException();
	}

	@CheckForNull
	@Override
	public Header header(String key) {
		return origin.header(key);
	}

	@Nonnull
	@Override
	public List<Header> headers() {
		return origin.headers();
	}

	@Nonnull
	@Override
	public Set<String> names() {
		return origin.names();
	}

	@Override
	public int hashCode() {
		return origin.hashCode();
	}

	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	@Override
	public boolean equals(Object obj) {
		return origin.equals(obj);
	}

	@Nonnull
	@Override
	public String toString() {
		return origin.toString();
	}
}


