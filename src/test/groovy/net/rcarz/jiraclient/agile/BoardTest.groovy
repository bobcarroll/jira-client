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
 * Created on 2016-05-19.
 * @author pldupont
 */
class BoardTest extends AbstractResourceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void "Given a RestClient, when calling getAll(), then receive a list of Board."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_BOARDS))

        List<Board> boards = Board.getAll(mockRestClient);

        assertThat boards, new IsNot<>(new IsNull())
        assertThat boards.size(), new IsEqual<Integer>(2)
        "Assert equals to Board"(boards.get(0))
    }

    @Test
    void "Given a RestClient, when calling getAll() and use doesn't have access, then throws an 401 error."() {
        RestException unauthorized = new RestException("Do not have access", 401, "Unauthorized", [])
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve a list of Board : /rest/agile/1.0/board");

        Board.getAll(mockRestClient);
    }

    @Test
    void "Given a RestClient, when calling getBoard(boardId), then receive one Board."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/${JSONResources.BOARD_ID}"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.BOARD))

        Board board = Board.get(mockRestClient, JSONResources.BOARD_ID);

        "Assert equals to Board"(board)
    }

    @Test
    void "Given a RestClient, when calling getBoard(666), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized", [])
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/666"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Board : /rest/agile/1.0/board/666");

        Board.get(mockRestClient, 666);
    }

    @Test
    void "Given a valid Board, when calling getSprints(), then receive a list of Sprints."() {
        RestClient mockRestClient = "given a REST Client"()
        Board mockBoard = new Board(mockRestClient, JSONSerializer.toJSON(JSONResources.BOARD) as JSONObject)
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/${JSONResources.BOARD_ID}/sprint"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_SPRINTS))

        List<Sprint> sprints = mockBoard.getSprints();

        assertThat sprints, new IsNot<>(new IsNull())
        assertThat sprints.size(), new IsEqual<Integer>(2)
        "Assert equals to Sprint"(sprints.get(0))
    }

    @Test
    void "Given a valid Board, when calling getEpics(), then receive a list of Epics."() {
        RestClient mockRestClient = "given a REST Client"()
        Board mockBoard = new Board(mockRestClient, JSONSerializer.toJSON(JSONResources.BOARD) as JSONObject)
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/${JSONResources.BOARD_ID}/epic"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_EPICS))

        List<Epic> epics = mockBoard.getEpics();

        assertThat epics, new IsNot<>(new IsNull())
        assertThat epics.size(), new IsEqual<Integer>(2)
        "Assert equals to Epic"(epics.get(0))
    }

    @Test
    void "Given a valid Board, when calling getBacklog(), then receive a list of Issues."() {
        RestClient mockRestClient = "given a REST Client"()
        Board mockBoard = new Board(mockRestClient, JSONSerializer.toJSON(JSONResources.BOARD) as JSONObject)
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/${JSONResources.BOARD_ID}/backlog"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_ISSUES))

        List<Issue> backlog = mockBoard.getBacklog();

        assertThat backlog, new IsNot<>(new IsNull())
        assertThat backlog.size(), new IsEqual<Integer>(4)
        "Assert equals to Issue"(backlog.get(0))
    }

    @Test
    void "Given a valid Board, when calling getIssuesWithoutEpic(), then receive a list of Issues."() {
        RestClient mockRestClient = "given a REST Client"()
        Board mockBoard = new Board(mockRestClient, JSONSerializer.toJSON(JSONResources.BOARD) as JSONObject)
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/${JSONResources.BOARD_ID}/epic/none/issue"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_ISSUES))

        List<Issue> issues = mockBoard.getIssuesWithoutEpic();

        assertThat issues, new IsNot<>(new IsNull())
        assertThat issues.size(), new IsEqual<Integer>(4)
        "Assert equals to Issue"(issues.get(0))
    }
}
