package au.id.deejay.webserver;

import org.junit.Test;

import static au.id.deejay.webserver.MessageConstants.CRLF;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author David Jessup
 */
public class MessageConstantsTest {

	@Test
	public void testCRLF() throws Exception {
		assertThat(CRLF, is(equalTo(new String(new char[]{(char) 13, (char) 10}))));
	}
}