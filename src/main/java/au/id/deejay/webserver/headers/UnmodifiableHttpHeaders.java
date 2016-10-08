package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * @author David Jessup
 */
public class UnmodifiableHttpHeaders implements Headers {

	private Headers origin;

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

	@Nonnull
	@Override
	public Headers add(Header header) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Headers add(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Headers add(String key, String... values) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Headers set(Header header) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Headers set(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Headers set(String key, String... values) {
		throw new UnsupportedOperationException();
	}

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

	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	@Override
	public boolean equals(Object obj) {
		return origin.equals(obj);
	}

	@Override
	public int hashCode() {
		return origin.hashCode();
	}

	@Override
	public String toString() {
		return origin.toString();
	}
}


