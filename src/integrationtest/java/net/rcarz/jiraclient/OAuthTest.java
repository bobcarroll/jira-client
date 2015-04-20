package net.rcarz.jiraclient;

import net.rcarz.jiraclient.oauth.OAuthRestClient;

import java.net.URI;

/**
 * Example on how to use OAuth with JIRA and the Java Scribe project
 * Follow instructions here: https://developer.atlassian.com/jiradev/api-reference/jira-rest-apis/jira-rest-api-tutorials/jira-rest-api-example-oauth-authentication
 *
 * See https://github.com/fernandezpablo85/scribe-java for an example on how to get a tokenSecret and accessToken.
 * i.e
 * <pre>
 *  JiraApi jiraApi = new JiraApi(url, privateKey)
 *  OAuthService service = new ServiceBuilder().provider(jiraApi).apiKey(consumerKey).apiSecret(privateKey).callback(yourCallbackURL).build();
 *  Token requestToken = service.getRequestToken();
 *  ...
 *  on callback from Jira with params.oauth_verifier:
 * Verifier v = new Verifier(params.oauth_verifier.toString())
 *String accessToken = service.getAccessToken(requestToken, v);
 * </pre>
 *
 * Created by beders on 3/27/15.
 */
public class OAuthTest {
    static String accessToken = "..."; // access token received for a user after the OAuth challenge is complete
    static String tokenSecret = "..."; // acquired by service.getRequestToken().getSecret()
    static String privateKey =  "..."; // generated using keytool -genkeypair
    static String endpointURL = "https://blabla.atlassian.net";
    static String consumerName = "hardcoded-consumer"; // part of the OAuth configuration for JIRA

    public static void main(String... args)  {
        JiraClient client = new JiraClient(endpointURL, null, new OAuthRestClient(URI.create(endpointURL), privateKey, consumerName, tokenSecret, accessToken));
        try {
            for (Project p : client.getProjects()) {
                System.out.println(p);
            }
        } catch (JiraException e) {
            e.printStackTrace();
        }
    }
}
