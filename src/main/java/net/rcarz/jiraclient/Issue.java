/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.rcarz.jiraclient;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Represents a JIRA issue.
 */
public class Issue extends Resource {

    /**
     * Used to chain fields to a create action.
     */
    public static final class FluentCreate {

        Map<String, Object> fields = new HashMap<String, Object>();
        RestClient restclient = null;
        JSONObject createmeta = null;

        private FluentCreate(RestClient restclient, JSONObject createmeta) {
            this.restclient = restclient;
            this.createmeta = createmeta;
        }

        /**
         * Executes the create action (issue includes all fields).
         *
         * @throws JiraException when the create fails
         */
        public Issue execute() throws JiraException {
            return executeCreate(null);
        }

        /**
         * Executes the create action and specify which fields to retrieve.
         *
         * @param includedFields Specifies which issue fields will be included
         * in the result.
         * <br>Some examples how this parameter works:
         * <ul>
         * <li>*all - include all fields</li>
         * <li>*navigable - include just navigable fields</li>
         * <li>summary,comment - include just the summary and comments</li>
         * <li>*all,-comment - include all fields</li>
         * </ul>
         *
         * @throws JiraException when the create fails
         */
        public Issue execute(String includedFields) throws JiraException {
            return executeCreate(includedFields);
        }

        /**
         * Executes the create action and specify which fields to retrieve.
         *
         * @param includedFields Specifies which issue fields will be included
         * in the result.
         * <br>Some examples how this parameter works:
         * <ul>
         * <li>*all - include all fields</li>
         * <li>*navigable - include just navigable fields</li>
         * <li>summary,comment - include just the summary and comments</li>
         * <li>*all,-comment - include all fields</li>
         * </ul>
         *
         * @throws JiraException when the create fails
         */
        private Issue executeCreate(String includedFields) throws JiraException {
            JSONObject fieldmap = new JSONObject();

            if (fields.size() == 0) {
                throw new JiraException("No fields were given for create");
            }

            for (Map.Entry<String, Object> ent : fields.entrySet()) {
                Object newval = Field.toJson(ent.getKey(), ent.getValue(), createmeta);
                fieldmap.put(ent.getKey(), newval);
            }

            JSONObject req = new JSONObject();
            req.put("fields", fieldmap);

            JSON result = null;

            try {
                result = restclient.post(getRestUri(null), req);
            } catch (Exception ex) {
                throw new JiraException("Failed to create issue", ex);
            }

            if (!(result instanceof JSONObject) || !((JSONObject) result).containsKey("key")
                    || !(((JSONObject) result).get("key") instanceof String)) {
                throw new JiraException("Unexpected result on create issue");
            }

            if (includedFields != null) {
                return Issue.get(restclient, (String) ((JSONObject) result).get("key"), includedFields);
            } else {
                return Issue.get(restclient, (String) ((JSONObject) result).get("key"));
            }
        }

        /**
         * Appends a field to the update action.
         *
         * @param name Name of the field
         * @param value New field value
         *
         * @return the current fluent update instance
         */
        public FluentCreate field(String name, Object value) {
            fields.put(name, value);
            return this;
        }
    }

    /**
     * Used to chain fields to an update action.
     */
    public final class FluentUpdate {

        Map<String, Object> fields = new HashMap<String, Object>();
        Map<String, List> fieldOpers = new HashMap<String, List>();
        JSONObject editmeta = null;

        private FluentUpdate(JSONObject editmeta) {
            this.editmeta = editmeta;
        }

        /**
         * Executes the update action.
         *
         * @throws JiraException when the update fails
         */
        public void execute() throws JiraException {
            JSONObject fieldmap = new JSONObject();
            JSONObject updatemap = new JSONObject();

            if (fields.size() == 0 && fieldOpers.size() == 0)
                throw new JiraException("No fields were given for update");

            for (Map.Entry<String, Object> ent : fields.entrySet()) {
                Object newval = Field.toJson(ent.getKey(), ent.getValue(), editmeta);
                fieldmap.put(ent.getKey(), newval);
            }

            for (Map.Entry<String, List> ent : fieldOpers.entrySet()) {
                Object newval = Field.toJson(ent.getKey(), ent.getValue(), editmeta);
                updatemap.put(ent.getKey(), newval);
            }

            JSONObject req = new JSONObject();

            if (fieldmap.size() > 0)
                req.put("fields", fieldmap);

            if (updatemap.size() > 0)
                req.put("update", updatemap);

            try {
                restclient.put(getRestUri(key), req);
            } catch (Exception ex) {
                throw new JiraException("Failed to update issue " + key, ex);
            }
        }

        /**
         * Appends a field to the update action.
         *
         * @param name Name of the field
         * @param value New field value
         *
         * @return the current fluent update instance
         */
        public FluentUpdate field(String name, Object value) {
            fields.put(name, value);
            return this;
        }

        private FluentUpdate fieldOperation(String oper, String name, Object value) {
            if (!fieldOpers.containsKey(name))
                fieldOpers.put(name, new ArrayList());

            fieldOpers.get(name).add(new Field.Operation(oper, value));
            return this;
        }

        /**
         *  Adds a field value to the existing value set.
         *
         *  @param name Name of the field
         *  @param value Field value to append
         *
         *  @return the current fluent update instance
         */
        public FluentUpdate fieldAdd(String name, Object value) {
            return fieldOperation("add", name, value);
        }

        /**
         *  Removes a field value from the existing value set.
         *
         *  @param name Name of the field
         *  @param value Field value to remove
         *
         *  @return the current fluent update instance
         */
        public FluentUpdate fieldRemove(String name, Object value) {
            return fieldOperation("remove", name, value);
        }
    }

    /**
     * Used to chain fields to a transition action.
     */
    public final class FluentTransition {

        Map<String, Object> fields = new HashMap<String, Object>();
        JSONArray transitions = null;

        private FluentTransition(JSONArray transitions) {
            this.transitions = transitions;
        }

        private JSONObject getTransition(String id, boolean name) throws JiraException {
            JSONObject result = null;

            for (Object item : transitions) {
                if (!(item instanceof JSONObject) || !((JSONObject)item).containsKey("id"))
                    throw new JiraException("Transition metadata is malformed");

                JSONObject t = (JSONObject)item;

                if ((!name && Field.getString(t.get("id")).equals(id)) ||
                    (name && Field.getString(t.get("name")).equals(id))) {

                    result = t;
                    break;
                }
            }

            if (result == null)
                throw new JiraException("Transition was not found in metadata");

            return result;
        }

        private void realExecute(JSONObject trans) throws JiraException {

            if (trans.isNullObject() || !trans.containsKey("fields") ||
                    !(trans.get("fields") instanceof JSONObject))
                throw new JiraException("Transition metadata is missing fields");

            JSONObject editmeta = (JSONObject)trans.get("fields");
            JSONObject fieldmap = new JSONObject();

            for (Map.Entry<String, Object> ent : fields.entrySet()) {
                Object newval = Field.toJson(ent.getKey(), ent.getValue(), editmeta);
                fieldmap.put(ent.getKey(), newval);
            }

            JSONObject req = new JSONObject();

            if (fieldmap.size() > 0)
                req.put("fields", fieldmap);

            JSONObject t = new JSONObject();
            t.put("id", Field.getString(trans.get("id")));

            req.put("transition", t);

            try {
                restclient.post(getRestUri(key) + "/transitions", req);
            } catch (Exception ex) {
                throw new JiraException("Failed to transition issue " + key, ex);
            }
        }

        /**
         * Executes the transition action.
         *
         * @param id Internal transition ID
         *
         * @throws JiraException when the transition fails
         */
        public void execute(int id) throws JiraException {
            realExecute(getTransition(Integer.toString(id), false));
        }

        /**
         * Executes the transition action.
         *
         * @param name Transition name
         *
         * @throws JiraException when the transition fails
         */
        public void execute(String name) throws JiraException {
            realExecute(getTransition(name, true));
        }

        /**
         * Appends a field to the transition action.
         *
         * @param name Name of the field
         * @param value New field value
         *
         * @return the current fluent transition instance
         */
        public FluentTransition field(String name, Object value) {
            fields.put(name, value);
            return this;
        }
    }

    /**
     * Issue search results structure.
     */
    public static class SearchResult {
        public int start = 0;
        public int max = 0;
        public int total = 0;
        public List<Issue> issues = null;
    }

    private String key = null;
    private Map fields = null;

    /* system fields */
    private User assignee = null;
    private List<Attachment> attachments = null;
    private List<Comment> comments = null;
    private List<Component> components = null;
    private String description = null;
    private Date dueDate = null;
    private List<Version> fixVersions = null;
    private List<IssueLink> issueLinks = null;
    private IssueType issueType = null;
    private List<String> labels = null;
    private Priority priority = null;
    private Project project = null;
    private User reporter = null;
    private Resolution resolution = null;
    private Date resolutionDate = null;
    private Status status = null;
    private List<Issue> subtasks = null;
    private String summary = null;
    private TimeTracking timeTracking = null;
    private List<Version> versions = null;
    private Votes votes = null;
    private Watches watches = null;
    private List<WorkLog> workLogs = null;

    /**
     * Creates an issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Issue(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getString(map.get("id"));
        self = Field.getString(map.get("self"));
        key = Field.getString(map.get("key"));

        fields = (Map)map.get("fields");

        assignee = Field.getResource(User.class, fields.get(Field.ASSIGNEE), restclient);
        attachments = Field.getResourceArray(Attachment.class, fields.get(Field.ATTACHMENT), restclient);
        comments = Field.getComments(fields.get(Field.COMMENT), restclient);
        components = Field.getResourceArray(Component.class, fields.get(Field.COMPONENTS), restclient);
        description = Field.getString(fields.get(Field.DESCRIPTION));
        dueDate = Field.getDate(fields.get(Field.DUE_DATE));
        fixVersions = Field.getResourceArray(Version.class, fields.get(Field.FIX_VERSIONS), restclient);
        issueLinks = Field.getResourceArray(IssueLink.class, fields.get(Field.ISSUE_LINKS), restclient);
        issueType = Field.getResource(IssueType.class, fields.get(Field.ISSUE_TYPE), restclient);
        labels = Field.getStringArray(fields.get(Field.LABELS));
        priority = Field.getResource(Priority.class, fields.get(Field.PRIORITY), restclient);
        project = Field.getResource(Project.class, fields.get(Field.PROJECT), restclient);
        reporter = Field.getResource(User.class, fields.get(Field.REPORTER), restclient);
        resolution = Field.getResource(Resolution.class, fields.get(Field.RESOLUTION), restclient);
        resolutionDate = Field.getDate(fields.get(Field.RESOLUTION_DATE));
        status = Field.getResource(Status.class, fields.get(Field.STATUS), restclient);
        subtasks = Field.getResourceArray(Issue.class, fields.get(Field.SUBTASKS), restclient);
        summary = Field.getString(fields.get(Field.SUMMARY));
        timeTracking = Field.getTimeTracking(fields.get(Field.TIME_TRACKING));
        versions = Field.getResourceArray(Version.class, fields.get(Field.VERSIONS), restclient);
        votes = Field.getResource(Votes.class, fields.get(Field.VOTES), restclient);
        watches = Field.getResource(Watches.class, fields.get(Field.WATCHES), restclient);
        workLogs = Field.getWorkLogs(fields.get(Field.WORKLOG), restclient);
    }

    private static String getRestUri(String key) {
        return RESOURCE_URI + "issue/" + (key != null ? key : "");
    }

    public static JSONObject getCreateMetadata(
        RestClient restclient, String project, String issueType) throws JiraException {

        final String pval = project;
        final String itval = issueType;
        JSON result = null;

        try {
            URI createuri = restclient.buildURI(
                RESOURCE_URI + "issue/createmeta",
                new HashMap<String, String>() {{
                    put("expand", "projects.issuetypes.fields");
                    put("projectKeys", pval);
                    put("issuetypeNames", itval);
                }});
            result = restclient.get(createuri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve issue metadata", ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        JSONObject jo = (JSONObject)result;

        if (jo.isNullObject() || !jo.containsKey("projects") ||
                !(jo.get("projects") instanceof JSONArray))
            throw new JiraException("Create metadata is malformed");

        List<Project> projects = Field.getResourceArray(
            Project.class,
            (JSONArray)jo.get("projects"),
            restclient);

        if (projects.isEmpty() || projects.get(0).getIssueTypes().isEmpty())
            throw new JiraException("Project or issue type missing from create metadata");

        return projects.get(0).getIssueTypes().get(0).getFields();
    }

    private JSONObject getEditMetadata() throws JiraException {
        JSON result = null;

        try {
            result = restclient.get(getRestUri(key) + "/editmeta");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve issue metadata", ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        JSONObject jo = (JSONObject)result;

        if (jo.isNullObject() || !jo.containsKey("fields") ||
                !(jo.get("fields") instanceof JSONObject))
            throw new JiraException("Edit metadata is malformed");

        return (JSONObject)jo.get("fields");
    }

    private JSONArray getTransitions() throws JiraException {
        JSON result = null;

        try {
            URI transuri = restclient.buildURI(
                getRestUri(key) + "/transitions",
                new HashMap<String, String>() {{
                    put("expand", "transitions.fields");
                }});
            result = restclient.get(transuri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve transitions", ex);
        }

        JSONObject jo = (JSONObject)result;

        if (jo.isNullObject() || !jo.containsKey("transitions") ||
                !(jo.get("transitions") instanceof JSONArray))
            throw new JiraException("Transition metadata is missing from jos");

        return (JSONArray)jo.get("transitions");
    }
    
    /**
     * Adds an attachment to this issue.
     *
     * @param file java.io.File
     *
     * @throws JiraException when the comment creation fails
     */
    public void addAttachment(File file) throws JiraException {
        try {
            restclient.post(getRestUri(key) + "/attachments", file);
        } catch (Exception ex) {
            throw new JiraException("Failed add attachment to issue " + key, ex);
        }
    }

    /**
     * Adds a comment to this issue.
     *
     * @param body Comment text
     *
     * @throws JiraException when the comment creation fails
     */
    public void addComment(String body) throws JiraException {
        addComment(body, null, null);
    }

    /**
     * Adds a comment to this issue with limited visibility.
     *
     * @param body Comment text
     * @param visType Target audience type (role or group)
     * @param visName Name of the role or group to limit visibility to
     *
     * @throws JiraException when the comment creation fails
     */
    public void addComment(String body, String visType, String visName)
        throws JiraException {

        JSONObject req = new JSONObject();
        req.put("body", body);

        if (visType != null && visName != null) {
            JSONObject vis = new JSONObject();
            vis.put("type", visType);
            vis.put("value", visName);

            req.put("visibility", vis);
        }

        try {
            restclient.post(getRestUri(key) + "/comment", req);
        } catch (Exception ex) {
            throw new JiraException("Failed add comment to issue " + key, ex);
        }
    }

    /**
     * Links this issue with another issue.
     *
     * @param issue Other issue key
     * @param type Link type name
     *
     * @throws JiraException when the link creation fails
     */
    public void link(String issue, String type) throws JiraException {
        link(issue, type, null, null, null);
    }

    /**
     * Links this issue with another issue and adds a comment.
     *
     * @param issue Other issue key
     * @param type Link type name
     * @param body Comment text
     *
     * @throws JiraException when the link creation fails
     */
    public void link(String issue, String type, String body) throws JiraException {
        link(issue, type, body, null, null);
    }

    /**
     * Links this issue with another issue and adds a comment with limited visibility.
     *
     * @param issue Other issue key
     * @param type Link type name
     * @param body Comment text
     * @param visType Target audience type (role or group)
     * @param visName Name of the role or group to limit visibility to
     *
     * @throws JiraException when the link creation fails
     */
    public void link(String issue, String type, String body, String visType, String visName)
        throws JiraException {

        JSONObject req = new JSONObject();

        JSONObject t = new JSONObject();
        t.put("name", type);
        req.put("type", t);

        JSONObject inward = new JSONObject();
        inward.put("key", key);
        req.put("inwardIssue", inward);

        JSONObject outward = new JSONObject();
        outward.put("key", issue);
        req.put("outwardIssue", outward);

        if (body != null) {
            JSONObject comment = new JSONObject();
            comment.put("body", body);

            if (visType != null && visName != null) {
                JSONObject vis = new JSONObject();
                vis.put("type", visType);
                vis.put("value", visName);

                comment.put("visibility", vis);
            }

            req.put("comment", comment);
        }

        try {
            restclient.post(RESOURCE_URI + "issueLink", req);
        } catch (Exception ex) {
            throw new JiraException("Failed to link issue " + key + " with issue " + issue, ex);
        }
    }

    /**
     * Creates a new JIRA issue.
     *
     * @param restclient REST client instance
     * @param project Key of the project to create the issue in
     * @param issueType Name of the issue type to create
     *
     * @return a fluent create instance
     *
     * @throws JiraException when the client fails to retrieve issue metadata
     */
    public static FluentCreate create(RestClient restclient, String project, String issueType)
        throws JiraException {

        FluentCreate fc = new FluentCreate(
            restclient,
            getCreateMetadata(restclient, project, issueType));

        return fc
            .field(Field.PROJECT, project)
            .field(Field.ISSUE_TYPE, issueType);
    }

    /**
     * Creates a new sub-task.
     *
     * @return a fluent create instance
     *
     * @throws JiraException when the client fails to retrieve issue metadata
     */
    public FluentCreate createSubtask() throws JiraException {

        return Issue.create(restclient, getProject().getKey(), "Sub-task")
                .field("parent", getKey());
    }

    private static JSONObject realGet(RestClient restclient, String key, Map<String, String> queryParams)
            throws JiraException {

        JSON result = null;

        try {
            URI uri = restclient.buildURI(RESOURCE_URI + "issue/" + key, queryParams);
            result = restclient.get(uri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve issue " + key, ex);
        }

        if (!(result instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        return (JSONObject) result;
    }

    /**
     * Retrieves the given issue record.
     *
     * @param restclient REST client instance
     * @param key Issue key (PROJECT-123)
     *
     * @return an issue instance (issue includes all navigable fields)
     *
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, String key)
            throws JiraException {

        return new Issue(restclient, realGet(restclient, key, new HashMap<String, String>()));
    }

    /**
     * Retrieves the given issue record.
     *
     * @param restclient REST client instance
     *
     * @param key Issue key (PROJECT-123)
     *
     * @param includedFields Specifies which issue fields will be included in
     * the result.
     * <br>Some examples how this parameter works:
     * <ul>
     * <li>*all - include all fields</li>
     * <li>*navigable - include just navigable fields</li>
     * <li>summary,comment - include just the summary and comments</li>
     * <li>*all,-comment - include all fields</li>
     * </ul>
     *
     * @return an issue instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, String key, final String includedFields)
            throws JiraException {

        Map<String, String> queryParams = new HashMap<String, String>() {
            {
                put("fields", includedFields);
            }
        };
        return new Issue(restclient, realGet(restclient, key, queryParams));
    }

    /**
     * Search for issues with the given query.
     *
     * @param restclient REST client instance
     *
     * @param jql JQL statement
     *
     * @return a search result structure with results (issues include all
     * navigable fields)
     *
     * @throws JiraException when the search fails
     */
    public static SearchResult search(RestClient restclient, String jql)
            throws JiraException {
        return search(restclient, jql, null, null);
    }

    /**
     * Search for issues with the given query and specify which fields to
     * retrieve.
     *
     * @param restclient REST client instance
     *
     * @param jql JQL statement
     *
     * @param includedFields Specifies which issue fields will be included in
     * the result.
     * <br>Some examples how this parameter works:
     * <ul>
     * <li>*all - include all fields</li>
     * <li>*navigable - include just navigable fields</li>
     * <li>summary,comment - include just the summary and comments</li>
     * <li>*all,-comment - include all fields</li>
     * </ul>
     *
     * @return a search result structure with results
     *
     * @throws JiraException when the search fails
     */
    public static SearchResult search(RestClient restclient, String jql, String includedFields, Integer maxResults)
            throws JiraException {

        final String j = jql;
        JSON result = null;

        try {
            Map<String, String> queryParams = new HashMap<String, String>() {
                {
                    put("jql", j);
                }
            };
            if(maxResults != null){
            	queryParams.put("maxResults", String.valueOf(maxResults));
            }
            if (includedFields != null) {
                queryParams.put("fields", includedFields);
            }
            URI searchUri = restclient.buildURI(RESOURCE_URI + "search", queryParams);
            result = restclient.get(searchUri);
        } catch (Exception ex) {
            throw new JiraException("Failed to search issues", ex);
        }

        if (!(result instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        SearchResult sr = new SearchResult();
        Map map = (Map) result;

        sr.start = Field.getInteger(map.get("startAt"));
        sr.max = Field.getInteger(map.get("maxResults"));
        sr.total = Field.getInteger(map.get("total"));
        sr.issues = Field.getResourceArray(Issue.class, map.get("issues"), restclient);

        return sr;
    }

    /**
     * Reloads issue data from the JIRA server (issue includes all navigable
     * fields).
     *
     * @throws JiraException when the retrieval fails
     */
    public void refresh() throws JiraException {
        JSONObject result = realGet(restclient, key, new HashMap<String, String>());
        deserialise(result);
    }

    /**
     * Reloads issue data from the JIRA server and specify which fields to
     * retrieve.
     *
     * @param includedFields Specifies which issue fields will be included in
     * the result.
     * <br>Some examples how this parameter works:
     * <ul>
     * <li>*all - include all fields</li>
     * <li>*navigable - include just navigable fields</li>
     * <li>summary,comment - include just the summary and comments</li>
     * <li>*all,-comment - include all fields</li>
     * </ul>
     *
     * @throws JiraException when the retrieval fails
     */
    public void refresh(final String includedFields) throws JiraException {

        Map<String, String> queryParams = new HashMap<String, String>() {
            {
                put("fields", includedFields);
            }
        };
        JSONObject result = realGet(restclient, key, queryParams);
        deserialise(result);
    }

    /**
     * Gets an arbitrary field by its name.
     *
     * @param name Name of the field to retrieve
     *
     * @return the field value or null if not found
     */
    public Object getField(String name) {

        return fields != null ? fields.get(name) : null;
    }

    /**
     * Begins a transition field chain.
     *
     * @return a fluent transition instance
     *
     * @throws JiraException when the client fails to retrieve issue metadata
     */
    public FluentTransition transition() throws JiraException {
        return new FluentTransition(getTransitions());
    }

    /**
     * Begins an update field chain.
     *
     * @return a fluent update instance
     *
     * @throws JiraException when the client fails to retrieve issue metadata
     */
    public FluentUpdate update() throws JiraException {
        return new FluentUpdate(getEditMetadata());
    }

    /**
     * Casts a vote in favour of an issue.
     *
     * @throws JiraException when the voting fails
     */
    public void vote() throws JiraException {

        try {
            restclient.post(getRestUri(key) + "/votes");
        } catch (Exception ex) {
            throw new JiraException("Failed to vote on issue " + key, ex);
        }
    }

    /**
     * Removes the current user's vote from the issue.
     *
     * @throws JiraException when the voting fails
     */
    public void unvote() throws JiraException {

        try {
            restclient.delete(getRestUri(key) + "/votes");
        } catch (Exception ex) {
            throw new JiraException("Failed to unvote on issue " + key, ex);
        }
    }

    /**
     * Adds a watcher to the issue.
     *
     * @param username Username of the watcher to add
     *
     * @throws JiraException when the operation fails
     */
    public void addWatcher(String username) throws JiraException {

        try {
            URI uri = restclient.buildURI(getRestUri(key) + "/watchers");
            restclient.post(uri, username);
        } catch (Exception ex) {
            throw new JiraException(
                "Failed to add watcher (" + username + ") to issue " + key, ex
            );
        }
    }

    /**
     * Removes a watcher to the issue.
     *
     * @param username Username of the watcher to remove
     *
     * @throws JiraException when the operation fails
     */
    public void deleteWatcher(String username) throws JiraException {

        try {
            final String u = username;
            URI uri = restclient.buildURI(
                getRestUri(key) + "/watchers",
                new HashMap<String, String>() {{
                    put("username", u);
                }});
            restclient.delete(uri);
        } catch (Exception ex) {
            throw new JiraException(
                "Failed to remove watch (" + username + ") from issue " + key, ex
            );
        }
    }

    @Override
    public String toString() {
        return getKey();
    }

    public String getKey() {
        return key;
    }

    public User getAssignee() {
        return assignee;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Component> getComponents() {
        return components;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<Version> getFixVersions() {
        return fixVersions;
    }

    public List<IssueLink> getIssueLinks() {
        return issueLinks;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public List<String> getLabels() {
        return labels;
    }

    public Priority getPriority() {
        return priority;
    }

    public Project getProject() {
        return project;
    }

    public User getReporter() {
        return reporter;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public Status getStatus() {
        return status;
    }

    public List<Issue> getSubtasks() {
        return subtasks;
    }

    public String getSummary() {
        return summary;
    }

    public TimeTracking getTimeTracking() {
        return timeTracking;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public Votes getVotes() {
        return votes;
    }

    public Watches getWatches() {
        return watches;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }
}

