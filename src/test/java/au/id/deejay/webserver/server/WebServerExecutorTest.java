package au.id.deejay.webserver.server;

import au.id.deejay.webserver.response.ResponseFactory;
import org.junit.After;
import org.junit.Test;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author David Jessup
 */
public class WebServerExecutorTest {

	private WebServerExecutor executor;

	@After
	public void tearDown() throws Exception {
		if (executor != null && executor.running()) {
			executor.stop();
		}
	}

	@Test
	public void testConstructor() throws Exception {
		executor = new WebServerExecutor(0, 10, 10, mock(ResponseFactory.class));
		assertThat(executor.running(), is(false));
	}

	@Test
	public void run() throws Exception {
		executor = new WebServerExecutor(0, 10, 10, mock(ResponseFactory.class));

		Thread executorThread = new Thread(executor);
		executorThread.start();

		await().until(executorThread::isAlive);

		assertThat(executor.running(), is(true));
	}

	@Test
	public void running() throws Exception {

	}

	@Test
	public void stop() throws Exception {

	}

}