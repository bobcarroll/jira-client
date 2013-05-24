/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.rcarz.jiraclient;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Represents issue votes.
 */
public final class Votes extends Resource {

    private String name = null;
    private int votes = 0;
    private boolean hasVoted = false;

    /**
     * Creates votes from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Votes(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        votes = Field.getInteger(map.get("votes"));
        hasVoted = Field.getBoolean(map.get("hasVoted"));
    }

    /**
     * Retrieves the given votes record.
     *
     * @param restclient REST client instance
     * @param issue Internal JIRA ID of the issue
     *
     * @return a votes instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Votes get(RestClient restclient, String issue)
        throws JiraException {

        JSONObject result = null;

        try {
            result = restclient.get(RESOURCE_URI + "issue/" + issue + "/votes");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve votes for issue " + issue, ex);
        }

        return new Votes(restclient, result);
    }

    @Override
    public String toString() {
        return Integer.toString(getVotes());
    }

    public int getVotes() {
        return votes;
    }

    public boolean hasVoted() {
        return hasVoted;
    }
}

