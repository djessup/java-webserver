package au.id.deejay.webserver.exception;

import org.junit.Test;

/**
 * @author David Jessup
 */
public class ResponseExceptionTest {

	@SuppressWarnings("ThrowableInstanceNeverThrown")
	@Test
	public void testConstructors() throws Exception {
		new ResponseException();
		new ResponseException("");
		new ResponseException("", new RuntimeException());
		new ResponseException(new RuntimeException());
		new ResponseException("", new RuntimeException(), false, false);
		// If nothing went wrong, test passed!
	}

}