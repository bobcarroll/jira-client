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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents issue watches.
 */
public class Watches extends Resource {

    private String name = null;
    private int watchCount = 0;
    private boolean isWatching = false;
    private List<User> watchers = new ArrayList<User>();

    /**
     * Creates watches from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Watches(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        watchCount = Field.getInteger(map.get("watchCount"));
        isWatching = Field.getBoolean(map.get("isWatching"));
        watchers = Field.getResourceArray(User.class, map.get("watchers"), null);
    }

    /**
     * Retrieves the given watches record.
     *
     * @param restclient REST client instance
     * @param issue Internal JIRA ID of the issue
     *
     * @return a watches instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Watches get(RestClient restclient, String issue)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "issue/" + issue + "/watchers");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve watches for issue " + issue, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Watches(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return Integer.toString(getWatchCount());
    }

    public int getWatchCount() {
        return watchCount;
    }

    public boolean isWatching() {
        return isWatching;
    }

	public List<User> getWatchers() {
		return watchers;
	}
}

