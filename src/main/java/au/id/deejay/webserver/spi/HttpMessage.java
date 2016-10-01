package au.id.deejay.webserver.spi;


/**
 * @author David Jessup
 */
public interface HttpMessage {
	String body();
	Headers headers();
}