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

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Represents a product version.
 */
public class Version extends Resource {

    private String name = null;
    private boolean archived = false;
    private boolean released = false;

    /**
     * Creates a version from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Version(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        name = Field.getString(map.get("name"));
        archived = Field.getBoolean(map.get("archived"));
        released = Field.getBoolean(map.get("released"));
    }

    /**
     * Retrieves the given version record.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the version
     *
     * @return a version instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Version get(RestClient restclient, String id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(RESOURCE_URI + "version/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve version " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Version(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isReleased() {
        return released;
    }
}

