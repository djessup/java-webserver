package au.id.deejay.webserver.headers;


import au.id.deejay.webserver.spi.Headers;

import java.util.*;

/**
 * @author David Jessup
 */
public class HttpHeaders implements Headers {

	private Map<String, List<String>> headers;

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
		return (contains(key) && !headers.get(key).isEmpty()) ? headers.get(key).get(0) : null;
	}

	@Override
	public List<String> values(String key) {
		return headers.get(key);
	}

	@Override
	public Headers add(String key, String value) {
		return add(key, new String[]{value});
	}

	@Override
	public Headers add(String key, String... newValues) {
		List<String> values;
		if (contains(key)) {
			values = headers.get(key);
			Collections.addAll(values, newValues);
			headers.put(key, values);
		} else {
			return set(key, newValues);
		}

		return this;
	}

	@Override
	public Headers set(String key, String value) {
		return set(key, new String[]{value});
	}

	@Override
	public Headers set(String key, String... values) {
		headers.put(key, Arrays.asList(values));
		return this;
	}

	@Override
	public Headers remove(String key) {
		headers.remove(key);
		return this;
	}
}
