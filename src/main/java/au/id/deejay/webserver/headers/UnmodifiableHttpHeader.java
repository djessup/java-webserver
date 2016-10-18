package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * A single, immutable HTTP {@link Header}.
 *
 * @author David Jessup
 */
public class UnmodifiableHttpHeader implements Header {

	private Header origin;

	/**
	 * Creates a new {@link UnmodifiableHttpHeader} from the provided {@link Header}. It will operate exactly the same
	 * as the provided header, except any calls to methods that modify the header will throw an {@link
	 * UnsupportedOperationException}.
	 *
	 * @param origin the existing header
	 */
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

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Header add(String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Header add(String... newValues) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Header set(String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Header set(String... values) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Nonnull
	@Override
	public Header remove(String value) {
		throw new UnsupportedOperationException();
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
