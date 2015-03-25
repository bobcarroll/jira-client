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

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Represents an issue comment.
 */
public class Comment extends Resource {

    private User author = null;
    private String body = null;
    private Date created = null;
    private Date updated = null;
    private User updatedAuthor = null;

    /**
     * Creates a comment from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Comment(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        author = Field.getResource(User.class, map.get("author"), restclient);
        body = Field.getString(map.get("body"));
        created = Field.getDateTime(map.get("created"));
        updated = Field.getDateTime(map.get("updated"));
        updatedAuthor = Field.getResource(User.class, map.get("updatedAuthor"), restclient);
    }

    /**
     * Retrieves the given comment record.
     *
     * @param restclient REST client instance
     * @param issue Internal JIRA ID of the associated issue
     * @param id Internal JIRA ID of the comment
     *
     * @return a comment instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Comment get(RestClient restclient, String issue, String id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "issue/" + issue + "/comment/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve comment " + id + " on issue " + issue, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Comment(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return created + " by " + author;
    }

    public User getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedDate() {
        return created;
    }

    public User getUpdateAuthor() {
        return updatedAuthor;
    }

    public Date getUpdatedDate() {
        return updated;
    }
}

