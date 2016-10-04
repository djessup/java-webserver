package au.id.deejay.webserver.spi;

import java.util.List;

/**
 * A single HTTP header
 * @author David Jessup
 */
public interface Header {

	String name();
	String value();
	List<String> values();
	Header add(String value);
	Header add(String... newValues);
	Header set(String value);
	Header set(String... values);
	Header remove(String value);
}
