package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.HttpVersion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

/**
 * @author David Jessup
 */
public class ErrorResponse extends HttpResponse {

	public static final ErrorResponse BAD_REQUEST_400 = new ErrorResponse(HttpStatus.BAD_REQUEST_400, HttpVersion.HTTP_1_0);
	public static final ErrorResponse FORBIDDEN_403 = new ErrorResponse(HttpStatus.FORBIDDEN_403, HttpVersion.HTTP_1_0);
	public static final ErrorResponse NOT_FOUND_404 = new ErrorResponse(HttpStatus.NOT_FOUND_404, HttpVersion.HTTP_1_0);
	public static final ErrorResponse INTERNAL_SERVER_ERROR_500 = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR_500, HttpVersion.HTTP_1_0);
	public static final ErrorResponse NOT_IMPLEMENTED_501 = new ErrorResponse(HttpStatus.NOT_IMPLEMENTED_501, HttpVersion.HTTP_1_0);
	public static final ErrorResponse HTTP_VERSION_NOT_SUPPORTED_505 = new ErrorResponse(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505, HttpVersion.HTTP_1_0);

	public ErrorResponse(HttpStatus status, HttpVersion version) {
		super(status, version);
	}

	@Override
	public InputStream stream() {
		String body = MessageFormat.format("<title>{0} {1}</title><h1>{0} {1}</h1>", status().code(), status().description());
		return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
	}

}
