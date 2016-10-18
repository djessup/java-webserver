package au.id.deejay.webserver.exception;

/**
 * A {@link ResponseException} is a runtime exception that indicates an abnormal state occurred during the generation or
 * delivery of a {@link au.id.deejay.webserver.api.Response} message.
 *
 * @author David Jessup
 */
public class ResponseException extends RuntimeException {

	/**
	 * Constructs a new runtime exception with {@code null} as its  detail message.  The cause is not initialized, and
	 * may subsequently be  initialized by a call to {@link #initCause}.
	 */
	public ResponseException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may
	 * subsequently be initialized by a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
	 *                method.
	 */
	public ResponseException(String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and cause. <p>Note that the detail message
	 * associated with {@code cause} is <i>not</i> automatically incorporated in this runtime exception's detail
	 * message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
	 *                <tt>null</tt>
	 *                value is permitted, and indicates that the cause is nonexistent or  unknown.)
	 */
	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of <tt>(cause==null ? null :
	 * cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>).  This
	 * constructor is useful for runtime exceptions  that are little more than wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A <tt>null</tt>
	 *              value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ResponseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message, cause, suppression enabled or disabled, and
	 * writable stack trace enabled or disabled.
	 *
	 * @param message            the detail message.
	 * @param cause              the cause. (A {@code null} value is permitted, and indicates that the cause is
	 *                           nonexistent or unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	protected ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
