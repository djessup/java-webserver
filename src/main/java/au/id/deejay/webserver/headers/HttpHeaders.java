package au.id.deejay.webserver.headers;


import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static au.id.deejay.webserver.MessageConstants.CRLF;

/**
 * A collection of HTTP {@link Header}s.
 *
 * @author David Jessup
 */
public class HttpHeaders implements Headers {

	private Map<String, Header> headers;

	/**
	 * Creates a collection of {@link Headers} containing the {@link Header}s provided.
	 *
	 * @param headers an array of headers to include in the collection
	 */
	public HttpHeaders(Header... headers) {
		this();
		for (Header header : headers) {
			add(header);
		}
	}

	/**
	 * Creates a new empty {@link Headers} collection.
	 */
	public HttpHeaders() {
		headers = new LinkedHashMap<>();
	}

	private Header getModifiableHeader(String key) {
		return headers.get(key.toLowerCase());
	}

	@Override
	public int size() {
		return headers.size();
	}

	@Override
	public boolean contains(String key) {
		return headers.containsKey(key.toLowerCase());
	}

	@CheckForNull
	@Override
	public String value(String key) {
		return contains(key) ? getModifiableHeader(key).value() : null;
	}

	@CheckForNull
	@Override
	public List<String> values(String key) {
		return contains(key) ? getModifiableHeader(key).values() : null;
	}

	@Nonnull
	@Override
	public Headers add(Header header) {
		return add(header.name(), header.values().toArray(new String[]{}));
	}

	@Nonnull
	@Override
	public Headers add(String key, String value) {
		return add(key, new String[]{value});
	}

	@Nonnull
	@Override
	public Headers add(String key, String... values) {
		if (contains(key)) {
			getModifiableHeader(key).add(values);
		} else {
			return set(key, values);
		}

		return this;
	}

	@Nonnull
	@Override
	public Headers set(Header header) {
		headers.put(header.name().toLowerCase(), new HttpHeader(header.name(), header.values()));
		return this;
	}

	@Nonnull
	@Override
	public Headers set(String key, String value) {
		return set(key, new String[]{value});
	}

	@Nonnull
	@Override
	public Headers set(String key, String... values) {
		headers.put(key.toLowerCase(), new HttpHeader(key, values));
		return this;
	}

	@Nonnull
	@Override
	public Headers remove(String key) {
		headers.remove(key.toLowerCase());
		return this;
	}

	@CheckForNull
	@Nonnull
	@Override
	public Header header(String key) {
		return new UnmodifiableHttpHeader(getModifiableHeader(key));
	}

	@Nonnull
	@Override
	public List<Header> headers() {
		return Collections.unmodifiableList(
				headers.values().stream()
						.map(UnmodifiableHttpHeader::new)
						.collect(Collectors.toList())
		);
	}

	@Nonnull
	@Override
	public Set<String> names() {
		return Collections.unmodifiableSet(headers.keySet());
	}

	@Override
	public int hashCode() {
		return headers().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Headers
				&& headers().equals(((Headers) obj).headers());
	}

	@Nonnull
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (Header header : headers.values()) {
			output.append(header.toString())
					.append(CRLF);
		}
		return output.toString();
	}
}
