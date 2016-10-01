package au.id.deejay.webserver.impl;

import au.id.deejay.webserver.spi.Request;
import au.id.deejay.webserver.spi.Response;

import java.io.IOException;
import java.net.Socket;

/**
 * @author David Jessup
 */
public class WebWorker implements Runnable {

	private Socket client;

	public WebWorker(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {

		Request request = readRequest(client);

	}

	private Request readRequest(Socket client) {
		try {
			client.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void writeResponse(Response response) {

	}
}
