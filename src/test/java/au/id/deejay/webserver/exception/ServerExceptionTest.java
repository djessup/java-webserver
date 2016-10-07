package au.id.deejay.webserver.exception;

import org.junit.Test;

/**
 * @author David Jessup
 */
public class ServerExceptionTest {

	@SuppressWarnings("ThrowableInstanceNeverThrown")
	@Test
	public void testConstructors() throws Exception {
		new ServerException();
		new ServerException("");
		new ServerException("", new RuntimeException());
		new ServerException(new RuntimeException());
		new ServerException("", new RuntimeException(), false, false);
		// If nothing went wrong, test passed!
	}

}