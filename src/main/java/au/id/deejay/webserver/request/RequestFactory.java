package au.id.deejay.webserver.request;

import au.id.deejay.webserver.exception.RequestParsingException;
import au.id.deejay.webserver.spi.Request;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author David Jessup
 */
public class RequestFactory {

	public Request request(InputStream inputStream) {
		try {
			return new HttpRequest(inputStream);
		} catch (IOException e) {
			throw new RequestParsingException("Failed to read request from input stream.", e);
		}
	}
}
