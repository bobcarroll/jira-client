package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraException
import net.sf.json.JSONSerializer
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.junit.Assert.assertThat

/**
 * Test for edge cases on deserialization.
 * @author pldupont
 */
class AgileResourceTest extends AbstractResourceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void "given a valid single resource JSON and a valid type, when calling getResource(), then should return an object"() {
        def aRESTClient = "given a REST Client"()
        def aValidResource = JSONSerializer.toJSON(JSONResources.BOARD)

        def resource = AgileResource.getResource(Board.class, aValidResource, aRESTClient)

        assertThat resource, new IsNot<>(new IsNull())
    }

    @Test
    void "given an invalid single resource JSON and a valid type, when calling getResource(), then should throw an exception"() {
        def aRESTClient = "given a REST Client"()
        def anInvalidResource = JSONResources.BOARD
        expectedException.expectMessage("JSON payload is malformed")
        expectedException.expect(JiraException.class)

        AgileResource.getResource(Board.class, anInvalidResource, aRESTClient)
    }

    @Test
    void "given a valid single resource JSON and an invalid type, when calling getResource(), then should throw an exception"() {
        def aRESTClient = "given a REST Client"()
        def aValidResource = JSONSerializer.toJSON(JSONResources.BOARD)
        expectedException.expectMessage("Failed to deserialize object.")
        expectedException.expect(JiraException.class)

        AgileResource.getResource(String.class, aValidResource, aRESTClient)
    }

    @Test
    void "given a valid resource array JSON and a valid type, when calling getResource(), then should return an object"() {
        def aRESTClient = "given a REST Client"()
        def aValidResource = JSONSerializer.toJSON(JSONResources.LIST_OF_BOARDS)

        def resource = AgileResource.getResourceArray(Board.class, aValidResource, aRESTClient, "values")

        assertThat resource, new IsNot<>(new IsNull())
    }

    @Test
    void "given a valid resource array JSON and a valid type but list name invalid, when calling getResource(), then should return an object"() {
        def aRESTClient = "given a REST Client"()
        def aValidResource = JSONSerializer.toJSON(JSONResources.LIST_OF_BOARDS)
        expectedException.expectMessage("No array found for name 'v'")
        expectedException.expect(JiraException.class)

        AgileResource.getResourceArray(Board.class, aValidResource, aRESTClient, "v")
    }

    @Test
    void "given an invalid resource array JSON and a valid type, when calling getResource(), then should throw an exception"() {
        def aRESTClient = "given a REST Client"()
        def anInvalidResource = JSONResources.LIST_OF_BOARDS
        expectedException.expectMessage("JSON payload is malformed")
        expectedException.expect(JiraException.class)

        AgileResource.getResourceArray(Board.class, anInvalidResource, aRESTClient, "values")
    }

    @Test
    void "given a valid resource array JSON and an invalid type, when calling getResource(), then should throw an exception"() {
        def aRESTClient = "given a REST Client"()
        def aValidResource = JSONSerializer.toJSON(JSONResources.LIST_OF_BOARDS)
        expectedException.expectMessage("Failed to deserialize object.")
        expectedException.expect(JiraException.class)

        AgileResource.getResourceArray(String.class, aValidResource, aRESTClient, "values")
    }
}
