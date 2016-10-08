package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author David Jessup
 */
public class UnmodifiableHttpHeader implements Header {

	private Header origin;

	public UnmodifiableHttpHeader(Header origin) {
		this.origin = origin;
	}

	@Nonnull
	@Override
	public String name() {
		return origin.name();
	}

	@CheckForNull
	@Override
	public String value() {
		return origin.value();
	}

	@Nonnull
	@Override
	public List<String> values() {
		return origin.values();
	}

	@Nonnull
	@Override
	public Header add(String value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Header add(String... newValues) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Header set(String value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Header set(String... values) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public Header remove(String value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	@Override
	public String toString() {
		return origin.toString();
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
}
