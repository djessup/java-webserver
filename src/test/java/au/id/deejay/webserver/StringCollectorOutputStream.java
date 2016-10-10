package au.id.deejay.webserver;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream captures characters written to it so they can be retrieved later for testing purposes.
 *
 * @author David Jessup
 */
public class StringCollectorOutputStream extends OutputStream {

	private StringBuilder content = new StringBuilder();

	@Override
	public void write(int x) throws IOException {
		content.append((char) x);
	}

	public String toString() {
		return content.toString();
	}

	public void clear() {
		content = new StringBuilder();
	}
}
