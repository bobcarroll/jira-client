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

package net.rcarz.jiraclient.greenhopper;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.rcarz.jiraclient.Version;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper JIRA project version.
 */
public class RapidViewVersion extends GreenHopperResource {

    private String name = null;
    private int sequence = 0;
    private boolean released = false;

    /**
     * Creates a version from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected RapidViewVersion(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        name = Field.getString(map.get("name"));
        sequence = Field.getInteger(map.get("sequence"));
        released = Field.getBoolean(map.get("released"));
    }

    /**
     * Retrieves the full JIRA version.
     *
     * @return a Version
     *
     * @throws JiraException when the retrieval fails
     */
    public Version getJiraVersion() throws JiraException {
        return Version.get(restclient, Integer.toString(id));
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public boolean isReleased() {
        return released;
    }
}

