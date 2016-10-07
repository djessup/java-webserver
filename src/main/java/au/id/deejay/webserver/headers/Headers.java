package au.id.deejay.webserver.headers;

import java.util.List;
import java.util.Set;

/**
 * @author David Jessup
 */
public interface Headers {
	int size();
	boolean contains(String key);
	String value(String key);
	List<String> values(String key);
	Headers add(Header header);
	Headers add(String key, String value);
	Headers add(String key, String... values);
	Headers set(Header header);
	Headers set(String key, String value);
	Headers set(String key, String... values);
	Headers remove(String key);
	Header header(String key);
	List<Header> headers();
	Set<String> names();
}
