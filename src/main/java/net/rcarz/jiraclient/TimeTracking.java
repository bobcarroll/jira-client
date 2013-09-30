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

import net.sf.json.JSONObject;

/**
 * Represents issue time tracking data.
 */
public class TimeTracking {

    private String originalEstimate = null;
    private String remainingEstimate = null;
    private int originalEstimateSeconds = 0;
    private int remainingEstimateSeconds = 0;

    /**
     * Creates a time tracking structure from a JSON payload.
     *
     * @param json JSON payload
     */
    protected TimeTracking(JSONObject json) {
        Map map = json;

        originalEstimate = Field.getString(map.get("originalEstimate"));
        remainingEstimate = Field.getString(map.get("remainingEstimate"));
        originalEstimateSeconds = Field.getInteger(map.get("originalEstimateSeconds"));
        remainingEstimateSeconds = Field.getInteger(map.get("remainingEstimateSeconds"));
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public String getRemainingEstimate() {
        return remainingEstimate;
    }

    public int getOriginalEstimateSeconds() {
        return originalEstimateSeconds;
    }

    public int getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }
}

