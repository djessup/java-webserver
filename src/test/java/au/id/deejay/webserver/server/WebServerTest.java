package au.id.deejay.webserver.server;

import au.id.deejay.webserver.api.RequestHandler;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author David Jessup
 */
@RunWith(MockitoJUnitRunner.class)
public class WebServerTest {

	@Mock
	private WebServerExecutor executor;

	@InjectMocks
	private WebServer server = new WebServer(0, 10, 10, Collections.singletonList(mock(RequestHandler.class)));

	@After
	public void tearDown() throws Exception {
		if (server != null && server.running()) {
			server.stop();
		}
	}

	@Test
	public void testConstructor() throws Exception {
		server = new WebServer(0, 10, 10, Collections.singletonList(mock(RequestHandler.class)));
		assertThat(server.running(), is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPortLessThanZeroThrowsException() throws Exception {
		new WebServer(-1, 10, 10, Collections.singletonList(mock(RequestHandler.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPortGreaterThan65535ThrowsException() throws Exception {
		new WebServer(65536, 10, 10, Collections.singletonList(mock(RequestHandler.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMaxThreadsLessThanOneThrowsException() throws Exception {
		new WebServer(0, 10, 0, Collections.singletonList(mock(RequestHandler.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullRequestHandlersThrowsException() throws Exception {
		new WebServer(0, 10, 10, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyRequestHandlersThrowsException() throws Exception {
		new WebServer(0, 10, 10, Collections.emptyList());
	}

	@Test
	public void testStart() throws Exception {
		server.start();
		await().until(() -> server.running());
		verify(executor).run();
		assertThat(server.running(), is(true));
	}

	@Test(expected = IllegalStateException.class)
	public void testStartThrowsExceptionIfAlreadyRunning() throws Exception {
		server.start();
		assertThat(server.running(), is(true));
		server.start();
	}

	@Test
	public void testStop() throws Exception {
		server.start();
		server.stop();
		verify(executor).stop();
		assertThat(server.running(), is(false));
	}


	@Test(expected = IllegalStateException.class)
	public void testStopThrowsExceptionIfNotRunning() throws Exception {
		assertThat(server.running(), is(false));
		server.stop();
	}

	@Test
	public void testRunning() throws Exception {
		assertThat(server.running(), is(false));
		server.start();
		assertThat(server.running(), is(true));
		server.stop();
		assertThat(server.running(), is(false));
	}
}