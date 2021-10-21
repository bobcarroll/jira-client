package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class GroupTest {

    private static final String groupName = "special-group";
    private static final String self = "http://anyhost.org/rest/api/2/group?groupname=" + groupName;
    public static final int GROUP_SIZE = 420;

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

    protected static JSON getMemberJSON() {
        JSONArray users = new JSONArray();
        users.add(UserTest.getUserJSON());
        for (int i = 1; i < GROUP_SIZE; i++) {
            String name = "user-" + i;
            users.add(new JSONObject()
                    .accumulate("name", name)
                    .accumulate("key", name)
                    .accumulate("emailAddress", name+"@universum.org")
                    .accumulate("displayName", name)
                    .accumulate("active", true)
                    .accumulate("timezone", "Europe/Berlin"));
        }

        return new JSONObject()
                .accumulate("maxResults", String.valueOf(GROUP_SIZE))
                .accumulate("total", String.valueOf(GROUP_SIZE))
                .accumulate("isLast", true)
                .accumulate("startAt", "0")
                .accumulate("values", users);
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
        when(restClient.get((URI) any()))
                .thenReturn(getGroupJSON())
                .thenReturn(getMemberJSON());
        Group group = Group.get(restClient, groupName);

        assertEquals(groupName, group.getName());
        assertEquals(groupName, group.getId());
        assertEquals(self, group.getSelf());
        assertEquals(GROUP_SIZE, group.getMembers().size());
    }

    @Test
    public void testCreateGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.post((URI) any(), (JSON) any())).thenReturn(getGroupJSON());
        Group group = Group.create(restClient, groupName);
        assertEquals(groupName, group.getName());
    }

    @Test
    public void testHasGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupPickerJSON());
        assertTrue(Group.hasGroup(restClient, groupName));
        assertTrue(Group.hasGroup(restClient, "some-group"));
        assertFalse(Group.hasGroup(restClient, "not existing group"));
    }

    @Test
    public void testFindGroup() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(getGroupPickerJSON());
        Collection<String> searchResult = Group.findGroups(restClient, "group");
        assertTrue(searchResult.contains(groupName));
        assertTrue(searchResult.contains("some-group"));
        assertTrue(searchResult.contains("other-group"));
        assertEquals(3, searchResult.size());
    }

    @Test
    public void testAddMember() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any()))
                .thenReturn(getGroupJSON())
                .thenReturn(getMemberJSON());
        Group group = Group.get(restClient, groupName);

        when(restClient.get(anyString(), anyMap())).thenReturn(UserTest.getUserJSON());
        User newMember = User.get(restClient, "fritz");
        when(restClient.post((URI) any(), (JSON) any())).thenReturn(getGroupJSON());
        group.addUser(newMember);
    }

    @Test
    public void testRemoveMember() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any()))
                .thenReturn(getGroupJSON())
                .thenReturn(getMemberJSON());
        Group group = Group.get(restClient, groupName);

        when(restClient.get(anyString(), anyMap())).thenReturn(UserTest.getUserJSON());
        User member = User.get(restClient, "joseph");
        group.removeUser(member);
        assertEquals(GROUP_SIZE - 1, group.getMembers().size());
    }

    @Test
    public void testGetAllMembers() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any()))
                .thenReturn(getGroupJSON())
                .thenReturn(getMemberJSON());
        Group group = Group.get(restClient, groupName);
        assertEquals(GROUP_SIZE, group.getMembers().size());
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
