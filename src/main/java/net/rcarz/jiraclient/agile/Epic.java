/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.rcarz.jiraclient.agile;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Represents an Agile Epic.
 *
 * @author pldupont
 */
public class Epic extends AgileResource {

    private Issue issue;
    private String key;
    private String summary;
    private boolean done;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Epic(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Retrieves the epic matching the ID.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the epic
     * @return an epic instance
     * @throws JiraException when the retrieval fails
     */
    public static Epic get(RestClient restclient, long id) throws JiraException {
        return AgileResource.get(restclient, Epic.class, RESOURCE_URI + "epic/" + id);
    }

    /**
     * @param refresh If true, will fetch the information from JIRA, otherwise use the cached info.
     * @return The Issue representation of this Epic.
     * @throws JiraException when the retrieval fails
     */
    public Issue asIssue(boolean refresh) throws JiraException {
        if (this.issue == null || refresh) {
            this.issue = Issue.get(getRestclient(), getId());
        }
        return this.issue;
    }

    /**
     * @return All issues in the Epic.
     * @throws JiraException when the retrieval fails
     */
    public List<Issue> getIssues() throws JiraException {
        return AgileResource.list(getRestclient(), Issue.class, RESOURCE_URI + "epic/" + getId() + "/issue", "issues");
    }

    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    @Override
    void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);

        this.key = Field.getString(json.get("key"));
        this.summary = Field.getString(json.get("summary"));
        this.done = Field.getBoolean(json.get("done"));
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isDone() {
        return done;
    }
}
