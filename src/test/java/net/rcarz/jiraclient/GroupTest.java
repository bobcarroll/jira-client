package net.rcarz.jiraclient;

import net.sf.json.JSON;
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

    @Test
    public void testCreateGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.post((URI) any(), (JSON) any())).thenReturn(getGroupJSON());
        Group group = Group.create(restClient, groupName);
        assertEquals(groupName, group.getName());
    }

    @Test
    public void testFindGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupPickerJSON());
        assertTrue(Group.hasGroup(restClient, groupName));
        assertTrue(Group.hasGroup(restClient, "some-group"));
        assertFalse(Group.hasGroup(restClient, "not existing group"));
    }

    @Test
    public void testAddMember() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupJSON());
        Group group = Group.get(restClient, groupName);

        when(restClient.get(anyString(), anyMap())).thenReturn(UserTest.getUserJSON());
        User newMember = User.get(restClient, "fritz");
        when(restClient.put((URI) any(), (JSON) any())).thenReturn(getGroupJSON());
        group.addUser(newMember);
    }

    @Test
    public void testRemoveMember() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupJSON());
        Group group = Group.get(restClient, groupName);

        when(restClient.get(anyString(), anyMap())).thenReturn(UserTest.getUserJSON());
        User member = User.get(restClient, "fritz");

        when(restClient.delete((URI) any())).thenReturn(getGroupJSON());
        group.removeUser(member);
        assertTrue(group.getMembers().isEmpty());
    }

    @Test(expected = JiraException.class)
    public void testGetGroup_NotExisting() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getErrorJSON());

        Group.get(restClient, groupName);
    }

    @Test(expected = JiraException.class)
    public void testRestError() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenThrow(new RestException("noop", 500, "nix", null));

        Group.get(restClient, groupName);
    }

    private JSONObject getErrorJSON() {
        return new JSONObject()
                .accumulate("errorMessages", new JSONArray().element("not existing group"))
                .accumulate("errors", new JSONObject());
    }

    private JSON getGroupPickerJSON() {
        JSONArray groups = new JSONArray();
        groups.add(new JSONObject().accumulate("name", "some-group").accumulate("html", "<b>some-group<b>"));
        groups.add(new JSONObject().accumulate("name", "other-group").accumulate("html", "<b>other-group<b>"));
        groups.add(new JSONObject().accumulate("name", groupName).accumulate("html", "<b>" + groupName + "<b>"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("header", "Showing 3 of 3 matching groups");
        jsonObject.accumulate("total", 3);
        jsonObject.accumulate("groups", groups);
        return jsonObject;
    }
}
