package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class ErrorResponseTest {

	@Test
	public void testPreconfiguredResponseCodes() throws Exception {
		assertThat(ErrorResponse.BAD_REQUEST_400.status(),  is(HttpStatus.BAD_REQUEST_400));
		assertThat(ErrorResponse.NOT_FOUND_404.status(),  is(HttpStatus.NOT_FOUND_404));
		assertThat(ErrorResponse.INTERNAL_SERVER_ERROR_500.status(),  is(HttpStatus.INTERNAL_SERVER_ERROR_500));
		assertThat(ErrorResponse.NOT_IMPLEMENTED_501.status(),  is(HttpStatus.NOT_IMPLEMENTED_501));
		assertThat(ErrorResponse.HTTP_VERSION_NOT_SUPPORTED_505.status(),  is(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505));
	}

	@Test
	public void testErrorResponseBodyContainsErrorDetails() throws Exception {
		assertErrorResponseBodyContainsErrorDetails(ErrorResponse.BAD_REQUEST_400);
		assertErrorResponseBodyContainsErrorDetails(ErrorResponse.NOT_FOUND_404);
		assertErrorResponseBodyContainsErrorDetails(ErrorResponse.INTERNAL_SERVER_ERROR_500);
		assertErrorResponseBodyContainsErrorDetails(ErrorResponse.NOT_IMPLEMENTED_501);
		assertErrorResponseBodyContainsErrorDetails(ErrorResponse.HTTP_VERSION_NOT_SUPPORTED_505);
	}

	private void assertErrorResponseBodyContainsErrorDetails(ErrorResponse errorResponse) throws Exception {
		InputStream stream = errorResponse.stream();
		String body = IOUtils.toString(stream, StandardCharsets.UTF_8);
		stream.close();

		assertThat(body, containsString(String.valueOf(errorResponse.status().code())));
		assertThat(body, containsString(errorResponse.status().description()));
	}
}