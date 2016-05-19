package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.RestClient
import net.sf.json.JSONSerializer
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull
import org.junit.Test

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.when

/**
 * Created by pldupont on 2016-05-19.
 */
class BoardTest extends AbstractResourceTest {

    @Test
    void "Given an RestClient, when calling getAll(), then receive a list of Board."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.LIST_OF_BOARDS))

        List<Board> boards = Board.getAll(mockRestClient);

        assertThat boards, new IsNot<>(new IsNull())
        assertThat boards.size(), new IsEqual<Integer>(2)
        "Assert equals to Board 84"(boards.get(0))
    }

    @Test
    void "Given an agileClient, when calling getBoard(84), then receive one Board."() {
        RestClient mockRestClient = "given a REST Client"()
        when(mockRestClient.get(AgileResource.RESOURCE_URI + "board/84"))
                .thenReturn(JSONSerializer.toJSON(JSONResources.BOARD_84))

        Board board = Board.get(mockRestClient, 84);

        "Assert equals to Board 84"(board)
    }
}
