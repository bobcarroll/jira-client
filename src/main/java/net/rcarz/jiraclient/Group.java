package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            URI getGroupUri = restClient.buildURI(getBaseUri() + "group", params);
            response = restClient.get(getGroupUri);
        } catch (Exception e) {
            throw new JiraException("Problem getting Group: "+ groupName, e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException("Problem getting Group: "+ jsonObject.getString("errorMessages"));
        }

        Group group = new Group(restClient, jsonObject);
        group.loadMembers();
        return group;
    }

    /**
     * Creates a new Group.
     * @param restClient REST client instance
     * @param groupName The name of the new group
     * @return The recently created group
     * @throws JiraException failed to create the group
     */
    public static Group create(RestClient restClient, String groupName)  throws JiraException {
        JSON response = null;
        JSONObject payload = new JSONObject().accumulate("name", groupName);
        try {
            URI createGroupUri = restClient.buildURI(getBaseUri() +"group");
            response = restClient.post(createGroupUri, payload);
        } catch (Exception e) {
            throw new JiraException("Problem creating Group: "+ groupName, e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException("Problem getting Group: "+ jsonObject.getString("errorMessages"));
        }

        return new Group(restClient, jsonObject);
    }

    /**
     * Searches for Groups with that name (exact match).
     * @param restClient REST client instance
     * @param groupName The group to look for
     * @return true, when the group with exact given name exists, false if not.
     * @throws JiraException failed on search groups
     */
    public static boolean hasGroup(RestClient restClient, String groupName) throws JiraException {
        JSON response = null;
        try {
            Map<String, String> params = Collections.singletonMap("query", groupName);
            URI findUri = restClient.buildURI(getBaseUri() + "groups/picker", params);
            response = restClient.get(findUri);
        } catch (Exception e) {
            throw new JiraException("Problem searching Groups with name: "+ groupName, e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException("Problem searching Groups with name: "+ jsonObject.getString("errorMessages"));
        }

        return ((JSONObject) response).getJSONArray("groups").stream()
                .anyMatch(obj ->((JSONObject) obj).getString("name").equalsIgnoreCase(groupName));


    }

    /**
     * Get the name of the Group
     * @return The name of the Group
     */
    public String getName() {
        return getId();
    }

    /**
     * Get the members of the Group
     * @return The users which are in this Group
     */
    public Collection<User> getMembers() {
        return Collections.unmodifiableCollection(members);
    }

    /**
     * Adds the given User to this Group
     * @param user The User to add
     * @throws JiraException on any problem adding the user
     */
    public void addUser(User user) throws JiraException {
        JSON response = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("groupname", this.getName());
            URI addUserUri = restclient.buildURI("rest/api/2/group/user", params);
            // TODO: JIRA requires Websudo here ...
            response = restclient.post(addUserUri, new JSONObject().accumulate("name", user.getName()));
        } catch (Exception e) {
            throw new JiraException(String.format("Problem add User: %s to Group: %s", user.getName(), getName()),e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException(String.format("Problem add User: %s to Group: %s - Reason: %s",
                    user.getName(), getName(), jsonObject.getString("errorMessages")));
        }
        deserialize(jsonObject);
        loadMembers();
    }

    /**
     * Removes the given user from this group
     * @param user The user to remove
     * @throws JiraException on any problem removing this user
     */
    public void removeUser(User user) throws JiraException  {
        JSON response = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("groupname", this.getName());
            params.put("username", user.getName());
            URI removeUserUri = restclient.buildURI("api/2/group/user", params);
            // TODO: JIRA requires Websudo here ...
            response = restclient.delete(removeUserUri);
        } catch (Exception e) {
            throw new JiraException(String.format("Problem remove User: %s from Group: %s", user.getName(), getName()),e);
        }

        JSONObject jsonObject = (JSONObject) response;
        if (jsonObject != null && jsonObject.containsKey("errors")) {
            throw new JiraException(String.format("Problem remove User: %s from Group: %s - Reason: %s",
                    user.getName(), getName(), jsonObject.getString("errorMessages")));
        }
        // remove the member (be aware User has no equals)
        members = members.stream().filter(m -> m.getId() != user.getId()).collect(Collectors.toList());
    }

    private void deserialize(JSONObject json) {
        id = json.getString("name");
        self = json.getString("self");
        if (json.containsKey("users")) {
            members = Field.getResourceArray(User.class, json.getJSONObject("users").getJSONArray("items"), restclient);
        }
    }

    private void loadMembers() throws JiraException {
        boolean allMembersLoaded = false;
        int startAt = 0;
        JSONObject response = null;

        // get paginated results
        Map<String, String> params = new HashMap<>();
        params.put("groupname", getName());

        // get pages ...
        while (!allMembersLoaded) {
            try {
                params.put("startAt", String.valueOf(startAt));
                URI getGroupUri = restclient.buildURI( "rest/api/2/group/member", params);
                response = (JSONObject) restclient.get(getGroupUri);

                if (response.containsKey("errors")) {
                    throw new JiraException("Problem getting Group: " + response.getString("errorMessages"));
                }
                members.addAll(Field.getResourceArray(User.class, response.getJSONArray("values"), restclient));

                // prepare next page
                startAt = startAt + response.getInt("maxResults");
                allMembersLoaded = response.getBoolean("isLast");
            } catch (Exception e) {
                throw new JiraException("Problem getting Group-Members: " + e.getMessage(),e);
            }
        }
    }
}
