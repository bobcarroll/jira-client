package net.rcarz.jiraclient.oauth;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.rcarz.jiraclient.RestException;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import sun.misc.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * RestClient based on the scribe request/response classes
 * Created by beders on 3/30/15.
 */
public class OAuthRestClient extends RestClient {
    OAuthService service;
    private Token token;

    public OAuthRestClient(URI uri, String privateKey, String consumerKey, String tokenSecret, String accessKey) {
        super(uri);
        JiraApi jiraApi = new JiraApi(uri.toString(), privateKey);
        service = new ServiceBuilder().provider(jiraApi).apiKey(consumerKey).apiSecret(privateKey).build();
        token = new Token(accessKey, tokenSecret);
    }

    private JSON request(OAuthRequest req) throws RestException, IOException {
        req.addHeader("Accept", "application/json");

        service.signRequest(token, req);
        Response response = req.send();
        String result = response.getBody(); // note: this assumes body content is a string with UTF-8 encoding!

        int status = response.getCode();
        if (status >= 300)
            throw new RestException(response.toString(), status, result);

        return result.length() > 0 ? JSONSerializer.toJSON(result) : null;
    }

    private JSON request(OAuthRequest req, String payload)
            throws RestException, IOException {

        if (payload != null) {
            req.addHeader("Content-Type", "application/json");
            req.setCharset("UTF-8");
            req.addPayload(payload);
        }
        return request(req);
    }

    private JSON request(OAuthRequest req, File file)
            throws RestException, IOException {
        if (file != null) {
            req.addHeader("X-Atlassian-Token", "nocheck");

            MultipartEntity ent = new MultipartEntity();
            ent.addPart("file", new FileBody(file));
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length()); // yup, will fail with VERY large files
            ent.writeTo(bos);
            req.addHeader("Content-Type", "multipart/form-data");
            req.addPayload(bos.toByteArray());
        }
        return request(req);
    }

    private JSON request(OAuthRequest req, JSON payload)
            throws RestException, IOException {

        return request(req, payload != null ? payload.toString() : null);
    }


    /**
     * Executes an HTTP DELETE with the given URI.
     *
     * @param uri Full URI of the remote endpoint
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON delete(URI uri) throws RestException, IOException {
        return request(new OAuthRequest(Verb.DELETE, uri.toString()));
    }

    /**
     * Executes an HTTP DELETE with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws java.net.URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON delete(String path) throws RestException, IOException, URISyntaxException {
        return delete(buildURI(path));
    }

    /**
     * Executes an HTTP GET with the given URI.
     *
     * @param uri Full URI of the remote endpoint
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON get(URI uri) throws RestException, IOException {
        return request(new OAuthRequest(Verb.GET, uri.toString()));
    }

    /**
     * Executes an HTTP GET with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param params Map of key value pairs
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON get(String path, Map<String, String> params) throws RestException, IOException, URISyntaxException {
        return get(buildURI(path, params));
    }

    /**
     * Executes an HTTP GET with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON get(String path) throws RestException, IOException, URISyntaxException {
        return get(path, null);
    }


    /**
     * Executes an HTTP POST with the given URI and payload.
     *
     * @param uri Full URI of the remote endpoint
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON post(URI uri, JSON payload) throws RestException, IOException {
        return request(new OAuthRequest(Verb.POST, uri.toString()), payload.toString());
    }

    /**
     * Executes an HTTP POST with the given URI and payload.
     *
     * At least one JIRA REST endpoint expects malformed JSON. The payload
     * argument is quoted and sent to the server with the application/json
     * Content-Type header. You should not use this function when proper JSON
     * is expected.
     *
     * @see https://jira.atlassian.com/browse/JRA-29304
     *
     * @param uri Full URI of the remote endpoint
     * @param payload Raw string to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON post(URI uri, String payload) throws RestException, IOException {
        String quoted = null;
        if(payload != null && !payload.equals(new JSONObject())){
            quoted = String.format("\"%s\"", payload);
        }
        return request(new OAuthRequest(Verb.POST, uri.toString()), quoted);
    }

    /**
     * Executes an HTTP POST with the given path and payload.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON post(String path, JSON payload)
            throws RestException, IOException, URISyntaxException {

        return post(buildURI(path), payload);
    }

    /**
     * Executes an HTTP POST with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON post(String path)
            throws RestException, IOException, URISyntaxException {

        return post(buildURI(path), new JSONObject());
    }

    /**
     * Executes an HTTP POST with the given path and file payload.
     *
     * @param uri Full URI of the remote endpoint
     * @param file java.io.File
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws RestException
     */
    public JSON post(String path, File file) throws RestException, IOException, URISyntaxException{
        return request(new OAuthRequest(Verb.POST, path), file);
    }

    /**
     * Executes an HTTP PUT with the given URI and payload.
     *
     * @param uri Full URI of the remote endpoint
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON put(URI uri, JSON payload) throws RestException, IOException {
        return request(new OAuthRequest(Verb.PUT, uri.toString()), payload);
    }

    /**
     * Executes an HTTP PUT with the given path and payload.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON put(String path, JSON payload)
            throws RestException, IOException, URISyntaxException {

        return put(buildURI(path), payload);
    }

    @Override
    public byte[] download(String uri) throws JiraException {
        OAuthRequest request = new OAuthRequest(Verb.GET, uri);
        service.signRequest(token, request);
        Response response = request.send();
        try {
            byte[] bytes = IOUtils.readFully(response.getStream(), -1, true);
            return bytes;
        } catch (IOException e) {
            throw new JiraException(String.format("Failed downloading attachment from %s: %s", this.uri, e.getMessage()),e);
        }
    }

}
