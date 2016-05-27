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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Represents an issue work log.
 */
public class WorkLog extends Resource {

    private User author = null;
    private String comment = null;
    private Date created = null;
    private Date updated = null;
    private User updateAuthor = null;
    private Date started = null;
    private String timeSpent = null;
    private int timeSpentSeconds = 0;

    /**
     * Creates a work log from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected WorkLog(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        author = Field.getResource(User.class, map.get("author"), restclient);
        comment = Field.getString(map.get("comment"));
        created = Field.getDateTime(map.get("created"));
        updated = Field.getDateTime(map.get("updated"));
        updateAuthor = Field.getResource(User.class, map.get("updateAuthor"), restclient);
        started = Field.getDateTime(map.get("started"));
        timeSpent = Field.getString(map.get("timeSpent"));
        timeSpentSeconds = Field.getInteger(map.get("timeSpentSeconds"));
    }

    /**
     * Retrieves the given work log record.
     *
     * @param restclient REST client instance
     * @param issue Internal JIRA ID of the associated issue
     * @param id Internal JIRA ID of the work log
     *
     * @return a work log instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static WorkLog get(RestClient restclient, String issue, String id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "issue/" + issue + "/worklog/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve work log " + id + " on issue " + issue, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new WorkLog(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return created + " by " + author;
    }

    public User getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreatedDate() {
        return created;
    }

    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getUpdatedDate() {
        return updated;
    }

    public Date getStarted(){ return started; }

    public String getTimeSpent(){ return timeSpent; }

    public int getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

}

