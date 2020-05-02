package au.id.deejay.webserver.server;

import au.id.deejay.webserver.response.ResponseFactory;
import org.junit.After;
import org.junit.Test;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
public class WebServerExecutorTest {

	private WebServerExecutor executor;
    private Thread executorThread;

    @After
	public void tearDown() {
		if (executor != null && executor.running()) {
			executor.stop();
		}
	}

	@Test
	public void testServerDoesNotStartBeforeInstructed() {
		withExecutor();

		assertThat(executor.running(), is(false));
	}

    @Test
	public void testServerCanRun() {
		withExecutor();
        withRunningServer();

		assertThat(executor.running(), is(true));
	}

	@Test
	public void testServerCanStop() {
		withExecutor();
        withRunningServer();

        executor.stop();

		assertThat(executor.running(), is(false));
	}

    private void withExecutor() {
        executor = new WebServerExecutor(0, 1, 10, mock(ResponseFactory.class));
    }

    private void withRunningServer() {
        executorThread = new Thread(executor);
        executorThread.start();

        await().until(executorThread::isAlive);
    }
}
