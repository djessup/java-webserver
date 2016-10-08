package au.id.deejay.webserver.headers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * A single HTTP header.
 *
 * @author David Jessup
 */
public interface Header {

	/**
	 * Gets the name of the header.
	 *
	 * @return Returns the header name.
	 */
	@Nonnull
	String name();

	/**
	 * Gets the value of the header, or the first value if the header has multiple values.
	 *
	 * @return Return the value of the header or null if it has no value set.
	 */
	@CheckForNull
	String value();

	/**
	 * Gets a list of all the header's values. If the header has no values an empty list will be returned.
	 *
	 * @return Returns an unmodifiable list of the header's values.
	 */
	@Nonnull
	List<String> values();

	/**
	 * Appends a single value to the header.
	 *
	 * @param value the value to add.
	 * @return Returns the header instance for method chaining.
	 */
	@Nonnull
	Header add(String value);

	/**
	 * Appends multiple values to the header.
	 *
	 * @param values the values to add.
	 * @return Returns the header instance for method chaining.
	 */
	@Nonnull
	Header add(String... values);

	/**
	 * Replaces any existing header values with the value provided.
	 *
	 * @param value the value to set.
	 * @return Returns the header instance for method chaining.
	 */
	@Nonnull
	Header set(String value);

	/**
	 * Replaces any existing header values with the values provided.
	 *
	 * @param values the values to set.
	 * @return Returns the header instance for method chaining.
	 */
	@Nonnull
	Header set(String... values);

	/**
	 * Removes a named value from the header.
	 *
	 * @param value the value to remove
	 * @return Returns the header instance for method chaining.
	 */
	@Nonnull
	Header remove(String value);

	/**
	 * Returns the header in string form, formatted as per RFC2616 - i.e. "Header-Name: value,other-value";
	 *
	 * @return Returns the header in string form.
	 */
	@Nonnull
	@Override
	String toString();
}
