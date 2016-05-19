package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraClient
import net.rcarz.jiraclient.RestClient
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Created by pldupont on 2016-05-19.
 */
class AbstractResourceTest {
    AgileClient agileClient;
    RestClient mockRestClient;

    void "given an Agile Client"() {
        mockRestClient = mock RestClient.class
        def mockJiraClient = mock JiraClient.class
        when(mockJiraClient.getRestClient()).thenReturn(mockRestClient)
        agileClient = new AgileClient(mockJiraClient)
    }

    RestClient "given a REST Client"() {
        mockRestClient = mock RestClient.class
        return mockRestClient
    }

    void "Assert equals to Board 84"(Board board) {
        assertThat board, new IsNot<>(new IsNull())
        assertThat board.getId(), new IsEqual<Integer>(JSONResources.BOARD_84_ID)
        assertThat board.getName(), new IsEqual<Integer>(JSONResources.BOARD_84_NAME)
        assertThat board.getType(), new IsEqual<Integer>(JSONResources.BOARD_84_TYPE)
        assertThat board.getSelfURL(), new IsEqual<Integer>(JSONResources.BOARD_84_SELF)

    }
}
