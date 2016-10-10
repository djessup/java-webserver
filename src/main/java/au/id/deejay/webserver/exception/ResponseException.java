package au.id.deejay.webserver.exception;

/**
 * @author David Jessup
 */
public class ResponseException extends RuntimeException {

	public ResponseException() {
		super();
	}

	public ResponseException(String message) {
		super(message);
	}

	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseException(Throwable cause) {
		super(cause);
	}

	public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
