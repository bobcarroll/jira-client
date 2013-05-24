/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.rcarz.jiraclient;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Represents a JIRA issue.
 */
public final class Issue extends Resource {

    /**
     * Used to chain fields to an update action.
     */
    public final class FluentUpdate {

        Map<String, Object> fields = new HashMap<String, Object>();
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

            if (fields.size() == 0)
                throw new JiraException("No fields were given for update");

            for (Map.Entry<String, Object> ent : fields.entrySet()) {
                Object newval = Field.toJson(ent.getKey(), ent.getValue(), editmeta);
                fieldmap.put(ent.getKey(), newval);
            }

            JSONObject req = new JSONObject();
            req.put("fields", fieldmap);

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
    }

    /**
     * Used to chain fields to an transition action.
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
    private IssueType issueType = null;
    private List<String> labels = null;
    private Priority priority = null;
    private Project project = null;
    private User reporter = null;
    private Status status = null;
    private String summary = null;
    private TimeTracking timeTracking = null;
    private List<Version> versions = null;
    private Votes votes = null;
    private Watches watches = null;

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
        attachments = Field.getResourceArray(Attachment.class, fields.get(Field.ASSIGNEE), restclient);
        comments = Field.getComments(fields.get(Field.COMMENT), restclient);
        components = Field.getResourceArray(Component.class, fields.get(Field.COMPONENTS), restclient);
        description = Field.getString(fields.get(Field.DESCRIPTION));
        dueDate = Field.getDate(fields.get(Field.DUE_DATE));
        fixVersions = Field.getResourceArray(Version.class, fields.get(Field.FIX_VERSIONS), restclient);
        issueType = Field.getResource(IssueType.class, fields.get(Field.ISSUE_TYPE), restclient);
        labels = Field.getStringArray(fields.get(Field.LABELS));
        priority = Field.getResource(Priority.class, fields.get(Field.PRIORITY), restclient);
        project = Field.getResource(Project.class, fields.get(Field.PROJECT), restclient);
        reporter = Field.getResource(User.class, fields.get(Field.REPORTER), restclient);
        status = Field.getResource(Status.class, fields.get(Field.STATUS), restclient);
        summary = Field.getString(fields.get(Field.SUMMARY));
        timeTracking = Field.getTimeTracking(fields.get(Field.TIME_TRACKING));
        versions = Field.getResourceArray(Version.class, fields.get(Field.VERSIONS), restclient);
        votes = Field.getResource(Votes.class, fields.get(Field.VOTES), restclient);
        watches = Field.getResource(Watches.class, fields.get(Field.WATCHES), restclient);
    }

    private static String getRestUri(String key) {
        return RESOURCE_URI + "issue/" + key;
    }

    private JSONObject getEditMetadata() throws JiraException {
        JSONObject result = null;

        try {
            result = restclient.get(getRestUri(key) + "/editmeta");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve issue metadata", ex);
        }

        if (result.isNullObject() || !result.containsKey("fields") ||
                !(result.get("fields") instanceof JSONObject))
            throw new JiraException("Edit metadata is malformed");

        return (JSONObject)result.get("fields");
    }

    private JSONArray getTransitions() throws JiraException {
        JSONObject result = null;

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

        if (result.isNullObject() || !result.containsKey("transitions") ||
                !(result.get("transitions") instanceof JSONArray))
            throw new JiraException("Transition metadata is missing from results");

        return (JSONArray)result.get("transitions");
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
            throw new JiraException("Failed to retrieve issue " + key, ex);
        }
    }

    /**
     * Retrieves the given issue record.
     *
     * @param restclient REST client instance
     * @param key Issue key (PROJECT-123)
     *
     * @return an issue instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, String key)
        throws JiraException {

        JSONObject result = null;

        try {
            result = restclient.get(getRestUri(key));
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve issue " + key, ex);
        }

        return new Issue(restclient, result);
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

    public Status getStatus() {
        return status;
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
}

