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
 * Represents an Agile Comment.
 *
 * @author pldupont
 */
public class Comment extends AgileResource {

    private User author;
    private String body;
    private User updateAuthor;
    private Date created;
    private Date updated;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Comment(RestClient restclient, JSONObject json) throws JiraException {
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
        this.body = Field.getString(json.get("body"));
        this.updateAuthor = getSubResource(User.class, json, "updateAuthor");
        this.created = Field.getDateTime(json.get("created"));
        this.updated = Field.getDateTime(json.get("updated"));
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, body='%s'}", getClass().getSimpleName(), getId(), getBody());
    }

    public User getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
}
