package au.id.deejay.webserver.exception;

import org.junit.Test;

/**
 * @author David Jessup
 */
public class RequestExceptionTest {

	@SuppressWarnings("ThrowableInstanceNeverThrown")
	@Test
	public void testConstructors() throws Exception {
		new RequestException();
		new RequestException("");
		new RequestException("", new RuntimeException());
		new RequestException(new RuntimeException());
		new RequestException("", new RuntimeException(), false, false);
		// If nothing went wrong, test passed!
	}

}