package au.id.deejay.webserver.api;

/**
 * HTTP code codes as defined by RFC2616.
 *
 * @author David Jessup
 */
public enum HttpStatus {

	CONTINUE_100(100, "Continue"),
	CONTINUE_101(101, "Switching Protocols"),
	OK_200(200, "OK"),
	CREATED_201(201, "Created"),
	ACCEPTED_202(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION_203(203, "Non-Authoritative Information"),
	NO_CONTENT_204(204, "No Content"),
	RESET_CONTENT_205(205, "Reset Content"),
	PARTIAL_CONTENT_206(206, "Partial Content"),
	MULTIPLE_CHOICES_300(300, "Multiple Choices"),
	MOVED_PERMANENTLY_301(301, "Moved Permanently"),
	FOUND_302(302, "Found"),
	SEE_OTHER_303(303, "See Other"),
	NOT_MODIFIED_304(304, "Not Modified"),
	USE_PROXY_305(305, "Use Proxy"),
	TEMPORARY_REDIRECT_307(307, "Temporary Redirect"),
	BAD_REQUEST_400(400, "Bad Request"),
	UNAUTHORIZED_401(401, "Unauthorized"),
	PAYMENT_REQUIRED_402(402, "Payment Required"),
	FORBIDDEN_403(403, "Forbidden"),
	NOT_FOUND_404(404, "Not Found"),
	METHOD_NOT_ALLOWED_405(405, "Method Not Allowed"),
	NOT_ACCEPTABLE_406(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED_407(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT_408(408, "Request Timeout"),
	CONFLICT_409(409, "Conflict"),
	GONE_410(410, "Gone"),
	LENGTH_REQUIRED_411(411, "Length Required"),
	PRECONDITION_FAILED_412(412, "Precondition Failed"),
	REQUEST_ENTITY_TOO_LARGE_413(413, "Request Entity Too Large"),
	REQUEST_URI_TOO_LONG_414(414, "Request-URI Too Long"),
	UNSUPPORTED_MEDIA_TYPE_415(415, "Unsupported Media Type"),
	REQUESTED_RANGE_NOT_SATISFIABLE_416(416, "Requested Range Not Satisfiable"),
	EXPECTATION_FAILED_417(417, "Expectation Failed"),
	INTERNAL_SERVER_ERROR_500(500, "Internal Server Error"),
	NOT_IMPLEMENTED_501(501, "Not Implemented"),
	BAD_GATEWAY_502(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE_503(503, "Service Unavailable"),
	GATEWAY_TIMEOUT_504(504, "Gateway Timeout"),
	HTTP_VERSION_NOT_SUPPORTED_505(505, "HTTP Version Not Supported");

	private final int code;
	private final String description;

	/**
	 * Initialises an HTTP status.
	 *
	 * @param code        the numeric HTTP status code
	 * @param description the text description of the HTTP status
	 */
	HttpStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * Gets the {@link HttpStatus} for a numeric status code.
	 *
	 * @param code the numeric status code
	 * @return Returns the corresponding HTTP status.
	 * @throws IllegalArgumentException if the numeric code does not match a known HTTP status.
	 */
	public static HttpStatus fromStatusCode(int code) {
		for (HttpStatus status : HttpStatus.values()) {
			if (code == status.code()) {
				return status;
			}
		}

		throw new IllegalArgumentException("Unrecognised status code: " + code);
	}

	/**
	 * Gets the numeric status code of the HTTP status.
	 *
	 * @return Returns the numeric status code.
	 */
	public int code() {
		return code;
	}

	/**
	 * Gets the text description of the HTTP status.
	 *
	 * @return Returns the status description.
	 */
	public String description() {
		return description;
	}
}