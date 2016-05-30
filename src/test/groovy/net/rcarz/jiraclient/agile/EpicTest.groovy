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
class EpicTest extends AbstractResourceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void "Given a valid Epic ID, when calling Epic.get(id), then receive one Epic."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "epic/" + JSONResources.EPIC_ID))
                .thenReturn(JSONSerializer.toJSON(JSONResources.EPIC))

        Epic epic = Epic.get(mockRestClient, JSONResources.EPIC_ID);

        "Assert equals to Epic ${JSONResources.EPIC_ID}"(epic)
    }

    @Test
    void "Given an invalid epic ID, when calling getEpic(666), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized")
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "epic/666"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Epic : /rest/agile/1.0/epic/666");

        Epic.get(mockRestClient, 666);
    }
}
