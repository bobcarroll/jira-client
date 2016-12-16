package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraException
import net.rcarz.jiraclient.RestClient
import net.rcarz.jiraclient.RestException
import net.sf.json.JSONObject
import net.sf.json.JSONSerializer
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.when

/**
 *  Created on 2016-05-20.
 * @author pldupont
 */
class EpicTest extends AbstractResourceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void "Given a valid Epic ID, when calling Epic.get(id), then receive one Epic."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "epic/" + JSONResources.EPIC_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.EPIC))

        Epic epic = Epic.get(mockRestClient, JSONResources.EPIC_ID);

        "Assert equals to Epic"(epic)
    }

    @Test
    void "Given an invalid epic ID, when calling getEpic(666), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized", [])
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "epic/666"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Epic : /rest/agile/1.0/epic/666");

        Epic.get(mockRestClient, 666);
    }

    @Test
    void "Given an epic without the issue cache, when calling asIssue(false), then call the REST Api."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/" + JSONResources.EPIC_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.ISSUE))
        Epic mockEpic = new Epic(mockRestClient, JSONSerializer.toJSON(JSONResources.EPIC) as JSONObject)

        assertThat mockEpic.issue, new IsNull()
        Issue issue = mockEpic.asIssue(false)

        "Assert equals to Issue"(issue)
        assertThat mockEpic.issue, new IsNot<>(new IsNull())
    }

    @Test
    void "Given an epic with the issue cache, when calling asIssue(false), then use the cache version."() {
        RestClient mockRestClient = "given a REST Client"()
        Epic mockEpic = new Epic(mockRestClient, JSONSerializer.toJSON(JSONResources.EPIC) as JSONObject)
        Issue mockIssue = new Issue(mockRestClient, JSONSerializer.toJSON(JSONResources.ISSUE) as JSONObject)
        mockEpic.issue = mockIssue

        assertThat mockEpic.issue, new IsNot<>(new IsNull())
        Issue issue = mockEpic.asIssue(false)

        "Assert equals to Issue"(issue)
        assertThat mockEpic.issue, new IsNot<>(new IsNull())
        assert mockEpic.issue == mockIssue
    }

    @Test
    void "Given an epic with the issue cache, when calling asIssue(true), then call the REST Api."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "issue/" + JSONResources.EPIC_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.ISSUE))
        Epic mockEpic = new Epic(mockRestClient, JSONSerializer.toJSON(JSONResources.EPIC) as JSONObject)
        Issue mockIssue = new Issue(mockRestClient, JSONSerializer.toJSON(JSONResources.ISSUE) as JSONObject)
        mockEpic.issue = mockIssue

        assertThat mockEpic.issue, new IsNot<>(new IsNull())
        Issue issue = mockEpic.asIssue(true)

        "Assert equals to Issue"(issue)
        assertThat mockEpic.issue, new IsNot<>(new IsNull())
        assert mockEpic.issue != mockIssue
    }

    @Test
    void "Given a valid Epic, when calling getIssues(), then receive a list of Issues."() {
        RestClient mockRestClient = "given a REST Client"()
        Epic mockEpic = new Epic(mockRestClient, JSONSerializer.toJSON(JSONResources.EPIC) as JSONObject)
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "epic/${JSONResources.EPIC_ID}/issue"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_ISSUES))

        List<Issue> issues = mockEpic.getIssues();

        assertThat issues, new IsNot<>(new IsNull())
        assertThat issues.size(), new IsEqual<Integer>(4)
        "Assert equals to Issue"(issues.get(0))
    }
}
