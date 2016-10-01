package au.id.deejay.webserver.request;

/**
 * @author David Jessup
 */
public class HttpVersion implements Comparable<HttpVersion> {

	public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
	public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);

	private int majorVersion;
	private int minorVersion;

	public HttpVersion(String rawHttpVersion) {

		// Split the version into "HTTP" and "<major>.<minor>" version strings
		int slashPos = rawHttpVersion.indexOf('/');
		if (slashPos == -1) {
			throw invalidVersionException(rawHttpVersion);
		}

		String httpLiteral = rawHttpVersion.substring(0, slashPos);
		String httpVersion = rawHttpVersion.substring(slashPos + 1, rawHttpVersion.length());

		// Check the first part was "HTTP"
		if (!"HTTP".equals(httpLiteral)) {
			throw invalidVersionException(rawHttpVersion);
		}

		// Split the version into individual major and minor versions
		int dotPos = httpVersion.indexOf('.');
		if (dotPos == -1) {
			throw invalidVersionException(rawHttpVersion);
		}

		// Try to parse the major/minor versions
		try {
			majorVersion = Integer.parseInt(httpVersion.substring(0, dotPos));
			minorVersion = Integer.parseInt(httpVersion.substring(dotPos + 1, httpVersion.length()));
		} catch (NumberFormatException nfe) {
			throw invalidVersionException(rawHttpVersion);
		}
	}

	private static RuntimeException invalidVersionException(String versionString) {
		return new IllegalArgumentException("Invalid HTTP version: " + versionString);
	}

	public HttpVersion(int majorVersion, int minorVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
	}

	public int major() {
		return majorVersion;
	}

	public int minor() {
		return minorVersion;
	}

	@Override
	public String toString() {
		return "HTTP/" + majorVersion + "." + minorVersion;
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

	@Override
	public boolean equals(Object obj) {
		return obj instanceof HttpVersion
				&& this.major() == ((HttpVersion)obj).major()
				&& this.minor() == ((HttpVersion)obj).minor();
	}
}
