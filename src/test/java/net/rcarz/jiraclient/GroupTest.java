package net.rcarz.jiraclient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class GroupTest {

    private static final String groupName = "special-group";
    private static final String self = "http://anyhost.org/rest/api/2/group?groupname=" + groupName;

    protected static JSONObject getGroupJSON() {
        return new JSONObject()
                .accumulate("name", groupName)
                .accumulate("self", self)
                .accumulate("users", new JSONObject()
                        .accumulate("size", 1)
                        .accumulate("items", UserTest.getUserJSONArray())
                        .accumulate("max-results", "50")
                        .accumulate("start-index", "0")
                        .accumulate("end-index", "2")
                )
                .accumulate("expand", "users");
    }

    @Test
    public void testDeserialize() throws Exception {
        Group group = new Group(new RestClient(null, new URI("/123/asd")), getGroupJSON());

        assertEquals(groupName, group.getName());
        assertEquals(groupName, group.getId());
        assertEquals(self, group.getSelf());
        assertEquals(1, group.getMembers().size());
    }

    @Test
    public void testGetGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupJSON());
        Group group = Group.get(restClient, groupName);

        assertEquals(groupName, group.getName());
        assertEquals(groupName, group.getId());
        assertEquals(self, group.getSelf());
        assertEquals(1, group.getMembers().size());
    }

    @Test(expected = JiraException.class)
    public void testGetGroup_NotExisting() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getErrorJSON());

        Group.get(restClient, groupName);
    }

    private JSONObject getErrorJSON() {
        return new JSONObject()
                .accumulate("errorMessages", new JSONArray().element("not existing group"))
                .accumulate("errors", new JSONObject());
    }

    @Test(expected = JiraException.class)
    public void testRestError() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenThrow(new RestException("noop", 500, "nix", null));

        Group.get(restClient, groupName);
    }
}
