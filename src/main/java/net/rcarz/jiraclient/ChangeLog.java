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

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Issue change log.
 */
public class ChangeLog extends Resource {
    /**
     * List of change log entries.
     */
    private List<ChangeLogEntry> entries = null;

    /**
     * Creates a change log from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected ChangeLog(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Deserializes a change log from a json payload.
     * @param json the json payload
     */
    private void deserialise(JSONObject json) {
        Map map = json;

        entries = Field.getResourceArray(ChangeLogEntry.class, map.get(
                Field.CHANGE_LOG_ENTRIES), restclient);
    }

    /**
     * Returns the list of change log entries in the change log.
     * @return the list of entries
     */
    public List<ChangeLogEntry> getEntries() {
        return entries;
    }
}
