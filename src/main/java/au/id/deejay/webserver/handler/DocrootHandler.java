package au.id.deejay.webserver.handler;

import au.id.deejay.webserver.api.HttpMethod;
import au.id.deejay.webserver.api.Request;
import au.id.deejay.webserver.api.RequestHandler;
import au.id.deejay.webserver.api.Response;
import au.id.deejay.webserver.response.DirectoryListingResponse;
import au.id.deejay.webserver.response.ErrorResponse;
import au.id.deejay.webserver.response.FileResponse;
import au.id.deejay.webserver.response.RedirectResponse;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * A request handler which handles GET requests by serving files from within a folder.
 * <p>
 * e.g. Using a DocrootHandler with docroot = /path/to/docroot and a request like GET /some/file.html the
 * DocrootHandler would attempt to respond with /path/to/docroot/some/file.html.
 *
 * @author David Jessup
 */
public class DocrootHandler implements RequestHandler {

	private final File docroot;
	private final List<String> indexFiles;
	private final Boolean allowDirectoryListings;

	/**
	 * Creates a new handler serving files from the path provided. If a list of index file names is provided then when a
	 * request is made to a directory the handler will look for a file matching one of the names provided and serve that
	 * file if found. If no index file is found and <code>allowDirectoryListings</code> is enabled a browse-able list of
	 * files will be served, allowing the user to browse through directories.
	 *
	 * @param path                   the path to the document root directory
	 * @param indexFiles             the list of valid index file names
	 * @param allowDirectoryListings if directory listings should be enabled
	 * @throws IllegalStateException    if the document root cannot be read by the server process.
	 * @throws IllegalArgumentException if the path is not to a directory
	 */
	public DocrootHandler(String path, List<String> indexFiles, boolean allowDirectoryListings) {
		this(new File(path), indexFiles, allowDirectoryListings);

	}

	/**
	 * Creates a new handler serving files from the directory provided. If a list of index file names is provided then
	 * when a request is made to a directory the handler will look for a file matching one of the names provided and
	 * serve that file if found. If no index file is found and <code>allowDirectoryListings</code> is enabled a
	 * browse-able list of files will be served, allowing the user to browse through directories.
	 *
	 * @param docroot                the document root directory
	 * @param indexFiles             the list of valid index file names
	 * @param allowDirectoryListings if directory listings should be enabled
	 * @throws IllegalStateException    if the document root cannot be read by the server process.
	 * @throws IllegalArgumentException if the docroot provided is not to a directory
	 */
	public DocrootHandler(File docroot, List<String> indexFiles, boolean allowDirectoryListings) {
		this.docroot = docroot;
		this.indexFiles = indexFiles;
		this.allowDirectoryListings = allowDirectoryListings;

		if (!this.docroot.canRead()) {
			throw new IllegalStateException("Unable to read docroot: " + docroot.getPath());
		}

		if (!this.docroot.isDirectory()) {
			throw new IllegalArgumentException(docroot.getPath() + " is not a directory");
		}
	}

	/**
	 * Checks if the handler can service a request.
	 *
	 * @param request the request to be handled
	 * @return Returns true if the request is a GET request.
	 */
	@Override
	public boolean canHandle(Request request) {
		return request.method() == HttpMethod.GET;
	}

	/**
	 * Handles a GET request. The handler will first check if the request URI maps to a file or folder within the
	 * docroot, and then response as follows:
	 * <p>
	 * If the requested file does not exist a "404 Not Found" response will be sent.
	 * <p>
	 * If the requested file does exist it will be streamed back to the client via a {@link FileResponse}.
	 * <p>
	 * If the requested file is actually a directory the handler will check the request has a trailing slash, and if
	 * not will send a {@link RedirectResponse} to redirect the client to the correct directory URL.
	 * <p>
	 * If index files are enabled the handler will look for a matching file inside the directory, and if one is found it
	 * will be served via a {@link FileResponse}.
	 * <p>
	 * Finally, if directory listings are enabled the handler will then send a {@link DirectoryListingResponse}.
	 *
	 * @param request the request to provide a response for
	 * @return Returns a response message for the request.
	 */
	@Override
	public Response handle(Request request) {
		File requestedFile = child(request.uri().getPath());

		if (!requestedFile.exists()) {
			return ErrorResponse.NOT_FOUND_404;
		}

		if (requestedFile.isDirectory()) {

			return handleDirectoryRequest(request, requestedFile);

		} else if (requestedFile.isFile()) {
			// Serve the file
			return new FileResponse(requestedFile, request.version());
		} else {
			return ErrorResponse.INTERNAL_SERVER_ERROR_500;
		}
	}

	/**
	 * Gets a child file inside the document root. Note this will return a valid {@link File}, even if the file does not
	 * exist (it can be tested with {@link File#exists()}).
	 *
	 * @param name the name/path of the child file.
	 * @return Returns a {@link File} object for the requested child file.
	 */
	private File child(String name) {
		return new File(docroot, name);
	}

	/**
	 * Checks if URI ends with a slash ("/") or not.
	 *
	 * @param uri the URI to check
	 * @return Returns true if the URI does NOT end with a slash, or false if it does.
	 */
	private boolean uriDoesNotEndWithSlash(URI uri) {
		return !uri.toString().endsWith("/");
	}

	/**
	 * Checks if the index file options is enabled, and at least one index filename has been provided.
	 *
	 * @return Returns true if index files are enabled.
	 */
	private boolean indexFilesAreEnabled() {
		return indexFiles != null && !indexFiles.isEmpty();
	}

	/**
	 * Looks for a matching index file in a directory.
	 *
	 * @param directory the directory to search in.
	 * @return Returns the first index file found, or null if no index file is found.
	 */
	private File findIndexFile(File directory) {

		for (String indexFileName : indexFiles) {
			File candidate = new File(directory, indexFileName);
			if (candidate.exists()) {
				return candidate;
			}
		}

		return null;
	}

	private Response handleDirectoryRequest(Request request, File requestedFile) {
		// Redirect requests for "/directory" to "/directory/"
		if (uriDoesNotEndWithSlash(request.uri())) {
			return new RedirectResponse(request.uri().toString() + "/", request.version());
		}

		if (indexFilesAreEnabled()) {
			File indexFile = findIndexFile(requestedFile);
			if (indexFile != null) {
				return new FileResponse(indexFile, request.version());
			}
		}

		if (allowDirectoryListings) {
			return new DirectoryListingResponse(requestedFile, request.version());
		} else {
			return ErrorResponse.FORBIDDEN_403;
		}
	}
}
