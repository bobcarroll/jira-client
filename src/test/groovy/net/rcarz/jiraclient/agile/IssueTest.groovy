package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraException
import net.rcarz.jiraclient.RestClient
import net.rcarz.jiraclient.RestException
import net.sf.json.JSONSerializer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.mockito.Mockito.when

/**
 *  Created on 2016-05-20.
 * @author pldupont
 */
class IssueTest extends AbstractResourceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void "Given a valid issue ID, when calling Issue.get(id), then receive one Issue."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/" + JSONResources.ISSUE_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.ISSUE))

        Issue issue = Issue.get(mockRestClient, JSONResources.ISSUE_ID);

        "Assert equals to Issue"(issue)
    }

    @Test
    void "Given an invalid issue ID, when calling getIssue(666), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized", [])
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/666"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Issue : /rest/agile/1.0/issue/666");

        Issue.get(mockRestClient, 666);
    }

    @Test
    void "Given a valid issue Key, when calling Issue.get(key), then receive one Issue."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/" + JSONResources.ISSUE_KEY))
                .thenReturn(JSONSerializer.toJSON(JSONResources.ISSUE))

        Issue issue = Issue.get(mockRestClient, JSONResources.ISSUE_KEY);

        "Assert equals to Issue"(issue)
    }

    @Test
    void "Given an invalid issue Key, when calling getIssue('HSP-2'), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized", [])
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/HSP-2"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Issue : /rest/agile/1.0/issue/HSP-2");

        Issue.get(mockRestClient, "HSP-2");
    }

    @Test
    void "Given an issue empty, when calling Issue.get(id), then deserialize properly."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/" + JSONResources.BLANK_ISSUE1_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.BLANK_ISSUE1))

        Issue issue = Issue.get(mockRestClient, JSONResources.BLANK_ISSUE1_ID);

        "Assert equals to Issue Blank"(issue)
    }
}
