package au.id.deejay.webserver.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class HttpStatusTest {

	@Test
	public void testStatusCodes() throws Exception {
		assertThat(HttpStatus.CONTINUE_100.code(), is(equalTo(100)));
		assertThat(HttpStatus.CONTINUE_101.code(), is(equalTo(101)));
		assertThat(HttpStatus.OK_200.code(), is(equalTo(200)));
		assertThat(HttpStatus.CREATED_201.code(), is(equalTo(201)));
		assertThat(HttpStatus.ACCEPTED_202.code(), is(equalTo(202)));
		assertThat(HttpStatus.NON_AUTHORITATIVE_INFORMATION_203.code(), is(equalTo(203)));
		assertThat(HttpStatus.NO_CONTENT_204.code(), is(equalTo(204)));
		assertThat(HttpStatus.RESET_CONTENT_205.code(), is(equalTo(205)));
		assertThat(HttpStatus.PARTIAL_CONTENT_206.code(), is(equalTo(206)));
		assertThat(HttpStatus.MULTIPLE_CHOICES_300.code(), is(equalTo(300)));
		assertThat(HttpStatus.MOVED_PERMANENTLY_301.code(), is(equalTo(301)));
		assertThat(HttpStatus.FOUND_302.code(), is(equalTo(302)));
		assertThat(HttpStatus.SEE_OTHER_303.code(), is(equalTo(303)));
		assertThat(HttpStatus.NOT_MODIFIED_304.code(), is(equalTo(304)));
		assertThat(HttpStatus.USE_PROXY_305.code(), is(equalTo(305)));
		assertThat(HttpStatus.TEMPORARY_REDIRECT_307.code(), is(equalTo(307)));
		assertThat(HttpStatus.BAD_REQUEST_400.code(), is(equalTo(400)));
		assertThat(HttpStatus.UNAUTHORIZED_401.code(), is(equalTo(401)));
		assertThat(HttpStatus.PAYMENT_REQUIRED_402.code(), is(equalTo(402)));
		assertThat(HttpStatus.FORBIDDEN_403.code(), is(equalTo(403)));
		assertThat(HttpStatus.NOT_FOUND_404.code(), is(equalTo(404)));
		assertThat(HttpStatus.METHOD_NOT_ALLOWED_405.code(), is(equalTo(405)));
		assertThat(HttpStatus.NOT_ACCEPTABLE_406.code(), is(equalTo(406)));
		assertThat(HttpStatus.PROXY_AUTHENTICATION_REQUIRED_407.code(), is(equalTo(407)));
		assertThat(HttpStatus.REQUEST_TIMEOUT_408.code(), is(equalTo(408)));
		assertThat(HttpStatus.CONFLICT_409.code(), is(equalTo(409)));
		assertThat(HttpStatus.GONE_410.code(), is(equalTo(410)));
		assertThat(HttpStatus.LENGTH_REQUIRED_411.code(), is(equalTo(411)));
		assertThat(HttpStatus.PRECONDITION_FAILED_412.code(), is(equalTo(412)));
		assertThat(HttpStatus.REQUEST_ENTITY_TOO_LARGE_413.code(), is(equalTo(413)));
		assertThat(HttpStatus.REQUEST_URI_TOO_LONG_414.code(), is(equalTo(414)));
		assertThat(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415.code(), is(equalTo(415)));
		assertThat(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE_416.code(), is(equalTo(416)));
		assertThat(HttpStatus.EXPECTATION_FAILED_417.code(), is(equalTo(417)));
		assertThat(HttpStatus.INTERNAL_SERVER_ERROR_500.code(), is(equalTo(500)));
		assertThat(HttpStatus.NOT_IMPLEMENTED_501.code(), is(equalTo(501)));
		assertThat(HttpStatus.BAD_GATEWAY_502.code(), is(equalTo(502)));
		assertThat(HttpStatus.SERVICE_UNAVAILABLE_503.code(), is(equalTo(503)));
		assertThat(HttpStatus.GATEWAY_TIMEOUT_504.code(), is(equalTo(504)));
		assertThat(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505.code(), is(equalTo(505)));
	}

	@Test
	public void testStatusesHaveDescriptions() throws Exception {
		assertThat(HttpStatus.CONTINUE_100.description(), is(notNullValue()));
		assertThat(HttpStatus.CONTINUE_101.description(), is(notNullValue()));
		assertThat(HttpStatus.OK_200.description(), is(notNullValue()));
		assertThat(HttpStatus.CREATED_201.description(), is(notNullValue()));
		assertThat(HttpStatus.ACCEPTED_202.description(), is(notNullValue()));
		assertThat(HttpStatus.NON_AUTHORITATIVE_INFORMATION_203.description(), is(notNullValue()));
		assertThat(HttpStatus.NO_CONTENT_204.description(), is(notNullValue()));
		assertThat(HttpStatus.RESET_CONTENT_205.description(), is(notNullValue()));
		assertThat(HttpStatus.PARTIAL_CONTENT_206.description(), is(notNullValue()));
		assertThat(HttpStatus.MULTIPLE_CHOICES_300.description(), is(notNullValue()));
		assertThat(HttpStatus.MOVED_PERMANENTLY_301.description(), is(notNullValue()));
		assertThat(HttpStatus.FOUND_302.description(), is(notNullValue()));
		assertThat(HttpStatus.SEE_OTHER_303.description(), is(notNullValue()));
		assertThat(HttpStatus.NOT_MODIFIED_304.description(), is(notNullValue()));
		assertThat(HttpStatus.USE_PROXY_305.description(), is(notNullValue()));
		assertThat(HttpStatus.TEMPORARY_REDIRECT_307.description(), is(notNullValue()));
		assertThat(HttpStatus.BAD_REQUEST_400.description(), is(notNullValue()));
		assertThat(HttpStatus.UNAUTHORIZED_401.description(), is(notNullValue()));
		assertThat(HttpStatus.PAYMENT_REQUIRED_402.description(), is(notNullValue()));
		assertThat(HttpStatus.FORBIDDEN_403.description(), is(notNullValue()));
		assertThat(HttpStatus.NOT_FOUND_404.description(), is(notNullValue()));
		assertThat(HttpStatus.METHOD_NOT_ALLOWED_405.description(), is(notNullValue()));
		assertThat(HttpStatus.NOT_ACCEPTABLE_406.description(), is(notNullValue()));
		assertThat(HttpStatus.PROXY_AUTHENTICATION_REQUIRED_407.description(), is(notNullValue()));
		assertThat(HttpStatus.REQUEST_TIMEOUT_408.description(), is(notNullValue()));
		assertThat(HttpStatus.CONFLICT_409.description(), is(notNullValue()));
		assertThat(HttpStatus.GONE_410.description(), is(notNullValue()));
		assertThat(HttpStatus.LENGTH_REQUIRED_411.description(), is(notNullValue()));
		assertThat(HttpStatus.PRECONDITION_FAILED_412.description(), is(notNullValue()));
		assertThat(HttpStatus.REQUEST_ENTITY_TOO_LARGE_413.description(), is(notNullValue()));
		assertThat(HttpStatus.REQUEST_URI_TOO_LONG_414.description(), is(notNullValue()));
		assertThat(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415.description(), is(notNullValue()));
		assertThat(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE_416.description(), is(notNullValue()));
		assertThat(HttpStatus.EXPECTATION_FAILED_417.description(), is(notNullValue()));
		assertThat(HttpStatus.INTERNAL_SERVER_ERROR_500.description(), is(notNullValue()));
		assertThat(HttpStatus.NOT_IMPLEMENTED_501.description(), is(notNullValue()));
		assertThat(HttpStatus.BAD_GATEWAY_502.description(), is(notNullValue()));
		assertThat(HttpStatus.SERVICE_UNAVAILABLE_503.description(), is(notNullValue()));
		assertThat(HttpStatus.GATEWAY_TIMEOUT_504.description(), is(notNullValue()));
		assertThat(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505.description(), is(notNullValue()));
	}

	@Test
	public void testFromStatusCode() throws Exception {
		assertThat(HttpStatus.fromStatusCode(100), is(HttpStatus.CONTINUE_100));
		assertThat(HttpStatus.fromStatusCode(101), is(HttpStatus.CONTINUE_101));
		assertThat(HttpStatus.fromStatusCode(200), is(HttpStatus.OK_200));
		assertThat(HttpStatus.fromStatusCode(201), is(HttpStatus.CREATED_201));
		assertThat(HttpStatus.fromStatusCode(202), is(HttpStatus.ACCEPTED_202));
		assertThat(HttpStatus.fromStatusCode(203), is(HttpStatus.NON_AUTHORITATIVE_INFORMATION_203));
		assertThat(HttpStatus.fromStatusCode(204), is(HttpStatus.NO_CONTENT_204));
		assertThat(HttpStatus.fromStatusCode(205), is(HttpStatus.RESET_CONTENT_205));
		assertThat(HttpStatus.fromStatusCode(206), is(HttpStatus.PARTIAL_CONTENT_206));
		assertThat(HttpStatus.fromStatusCode(300), is(HttpStatus.MULTIPLE_CHOICES_300));
		assertThat(HttpStatus.fromStatusCode(301), is(HttpStatus.MOVED_PERMANENTLY_301));
		assertThat(HttpStatus.fromStatusCode(302), is(HttpStatus.FOUND_302));
		assertThat(HttpStatus.fromStatusCode(303), is(HttpStatus.SEE_OTHER_303));
		assertThat(HttpStatus.fromStatusCode(304), is(HttpStatus.NOT_MODIFIED_304));
		assertThat(HttpStatus.fromStatusCode(305), is(HttpStatus.USE_PROXY_305));
		assertThat(HttpStatus.fromStatusCode(307), is(HttpStatus.TEMPORARY_REDIRECT_307));
		assertThat(HttpStatus.fromStatusCode(400), is(HttpStatus.BAD_REQUEST_400));
		assertThat(HttpStatus.fromStatusCode(401), is(HttpStatus.UNAUTHORIZED_401));
		assertThat(HttpStatus.fromStatusCode(402), is(HttpStatus.PAYMENT_REQUIRED_402));
		assertThat(HttpStatus.fromStatusCode(403), is(HttpStatus.FORBIDDEN_403));
		assertThat(HttpStatus.fromStatusCode(404), is(HttpStatus.NOT_FOUND_404));
		assertThat(HttpStatus.fromStatusCode(405), is(HttpStatus.METHOD_NOT_ALLOWED_405));
		assertThat(HttpStatus.fromStatusCode(406), is(HttpStatus.NOT_ACCEPTABLE_406));
		assertThat(HttpStatus.fromStatusCode(407), is(HttpStatus.PROXY_AUTHENTICATION_REQUIRED_407));
		assertThat(HttpStatus.fromStatusCode(408), is(HttpStatus.REQUEST_TIMEOUT_408));
		assertThat(HttpStatus.fromStatusCode(409), is(HttpStatus.CONFLICT_409));
		assertThat(HttpStatus.fromStatusCode(410), is(HttpStatus.GONE_410));
		assertThat(HttpStatus.fromStatusCode(411), is(HttpStatus.LENGTH_REQUIRED_411));
		assertThat(HttpStatus.fromStatusCode(412), is(HttpStatus.PRECONDITION_FAILED_412));
		assertThat(HttpStatus.fromStatusCode(413), is(HttpStatus.REQUEST_ENTITY_TOO_LARGE_413));
		assertThat(HttpStatus.fromStatusCode(414), is(HttpStatus.REQUEST_URI_TOO_LONG_414));
		assertThat(HttpStatus.fromStatusCode(415), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415));
		assertThat(HttpStatus.fromStatusCode(416), is(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE_416));
		assertThat(HttpStatus.fromStatusCode(417), is(HttpStatus.EXPECTATION_FAILED_417));
		assertThat(HttpStatus.fromStatusCode(500), is(HttpStatus.INTERNAL_SERVER_ERROR_500));
		assertThat(HttpStatus.fromStatusCode(501), is(HttpStatus.NOT_IMPLEMENTED_501));
		assertThat(HttpStatus.fromStatusCode(502), is(HttpStatus.BAD_GATEWAY_502));
		assertThat(HttpStatus.fromStatusCode(503), is(HttpStatus.SERVICE_UNAVAILABLE_503));
		assertThat(HttpStatus.fromStatusCode(504), is(HttpStatus.GATEWAY_TIMEOUT_504));
		assertThat(HttpStatus.fromStatusCode(505), is(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromStatusCodeWithInvalidCodeThrowsException() throws Exception {
		HttpStatus.fromStatusCode(-1);
	}
}