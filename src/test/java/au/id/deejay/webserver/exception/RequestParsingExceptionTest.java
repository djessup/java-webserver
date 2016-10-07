package au.id.deejay.webserver.exception;

import org.junit.Test;

/**
 * @author David Jessup
 */
public class RequestParsingExceptionTest {

	@SuppressWarnings("ThrowableInstanceNeverThrown")
	@Test
	public void testConstructors() throws Exception {
		new RequestParsingException();
		new RequestParsingException("");
		new RequestParsingException("", new RuntimeException());
		new RequestParsingException(new RuntimeException());
		new RequestParsingException("", new RuntimeException(), false, false);
		// If nothing went wrong, test passed!
	}

}