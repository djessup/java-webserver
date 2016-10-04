package au.id.deejay.webserver.headers;

import au.id.deejay.webserver.spi.Header;
import au.id.deejay.webserver.spi.Headers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	@Override
	public String value(String key) {
		return origin.value(key);
	}

	@Override
	public List<String> values(String key) {
		return origin.values(key);
	}

	@Override
	public Headers add(Header header) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers add(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers add(String key, String... newValues) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers set(Header header) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers set(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers set(String key, String... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Headers remove(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Header header(String key) {
		return origin.contains(key) ? new UnmodifiableHttpHeader(origin.header(key)) : null;
	}

	@Override
	public List<Header> headers() {
		return origin.headers()
				.stream()
				.map(UnmodifiableHttpHeader::new)
				.collect(Collectors.toList());
	}

	@Override
	public Set<String> names() {
		return origin.names();
	}
}


