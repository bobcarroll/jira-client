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
import net.rcarz.jiraclient.RestClient;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper sprint issue.
 */
public class SprintIssue extends GreenHopperIssue {

    private String epic = null;
    private EstimateStatistic estimateStatistic = null;

    /**
     * Creates a sprint issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected SprintIssue(RestClient restclient, JSONObject json) {
        super(restclient, json);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        epic = Field.getString(map.get("epic"));
        estimateStatistic = GreenHopperField.getEstimateStatistic(map.get("estimateStatistic"));
    }

    public String getEpic() {
        return epic;
    }

    public EstimateStatistic getEstimateStatistic() {
        return estimateStatistic;
    }
}

