package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraException
import net.rcarz.jiraclient.RestClient
import net.rcarz.jiraclient.RestException
import net.sf.json.JSONSerializer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.internal.matchers.Contains

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.when

/**
 * Created by pldupont on 2016-05-19.
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
        "Assert equals to Board 84"(boards.get(0))
    }

    @Test
    void "Given a RestClient, when calling getAll() and use doesn't have access, then throws an 401 error."() {
        RestException unauthorized = new RestException("Do not have access", 401, "Unauthorized")
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve a list of Board : /rest/agile/1.0/board");

        List<Board> boards = Board.getAll(mockRestClient);
    }

    @Test
    void "Given a RestClient, when calling getBoard(84), then receive one Board."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/84"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.BOARD_84))

        Board board = Board.get(mockRestClient, 84);

        "Assert equals to Board 84"(board)
    }

    @Test
    void "Given a RestClient, when calling getBoard(666), then throws an 404 error."() {
        RestException unauthorized = new RestException("Do not have access", 404, "Unauthorized")
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/666"))
                .thenThrow(unauthorized)
        expectedException.expect(JiraException.class);
        expectedException.expectMessage("Failed to retrieve Board : /rest/agile/1.0/board/666");

        Board board = Board.get(mockRestClient, 666);
    }

    @Test
    void "Given a valid Board, when calling getSprints(), then receive a list of Sprints."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/84"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.BOARD_84))
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/84/sprint"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_SPRINTS))

        Board board = Board.get(mockRestClient, 84);
        "Assert equals to Board 84"(board)

        List<Sprint> sprints = board.getSprints();

        assertThat sprints, new IsNot<>(new IsNull())
        assertThat sprints.size(), new IsEqual<Integer>(2)
        "Assert equals to Sprint 37"(sprints.get(0))
    }
}
