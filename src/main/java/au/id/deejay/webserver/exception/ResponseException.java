package au.id.deejay.webserver.exception;

/**
 * A {@link ResponseException} is a runtime exception that indicates an abnormal state occurred during the generation or
 * delivery of a {@link au.id.deejay.webserver.api.Response} message.
 *
 * @author David Jessup
 */
public class ResponseException extends RuntimeException {

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public ResponseException() {
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public ResponseException(String message) {
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public ResponseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
	 */
	public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
