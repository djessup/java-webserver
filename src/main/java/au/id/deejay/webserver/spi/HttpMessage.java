package au.id.deejay.webserver.spi;

import com.sun.net.httpserver.Headers;

/**
 * @author David Jessup
 */
public interface HttpMessage {
	String body();
	Headers headers();
}