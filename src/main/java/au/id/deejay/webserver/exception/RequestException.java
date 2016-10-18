package au.id.deejay.webserver.exception;

/**
 * A {@link RequestException} is a runtime exception that indicates an abnormal state occurred during the generation or
 * delivery of a {@link au.id.deejay.webserver.api.Request} message.
 *
 * @author David Jessup
 */
public class RequestException extends RuntimeException {

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public RequestException() {
		super();
	}
	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public RequestException(String message) {
		super(message);
	}
	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public RequestException(Throwable cause) {
		super(cause);
	}
	/**
	 * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
	 */
	protected RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
