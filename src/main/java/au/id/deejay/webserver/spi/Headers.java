package au.id.deejay.webserver.spi;

import java.util.List;

/**
 * @author David Jessup
 */
public interface Headers {

	int size();
	boolean contains(String key);
	String value(String key);
	List<String> values(String key);
	Headers add(String key, String value);
	Headers add(String key, String... newValues);
	Headers set(String key, String value);
	Headers set(String key, String... values);
	Headers remove(String key);
}
