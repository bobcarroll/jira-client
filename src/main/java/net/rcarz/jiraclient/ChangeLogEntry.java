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

import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Contains information about an issue change log entry. Change log entries are
 * not returned by default when fetching issues. The <code>changelog</code>
 * field (expansion) must be explicitly provided in
 * {@link JiraClient#getIssue(String, String)}.
 */
public class ChangeLogEntry extends Resource {
    /**
     * Changelog author.
     */
    private User author = null;

    /**
     * Date when the changelog entry was created.
     */
    private Date created = null;

    /**
     * List of change log items in the change log entry.
     */
    private List<ChangeLogItem> items = null;

    /**
     * Creates a change log from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected ChangeLogEntry(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Deserializes a change log entry from a json payload.
     * @param json the json payload
     */
    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getString(map.get("id"));
        author = Field.getResource(User.class, map.get("author"), restclient);
        created = Field.getDateTime(map.get("created"));
        items = Field.getResourceArray(ChangeLogItem.class, map.get(
                Field.CHANGE_LOG_ITEMS), restclient);
    }

    /**
     * Obtains the author of the change log entry.
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Returns the date when the change log entry was created.
     * @return the date
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Returns the list of items in the change log entry.
     * @return the list of items
     */
    public List<ChangeLogItem> getItems() {
        return items;
    }
}
