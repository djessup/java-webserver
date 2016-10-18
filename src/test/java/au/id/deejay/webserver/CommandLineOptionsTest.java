package au.id.deejay.webserver;

import joptsimple.OptionException;
import org.junit.Test;

import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
public class CommandLineOptionsTest {

	private CommandLineOptions options;

	@Test
	public void testPort() throws Exception {
		// long-form
		options = new CommandLineOptions("--port", "123");
		assertThat(options.port(), is(equalTo(123)));

		// long-form with "equals" syntax
		options = new CommandLineOptions("--port=456");
		assertThat(options.port(), is(equalTo(456)));

		// short-form
		options = new CommandLineOptions("-p", "123");
		assertThat(options.port(), is(equalTo(123)));

		// short-form with "equals" syntax
		options = new CommandLineOptions("-p=456");
		assertThat(options.port(), is(equalTo(456)));

		// default
		options = new CommandLineOptions();
		assertThat(options.port(), is(equalTo(0)));
	}

	@Test(expected = OptionException.class)
	public void testNonNumericPortThrowsException() throws Exception {
		new CommandLineOptions("--port", "notanumber").port();
	}

	@Test(expected = OptionException.class)
	public void testPortWithoutValueThrowsException() throws Exception {
		options = new CommandLineOptions("--port");
	}

	@Test
	public void testDocroot() throws Exception {
		// long-form
		options = new CommandLineOptions("--docroot", "/path/to/docroot");
		assertThat(options.docroot(), is(equalTo("/path/to/docroot")));

		// long-form with "equals" syntax
		options = new CommandLineOptions("--docroot=/other/path/to/docroot");
		assertThat(options.docroot(), is(equalTo("/other/path/to/docroot")));

		// short-form
		options = new CommandLineOptions("-d", "/path/to/docroot");
		assertThat(options.docroot(), is(equalTo("/path/to/docroot")));

		// short-form with "equals" syntax
		options = new CommandLineOptions("-d=/other/path/to/docroot");
		assertThat(options.docroot(), is(equalTo("/other/path/to/docroot")));

		// default
		options = new CommandLineOptions();
		assertThat(options.docroot(), is(equalTo("./docroot")));
	}

	@Test(expected = OptionException.class)
	public void testDocrootWithoutValueThrowsException() throws Exception {
		options = new CommandLineOptions("--docroot");
	}

	@Test
	public void testTimeout() throws Exception {
		// long-form
		options = new CommandLineOptions("--timeout", "20");
		assertThat(options.timeout(), is(equalTo(20)));

		// long-form with "equals" syntax
		options = new CommandLineOptions("--timeout=30");
		assertThat(options.timeout(), is(equalTo(30)));

		// short-form
		options = new CommandLineOptions("-t", "20");
		assertThat(options.timeout(), is(equalTo(20)));

		// short-form with "equals" syntax
		options = new CommandLineOptions("-t=30");
		assertThat(options.timeout(), is(equalTo(30)));

		// default
		options = new CommandLineOptions();
		assertThat(options.timeout(), is(equalTo(10)));
	}

	@Test(expected = OptionException.class)
	public void testTimeoutNumericPortThrowsException() throws Exception {
		new CommandLineOptions("--timeout", "notanumber").timeout();
	}

	@Test(expected = OptionException.class)
	public void testTimeoutWithoutValueThrowsException() throws Exception {
		options = new CommandLineOptions("--timeout");
	}

	@Test
	public void testMaxThreads() throws Exception {

		// long-form
		options = new CommandLineOptions("--maxthreads", "123");
		assertThat(options.maxThreads(), is(equalTo(123)));

		// long-form with "equals" syntax
		options = new CommandLineOptions("--maxthreads=456");
		assertThat(options.maxThreads(), is(equalTo(456)));

		// short-form
		options = new CommandLineOptions("-m", "123");
		assertThat(options.maxThreads(), is(equalTo(123)));

		// short-form with "equals" syntax
		options = new CommandLineOptions("-m=456");
		assertThat(options.maxThreads(), is(equalTo(456)));

		// default
		options = new CommandLineOptions();
		assertThat(options.maxThreads(), is(equalTo(20)));
	}


	@Test(expected = OptionException.class)
	public void testNonNumericMaxThreadsThrowsException() throws Exception {
		new CommandLineOptions("--maxthreads", "notanumber").maxThreads();
	}

	@Test(expected = OptionException.class)
	public void testMaxThreadsWithoutValueThrowsException() throws Exception {
		options = new CommandLineOptions("--maxthreads");
	}

	@Test
	public void testHelp() throws Exception {
		// long-form
		options = new CommandLineOptions("--help");
		assertThat(options.help(), is(true));

		// short-form
		options = new CommandLineOptions("-h");
		assertThat(options.help(), is(true));

		// default
		options = new CommandLineOptions();
		assertThat(options.help(), is(false));

		// combined with other options
		options = new CommandLineOptions("--port", "123", "--help");
		assertThat(options.help(), is(true));
	}

	@Test
	public void testMultipleOptions() throws Exception {
		options = new CommandLineOptions("--port", "123", "--timeout", "20", "--maxthreads", "456", "--docroot", "/path/to/docroot");

		assertThat(options.port(), is(equalTo(123)));
		assertThat(options.timeout(), is(equalTo(20)));
		assertThat(options.docroot(), is(equalTo("/path/to/docroot")));
		assertThat(options.maxThreads(), is(equalTo(456)));
	}

	@Test
	public void testPrintHelp() throws Exception {
		OutputStream outputStream = mock(OutputStream.class);

		options = new CommandLineOptions();
		options.printHelpOn(outputStream);

		verify(outputStream, atLeastOnce()).write(anyVararg(), anyInt(), anyInt());
	}

}