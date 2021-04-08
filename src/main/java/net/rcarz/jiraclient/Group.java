package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A group of users in JIRA.
 */
public class Group extends Resource {

    private Collection<User> members;

    /**
     * Creates a new Group
     *
     * @param restclient REST client instance
     * @param payload The JSON representation of the group
     */
    protected Group(RestClient restclient, JSONObject payload) {
        super(restclient);
        if (payload != null) {
            deserialize(payload);
        }
    }

    /**
     * Retrieves the Group record including the members.
     * @param restClient REST client instance
     * @param groupName The name of the group
     * @return The obtained group
     * @throws JiraException failed to obtain the group
     */
    public static Group get(RestClient restClient, String groupName) throws JiraException {
        JSON response = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("groupname", groupName);
            params.put("expand", "users");
            URI getGroupUri = restClient.buildURI("api/2/group", params);
            response = restClient.get(getGroupUri);
        } catch (Exception e) {
            throw new JiraException("Problem getting Group: "+ groupName, e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException("Problem getting Group: "+ jsonObject.getString("errorMessages"));
        }

        return new Group(restClient, jsonObject);
    }

    private void deserialize(JSONObject json) {
        id = json.getString("name");
        self = json.getString("self");
        if (json.containsKey("users")) {
            members = Field.getResourceArray(User.class, json.getJSONObject("users").getJSONArray("items"), restclient);
        }
    }

    public String getName() {
        return getId();
    }

    public Collection<User> getMembers() {
        return Collections.unmodifiableCollection(members);
    }
}
