package au.id.deejay.webserver.response;

import au.id.deejay.webserver.api.HttpStatus;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author David Jessup
 */
@RunWith(MockitoJUnitRunner.class)
public class ResponseFactoryTest {

	private ResponseFactory responseFactory;

	@Mock
	private RequestHandler handler1;

	@Mock
	private RequestHandler handler2;

	@Mock
	private RequestHandler handler3;

	@Mock
	private Request request;

	@Test
	public void testResponseHandlersAreAskedToHandleInOrder() throws Exception {
		withResponseFactory();

		when(handler1.canHandle(any())).thenReturn(false);
		when(handler2.canHandle(any())).thenReturn(true);
		when(handler3.canHandle(any())).thenReturn(true);

		Response response = responseFactory.response(request);

		verify(handler1).canHandle(request);
		verify(handler2).canHandle(request);
		verify(handler3, never()).canHandle(request);

		verify(handler1, never()).handle(request);
		verify(handler2).handle(request);
		verify(handler3, never()).handle(request);
	}

	@Test
	public void test501ResponseReturnedIfNoHandlersCanHandleRequest() throws Exception {
		withResponseFactory();
		when(handler1.canHandle(any())).thenReturn(false);
		when(handler2.canHandle(any())).thenReturn(false);
		when(handler3.canHandle(any())).thenReturn(false);

		Response response = responseFactory.response(request);

		assertThat(response.status(), is(HttpStatus.NOT_IMPLEMENTED_501));
	}

	private void withResponseFactory() {
		responseFactory = new ResponseFactory(Arrays.asList(handler1, handler2));
	}
}