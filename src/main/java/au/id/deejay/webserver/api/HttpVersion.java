package au.id.deejay.webserver.api;

/**
 * Represents an HTTP version string (e.g. "HTTP/1.1"). Can be constructed explicitly or from an HTTP version string.
 *
 * @author David Jessup
 */
public final class HttpVersion implements Comparable<HttpVersion> {

	/**
	 * Convenience instance for HTTP/1.0
	 */
	public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);

	/**
	 * Convenience instance for HTTP/1.1
	 */
	public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);

	private int majorVersion;
	private int minorVersion;

	/**
	 * Constructs an HttpVersion instance using explicit major and minor versions.
	 *
	 * @param majorVersion the major version number
	 * @param minorVersion the minor version number
	 */
	public HttpVersion(int majorVersion, int minorVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
	}

	/**
	 * Constructs an HttpVersion instance from a raw HTTP version string (e.g. "HTTP/1.1").
	 *
	 * @param rawHttpVersion the raw HTTP version string.
	 */
	public HttpVersion(String rawHttpVersion) {
		parseRawVersionString(rawHttpVersion);
	}

	/**
	 * Parses a raw HTTP version string into it's major/minor versions.
	 */
	private void parseRawVersionString(String rawHttpVersion) {
		// Split the version into "HTTP" and "<major>.<minor>" version strings
		int slashPos = rawHttpVersion.indexOf('/');
		if (slashPos == -1) {
			throwInvalidVersionException(rawHttpVersion);
		}

		String httpLiteral = rawHttpVersion.substring(0, slashPos);
		String httpVersion = rawHttpVersion.substring(slashPos + 1, rawHttpVersion.length());

		// Check the first part was "HTTP"
		if (!"HTTP".equals(httpLiteral)) {
			throwInvalidVersionException(rawHttpVersion);
		}

		// Split the version into individual major and minor versions
		int dotPos = httpVersion.indexOf('.');
		if (dotPos == -1) {
			throwInvalidVersionException(rawHttpVersion);
		}

		// Try to parse the major/minor versions
		try {
			majorVersion = Integer.parseInt(httpVersion.substring(0, dotPos));
			minorVersion = Integer.parseInt(httpVersion.substring(dotPos + 1, httpVersion.length()));
		} catch (NumberFormatException nfe) {
			throwInvalidVersionException(rawHttpVersion);
		}
	}

	/**
	 * Throws an {@link IllegalArgumentException} indicating a supplied version string was invalid or malformed.
	 * This is purely to avoid repeating the same message string ~5 times.
	 */
	private static void throwInvalidVersionException(String versionString) {
		throw new IllegalArgumentException("Invalid HTTP version: " + versionString);
	}

	@Override
	public int compareTo(HttpVersion other) {
		if (this.major() == other.major()) {
			if (this.minor() == other.minor()) {
				return 0;
			} else {
				return this.minor() > other.minor() ? 1 : -1;
			}
		} else {
			return this.major() > other.major() ? 1 : -1;
		}
	}

	/**
	 * Gets the major version.
	 *
	 * @return Returns the major version number.
	 */
	public int major() {
		return majorVersion;
	}

	/**
	 * Gets the minor version.
	 *
	 * @return Returns the minor version number.
	 */
	public int minor() {
		return minorVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return (17 + majorVersion)
				* 31 + minorVersion;
	}

	/**
	 * Returns true if the other object is an HttpVersion with the same major and minor version numbers.
	 *
	 * @param obj the other object to compare this instance with
	 * @return true if both objects are HttpVersions with the same major and minor version numbers.
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof HttpVersion
				&& this.major() == ((HttpVersion) obj).major()
				&& this.minor() == ((HttpVersion) obj).minor();
	}

	/**
	 * Formats the HTTP version string in a format suitable for use in a request line (e.g. "HTTP/1.1").
	 *
	 * @return Returns a formatted HTTP version string.
	 */
	@Override
	public String toString() {
		return "HTTP/" + majorVersion + "." + minorVersion;
	}
}
