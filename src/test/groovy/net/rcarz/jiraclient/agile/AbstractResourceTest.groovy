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
        assertThat board.getId(), new IsEqual<Integer>(JSONResources.BOARD_ID)
        assertThat board.getName(), new IsEqual<Integer>(JSONResources.BOARD_NAME)
        assertThat board.getType(), new IsEqual<Integer>(JSONResources.BOARD_TYPE)
        assertThat board.getSelfURL(), new IsEqual<Integer>(JSONResources.BOARD_SELF)
        assertThat board.toString(), new IsEqual<String>(
                String.format("Board{id=%s, name='%s'}",
                        JSONResources.BOARD_ID, JSONResources.BOARD_NAME))

    }

    void "Assert equals to Sprint 37"(Sprint sprint) {
        assertThat sprint, new IsNot<>(new IsNull())
        assertThat sprint.getId(), new IsEqual<Integer>(JSONResources.SPRINT_ID)
        assertThat sprint.getName(), new IsEqual<Integer>(JSONResources.SPRINT_NAME)
        assertThat sprint.getSelfURL(), new IsEqual<Integer>(JSONResources.SPRINT_SELF)
        assertThat sprint.getState(), new IsEqual<Integer>(JSONResources.SPRINT_STATE)
        assertThat sprint.getOriginBoardId(), new IsEqual<Integer>(JSONResources.SPRINT_ORIGIN_BOARD_ID)
        assertThat sprint.getStartDate(), new IsEqual<Date>(JSONResources.SPRINT_START_DATE)
        assertThat sprint.getEndDate(), new IsEqual<Date>(JSONResources.SPRINT_END_DATE)
        assertThat sprint.getCompleteDate(), new IsEqual<Date>(JSONResources.SPRINT_COMPLETE_DATE)
        assertThat sprint.toString(), new IsEqual<String>(
                String.format("Sprint{id=%s, name='%s'}",
                        JSONResources.SPRINT_ID, JSONResources.SPRINT_NAME))
    }

    void "Assert equals to Issue 10001"(Issue issue) {
        assertThat issue, new IsNot<>(new IsNull())
        assertThat issue.getId(), new IsEqual<Integer>(JSONResources.ISSUE_ID)
        assertThat issue.getName(), new IsNull<String>()
        assertThat issue.getSelfURL(), new IsEqual<Integer>(JSONResources.ISSUE_SELF)

    }

    void "Assert equals to Issue HSP-1"(Issue issue) {
        "Assert equals to Issue 10001"(issue)

    }
}
