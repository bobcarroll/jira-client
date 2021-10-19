package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class UserTest {

    private final static java.lang.String username = "joseph";
    private final static java.lang.String displayName = "Joseph McCarthy";
    private final static java.lang.String email = "joseph.b.mccarthy2012@googlemail.com";
    private final static java.lang.String userID = "10";
    private final static boolean isActive = true;
    private final static String self = "https://brainbubble.atlassian.net/rest/api/2/user?username=joseph";
    private final static String groupSelfTemplate = "https://brainbubble.atlassian.net/rest/api/2/group?groupname=";


    protected static JSONObject getUserJSON() {
        JSONObject json = new JSONObject();

        json.put("name", username);
        json.put("emailAddress", email);
        json.put("active", isActive);
        json.put("displayName", displayName);
        json.put("self", self);

        JSONObject images = new JSONObject();
        images.put("16x16", "https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16");
        images.put("24x24", "https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24");
        images.put("32x32", "https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32");
        images.put("48x48", "https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48");

        json.put("avatarUrls", images);
        json.put("id", "10");

        JSONArray items = new JSONArray();
        items.element(new JSONObject().accumulate("name", "jira-users").accumulate("self", groupSelfTemplate + "jira-users"));
        items.element(new JSONObject().accumulate("name", "a-team").accumulate("self", groupSelfTemplate + "a-team"));

        json.put("groups", new JSONObject().accumulate("size", 2).accumulate("items", items));

        return json;
    }

    protected static JSONArray getUserJSONArray() {
        JSONArray json = new JSONArray();
        json.add(getUserJSON());
        return json;
    }

    @Test
    public void testJSONDeserializer() throws Exception {
        User user = new User(new RestClient(null, new URI("/123/asd")), getUserJSON());
        assertEquals(user.getName(), username);
        assertEquals(user.getDisplayName(), displayName);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getId(), userID);

        Map<String, String> avatars = user.getAvatarUrls();

        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16", avatars.get("16x16"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24", avatars.get("24x24"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32", avatars.get("32x32"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48", avatars.get("48x48"));

        assertTrue(user.isActive());

        assertEquals(user.getGroups().size(), 2);
    }

    @Test
    public void testStatusToString() throws URISyntaxException {
        User user = new User(new RestClient(null, new URI("/123/asd")), getUserJSON());
        assertEquals(username, user.toString());
    }

    @Test(expected = JiraException.class)
    public void testGetUserJSONError() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get(anyString(), anyMap())).thenReturn(null);
        User.get(restClient, "username");

    }

    @Test(expected = JiraException.class)
    public void testGetUserRestError() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get(anyString(), anyMap())).thenThrow(Exception.class);
        User.get(restClient, "username");
    }

    @Test
    public void testGetUser() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get(anyString(), anyMap())).thenReturn(getUserJSON());
        final User user = User.get(restClient, "username");

        assertEquals(user.getName(), username);
        assertEquals(user.getDisplayName(), displayName);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getId(), userID);

        Map<String, String> avatars = user.getAvatarUrls();

        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16", avatars.get("16x16"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24", avatars.get("24x24"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32", avatars.get("32x32"));
        assertEquals("https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48", avatars.get("48x48"));

        assertTrue(user.isActive());
        assertEquals(user.getGroups().size(), 2);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        JiraClient jiraClient = getJiraClientMock(GroupTest.getGroupJSON(), GroupTest.getMemberJSON());
        when(jiraClient.getAllUsers(anyBoolean())).thenCallRealMethod();

        Collection<User> members = jiraClient.getAllUsers(false);
        assertEquals(GroupTest.GROUP_SIZE, members.size());
        assertEquals(username, members.iterator().next().getName());
    }

    private JiraClient getJiraClientMock(JSON firstResponse, JSON secondResponse) throws RestException, IOException {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get((URI) any())).thenReturn(firstResponse).thenReturn(secondResponse);
        JiraClient jiraClient = PowerMockito.mock(JiraClient.class);
        when(jiraClient.getRestClient()).thenReturn(restClient);
        return jiraClient;
    }
}
