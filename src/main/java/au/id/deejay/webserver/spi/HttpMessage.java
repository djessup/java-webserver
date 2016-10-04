package au.id.deejay.webserver.spi;


import au.id.deejay.webserver.request.HttpVersion;

import java.io.InputStream;

/**
 * @author David Jessup
 */
public interface HttpMessage {
	InputStream body();
	Headers headers();
	HttpVersion version();
}