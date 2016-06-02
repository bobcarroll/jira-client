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

import java.util.Date;

/**
 * Represents an Agile Worklog.
 *
 * @author pldupont
 */
public class Worklog extends AgileResource {

    private User author;
    private String comment;
    private Date created;
    private Date updated;
    private User updateAuthor;
    private Date started;
    private String timeSpent;
    private long timeSpentSeconds;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Worklog(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
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
        this.author = getSubResource(User.class, json, "author");
        this.comment = Field.getString(json.get("comment"));
        this.created = Field.getDateTime(json.get("created"));
        this.updated = Field.getDateTime(json.get("updated"));
        this.updateAuthor = getSubResource(User.class, json, "updateAuthor");
        this.started = Field.getDateTime(json.get("started"));
        this.timeSpent = Field.getString(json.get("timeSpent"));
        this.timeSpentSeconds = Field.getLong(json.get("timeSpentSeconds"));
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, comment='%s'}", getClass().getSimpleName(), getId(), getComment());
    }

    public User getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getStarted() {
        return started;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }
}
