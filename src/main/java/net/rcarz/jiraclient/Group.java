package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
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
        JSON response;
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
        group.loadAllMembers();
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
        JSON response;
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
        return findGroups(restClient, groupName).stream().anyMatch(name ->name.equalsIgnoreCase(groupName));
    }

    /**
     * Searches for Groups which contain the given substring in their name.
     * @param restClient REST client interface
     * @param query A part of the Group-Names to find.
     * @return The list of matching group names.
     * @throws JiraException failed on search groups.
     */
    public static Collection<String> findGroups(RestClient restClient, String query) throws JiraException {
        JSON response;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("query", query);
            params.put("maxResults", "1000");
            URI findUri = restClient.buildURI(getBaseUri() + "groupuserpicker", params);
            response = restClient.get(findUri);
        } catch (Exception e) {
            throw new JiraException("Problem searching Groups with name: "+ query, e);
        }

        JSONObject responseObj = (JSONObject) response;
        if (responseObj != null && responseObj.containsKey("errors")) {
            throw new JiraException("Problem searching Groups with name: "+ responseObj.getString("errorMessages"));
        }

        Collection<String> names = new ArrayList<>();
        for (Object obj : responseObj.getJSONObject("groups").getJSONArray("groups")) {
            names.add(((JSONObject) obj).getString("name"));
        }
        return names;
    }

    /**
     * Deletes the group with that name (exact match).
     * @param restClient REST client interface
     * @param groupName The group to remove
     * @throws JiraException failed to delete group
     */
    public static void removeGroup(RestClient restClient, String groupName) throws  JiraException {
        try {
            Map<String, String> params = Collections.singletonMap("groupname", groupName);
            URI removeUri = restClient.buildURI(getBaseUri() + "group", params);
            restClient.delete(removeUri);
        } catch (Exception e) {
            throw new JiraException("Problem deleting Group with name: "+ groupName, e);
        }
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
        JSON response;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("groupname", this.getName());
            URI addUserUri = restclient.buildURI(getBaseUri() + "group/user", params);
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
        loadAllMembers();
    }

    /**
     * Removes the given user from this group
     * @param user The user to remove
     * @throws JiraException on any problem removing this user
     */
    public void removeUser(User user) throws JiraException  {
        JSON response;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("groupname", this.getName());
            params.put("username", user.getName());
            URI removeUserUri = restclient.buildURI(getBaseUri() + "group/user", params);
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
        members = members.stream().filter(m -> !m.getName().equalsIgnoreCase(user.getName())).collect(Collectors.toList());
    }

    private void deserialize(JSONObject json) {
        id = json.getString("name");
        self = json.getString("self");
        if (json.containsKey("users")) {
            members = Field.getResourceArray(User.class, json.getJSONObject("users").getJSONArray("items"), restclient);
        }
    }

    private void loadAllMembers() throws JiraException {
        boolean allMembersLoaded = false;
        int startAt = 0;
        JSONObject response;
        Collection<User> loadedMembers = new ArrayList<>();

        // get paginated results
        Map<String, String> params = new HashMap<>();
        params.put("groupname", getName());

        // get pages ...
        while (!allMembersLoaded) {
            try {
                params.put("startAt", String.valueOf(startAt));
                URI getGroupUri = restclient.buildURI( getBaseUri() + "group/member", params);
                response = (JSONObject) restclient.get(getGroupUri);

                if (response.containsKey("errors")) {
                    throw new JiraException("Problem getting Group: " + response.getString("errorMessages"));
                }
                loadedMembers.addAll(Field.getResourceArray(User.class, response.getJSONArray("values"), restclient));

                // prepare next page
                startAt = startAt + response.getInt("maxResults");
                allMembersLoaded = response.getBoolean("isLast");
            } catch (Exception e) {
                throw new JiraException("Problem getting Group-Members: " + e.getMessage(),e);
            }
        }
        // take over the freshly loaded members
        members.clear();
        members.addAll(loadedMembers);
    }
}
