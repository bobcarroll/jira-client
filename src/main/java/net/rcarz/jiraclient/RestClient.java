package net.rcarz.jiraclient;

import net.sf.json.JSON;
import org.apache.http.client.utils.URIBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Abstract base class for rest clients running HTTP requests over the wire.
 * Created by beders on 3/28/15.
 */
abstract public class RestClient {
    protected URI uri = null;

    public RestClient(URI uri) {
        this.uri = uri;
    }

    /**
     * Build a URI from a path.
     *
     * @param path Path to append to the base URI
     *
     * @return the full URI
     *
     * @throws URISyntaxException when the path is invalid
     */
    public URI buildURI(String path) throws URISyntaxException {
        return buildURI(path, null);
    }

    /**
     * Build a URI from a path and query parmeters.
     *
     * @param path Path to append to the base URI
     * @param params Map of key value pairs
     *
     * @return the full URI
     *
     * @throws URISyntaxException when the path is invalid
     */
    public URI buildURI(String path, Map<String, String> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder(uri);
        ub.setPath(ub.getPath() + path);

        if (params != null) {
            for (Map.Entry<String, String> ent : params.entrySet())
                ub.addParameter(ent.getKey(), ent.getValue());
        }

        return ub.build();
    }

    abstract public JSON delete(URI uri) throws RestException, IOException;

    abstract public JSON delete(String path) throws RestException, IOException, URISyntaxException;

    abstract public JSON get(URI uri) throws RestException, IOException;

    abstract public JSON get(String path, Map<String, String> params) throws RestException, IOException, URISyntaxException;

    abstract public JSON get(String path) throws RestException, IOException, URISyntaxException;

    abstract public JSON post(URI uri, JSON payload) throws RestException, IOException;

    abstract public JSON post(URI uri, String payload) throws RestException, IOException;

    abstract public JSON post(String path, JSON payload)
        throws RestException, IOException, URISyntaxException;

    abstract public JSON post(String path)
            throws RestException, IOException, URISyntaxException;

    abstract public JSON post(String path, File file) throws RestException, IOException, URISyntaxException;

    abstract public JSON put(URI uri, JSON payload) throws RestException, IOException;

    abstract public JSON put(String path, JSON payload)
        throws RestException, IOException, URISyntaxException;

    public abstract byte[] download(String uri) throws JiraException;
}
