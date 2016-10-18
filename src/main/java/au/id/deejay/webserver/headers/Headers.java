package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * A collection of HTTP headers.
 *
 * @author David Jessup
 */
public interface Headers {

	/**
	 * Gets the number of headers in the collection.
	 *
	 * @return Returns the number of headers in the collection.
	 */
	int size();

	/**
	 * Checks if the header collection contains a header of the specified name. The name is case-insensitive.
	 *
	 * @param key the name of the header to check for
	 * @return Returns true of the collection contains a header with the specified name.
	 */
	boolean contains(String key);

	/**
	 * Returns the value of the header with the specified name. If the header has multiple values then the first one is
	 * returned. If there is no header of that name then null is returned. The name is case-insensitive.
	 *
	 * @param key the name of the header to get the value of
	 * @return Returns the first value of the header specified, or null if it is not in the collection.
	 */
	@CheckForNull
	String value(String key);

	/**
	 * Returns a list of all values of the header of the specified name. If the header has no values then an empty list
	 * is returned. If the header does not exist then null is returned. The name is case-insensitive.
	 *
	 * @param key the name of the header to get the values of
	 * @return Returns a list of of values for the header specified, or null if it is not in the collection.
	 */
	@CheckForNull
	List<String> values(String key);

	/**
	 * Adds a {@link Header} to the collection. If the same header already exists in the collection then the values are
	 * merged. This can result if a header with duplicate values if any of the new values already exist.
	 *
	 * @param header the {@link Header} to add
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers add(Header header);

	/**
	 * Adds a {@link Header} with the name and single value provided to the collection. If the same header already
	 * exists in the collection then the values are merged. This can result if a header with duplicate values if any of
	 * the new values already exist.
	 *
	 * @param key   the name of the header being added
	 * @param value the value of the header being added
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers add(String key, String value);

	/**
	 * Adds a {@link Header} with the name and values provided to the collection. If the same header already exists in
	 * the collection then the values are merged. This can result if a header with duplicate values if any of the new
	 * values already exist.
	 *
	 * @param key    the name of the header being added
	 * @param values a list of values for the header being added
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers add(String key, String... values);

	/**
	 * Adds a {@link Header} to the collection, replacing any existing header of the same name.
	 *
	 * @param header the header being added
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers set(Header header);

	/**
	 * Adds a {@link Header} with the name and single value provided to the collection, replacing any existing header of
	 * the same name.
	 *
	 * @param key   the name of the header being added
	 * @param value the value of the header being added
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers set(String key, String value);

	/**
	 * Adds a {@link Header} with the name and values provided to the collection, replacing any existing header of
	 * the same name.
	 *
	 * @param key    the name of the header being added
	 * @param values a list of values for the header being added
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers set(String key, String... values);

	/**
	 * Removes a named header from the collection. If the header is not in the collection then nothing is done.
	 *
	 * @param key the name of the header to remove
	 * @return Returns the current instance for method chaining.
	 */
	@Nonnull
	Headers remove(String key);

	/**
	 * Gets a {@link Header} from the collection by name. The name is case-insensitive.
	 *
	 * @param key the name of the header to get
	 * @return Returns the header with the specified name, or null if it does not exist in the collection.
	 */
	@CheckForNull
	Header header(String key);

	/**
	 * Gets a list of all the {@link Header}s in the collection. The headers and the list itself are all immutable.
	 *
	 * @return Returns an immutable list of all {@link Header}s in the collection.
	 */
	@Nonnull
	List<Header> headers();

	/**
	 * Gets the names of all the headers in the collection.
	 *
	 * @return Returns a set containing the names of all the headers in the collection.
	 */
	@Nonnull
	Set<String> names();

	/**
	 * Gets an RFC2616-formatted version of all the headers in the collection. The order of the headers are output is
	 * the same order they were added in.
	 * <p>
	 * Example:
	 * <pre>
	 *     new HttpHeaders(
	 *         new HttpHeader("Content-type", "text/html"),
	 *         new HttpHeader("Content-length", "1234")
	 *     ).toString();
	 * </pre>
	 * <p>
	 * would return:
	 * <pre>
	 *     Content-type: text/html
	 *     Content-length: 1234
	 * </pre>
	 *
	 * @return Returns the headers formatted as per RFC2616.
	 */
	@Nonnull
	@Override
	String toString();
}
