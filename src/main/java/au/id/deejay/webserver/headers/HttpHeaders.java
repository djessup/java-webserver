package au.id.deejay.webserver.headers;


import au.id.deejay.webserver.spi.Header;
import au.id.deejay.webserver.spi.Headers;

import java.util.*;

import static au.id.deejay.webserver.MessageConstants.CRLF;

/**
 * @author David Jessup
 */
public class HttpHeaders implements Headers {

	private Map<String, Header> headers;

	public HttpHeaders() {
		headers = new HashMap<>();
	}

	@Override
	public int size() {
		return headers.size();
	}

	@Override
	public boolean contains(String key) {
		return headers.containsKey(key);
	}

	@Override
	public String value(String key) {
		return contains(key) ? headers.get(key).value() : null;
	}

	@Override
	public List<String> values(String key) {
		return contains(key) ? headers.get(key).values() : null;
	}

	@Override
	public Headers add(Header header) {
		return add(header.name(), header.values().toArray(new String[]{}));
	}

	@Override
	public Headers add(String key, String value) {
		return add(key, new String[]{value});
	}

	@Override
	public Headers add(String key, String... values) {
		if (contains(key)) {
			headers.get(key).add(values);
		} else {
			return set(key, values);
		}

		return this;
	}

	@Override
	public Headers set(Header header) {
		headers.put(header.name(), header);
		return this;
	}

	@Override
	public Headers set(String key, String value) {
		return set(key, new String[]{value});
	}

	@Override
	public Headers set(String key, String... values) {
		headers.put(key, new HttpHeader(key, values));
		return this;
	}

	@Override
	public Headers remove(String key) {
		headers.remove(key);
		return this;
	}

	@Override
	public Header header(String key) {
		return headers.get(key);
	}

	@Override
	public List<Header> headers() {
		return Collections.unmodifiableList(new ArrayList<>(headers.values()));
	}

	@Override
	public Set<String> names() {
		return Collections.unmodifiableSet(headers.keySet());
	}

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
