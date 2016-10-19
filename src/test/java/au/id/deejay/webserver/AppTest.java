package au.id.deejay.webserver;

import joptsimple.OptionException;
import org.junit.Test;

/**
 * @author David Jessup
 */
public class AppTest {
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPortThrowsException() throws Exception {
		App.main(new String[]{"--port", "-1"});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadPortThrowsException() throws Exception {
		App.main(new String[]{"--port", "bad"});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidThreadsThrowsException() throws Exception {
		App.main(new String[]{"--maxthreads", "0"});
	}

	@Test(expected = OptionException.class)
	public void testUnknownOptionThrowsException() throws Exception {
		App.main(new String[]{"--unknown"});
	}

	@Test
	public void testUsageDisplay() throws Exception {
		App.main(new String[]{"--help"});
	}

	@Test
	public void testNoOptions() throws Exception {
		App.main(new String[]{});
	}
}