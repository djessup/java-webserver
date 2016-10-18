package au.id.deejay.webserver.api;


import au.id.deejay.webserver.headers.Headers;

import java.io.InputStream;

/**
 * An HTTP message object (i.e. a request or response).
 *
 * @author David Jessup
 */
public interface HttpMessage {

	/**
	 * Gets an {@link InputStream} for the message body. In a POST {@link Request} this would contain the message data,
	 * for a {@link Response} it would contain the response body.
	 * <p>
	 * Implementations should return a new instance for each invocation, since the consumer is expected to close the
	 * stream when they are finished with it.
	 *
	 * @return Returns an {@link InputStream} which will stream the content of the message body.
	 */
	InputStream stream();

	/**
	 * Gets the collection of HTTP headers associated with the message.
	 *
	 * @return Returns the {@link Headers} collection for this message.
	 */
	Headers headers();

	/**
	 * Gets the HTTP version of the message.
	 *
	 * @return Returns the {@link HttpVersion} of the message.
	 */
	HttpVersion version();
}