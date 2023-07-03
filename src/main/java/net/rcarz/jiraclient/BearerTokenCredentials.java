package net.rcarz.jiraclient;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;

/**
 * Bearer Token authentication credentials.
 */
public class BearerTokenCredentials  implements ICredentials {

    private String bearerToken;

    /**
     * Creates new Bearer Token credentials.
     *
     * @param bearerToken
     */
    public BearerTokenCredentials(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Sets the Authorization header for the given request.
     *
     * @param req HTTP request to authenticate
     */
    public void authenticate(HttpRequest req) {
        req.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
    }

    /**
     * Gets the logon name representing these credentials.
     *
     * @return the String "bearerToken" since in bearer token authentication, user has no value
     */
    public String getLogonName() {
        return "bearerToken";
    }

    public void initialize(RestClient client) throws JiraException {
        // not applicable
    }

    public void logout(RestClient client) throws JiraException {
        // not applicable
    }
}

