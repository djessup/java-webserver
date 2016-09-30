package au.id.deejay.webserver.spi;

import java.net.Socket;

/**
 * @author David Jessup
 */
public interface Worker {
	void service(Socket client);
}
