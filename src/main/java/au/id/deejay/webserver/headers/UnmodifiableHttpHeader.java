package au.id.deejay.webserver.headers;

import au.id.deejay.webserver.spi.Header;

import java.util.List;

/**
 * @author David Jessup
 */
public class UnmodifiableHttpHeader implements Header {

	private Header origin;

	public UnmodifiableHttpHeader(Header origin) {
		this.origin = origin;
	}

	@Override
	public String name() {
		return origin.name();
	}

	@Override
	public String value() {
		return origin.value();
	}

	@Override
	public List<String> values() {
		return origin.values();
	}

	@Override
	public Header add(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Header add(String... newValues) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Header set(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Header set(String... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Header remove(String value) {
		throw new UnsupportedOperationException();
	}
}
