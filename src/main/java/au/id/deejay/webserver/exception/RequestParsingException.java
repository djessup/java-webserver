package au.id.deejay.webserver.exception;

/**
 * @author David Jessup
 */
public class RequestParsingException extends RuntimeException {

	public RequestParsingException() {
		super();
	}

	public RequestParsingException(String message) {
		super(message);
	}

	public RequestParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestParsingException(Throwable cause) {
		super(cause);
	}

	protected RequestParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
