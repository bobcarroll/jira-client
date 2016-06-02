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

/**
 * Represents an Agile TimeTracking.
 *
 * @author pldupont
 */
public class TimeTracking extends AgileResource {

    private String originalEstimate;
    private String remainingEstimate;
    private String timeSpent;
    private long originalEstimateSeconds;
    private long remainingEstimateSeconds;
    private long timeSpentSeconds;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public TimeTracking(RestClient restclient, JSONObject json) throws JiraException {
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
        this.originalEstimate = Field.getString(json.get("originalEstimate"));
        this.remainingEstimate = Field.getString(json.get("remainingEstimate"));
        this.timeSpent = Field.getString(json.get("timeSpent"));
        this.originalEstimateSeconds = Field.getLong(json.get("originalEstimateSeconds"));
        this.remainingEstimateSeconds = Field.getLong(json.get("remainingEstimateSeconds"));
        this.timeSpentSeconds = Field.getLong(json.get("timeSpentSeconds"));
    }

    @Override
    public String toString() {
        return String.format("%s{original='%s', remaining='%s', timeSpent='%s'}",
                getClass().getSimpleName(), getOriginalEstimate(), getRemainingEstimate(), getTimeSpent());
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public String getRemainingEstimate() {
        return remainingEstimate;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public long getOriginalEstimateSeconds() {
        return originalEstimateSeconds;
    }

    public long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }
}
