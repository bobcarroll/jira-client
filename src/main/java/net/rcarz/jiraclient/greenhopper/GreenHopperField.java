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

import net.rcarz.jiraclient.RestClient;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Utility functions for translating between JSON and fields.
 */
public final class GreenHopperField {

    public static final String DATE_TIME_FORMAT = "d/MMM/yy h:m a";

    private GreenHopperField() { }

    /**
     * Gets a date-time from the given object.
     *
     * @param dt Date-Time as a string
     *
     * @return the date-time or null
     */
    public static DateTime getDateTime(Object dt) {
        return dt != null ?
            DateTime.parse((String)dt, DateTimeFormat.forPattern(DATE_TIME_FORMAT)) :
            null;
    }

    /**
     * Gets a list of integers from the given object.
     *
     * @param ia a JSONArray instance
     *
     * @return a list of integers
     */
    public static List<Integer> getIntegerArray(Object ia) {
        List<Integer> results = new ArrayList<Integer>();

        if (ia instanceof JSONArray) {
            for (Object v : (JSONArray)ia)
                results.add((Integer)v);
        }

        return results;
    }

    /**
     * Gets a GreenHopper resource from the given object.
     *
     * @param type Resource data type
     * @param r a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a Resource instance or null if r isn't a JSONObject instance
     */
    public static <T extends GreenHopperResource> T getResource(
        Class<T> type, Object r, RestClient restclient) {

        T result = null;

        if (r instanceof JSONObject && !((JSONObject)r).isNullObject()) {
            if (type == EstimateStatistic.class)
                result = (T)new EstimateStatistic(restclient, (JSONObject)r);
            else if (type == RapidView.class)
                result = (T)new RapidView(restclient, (JSONObject)r);
            else if (type == Sprint.class)
                result = (T)new Sprint(restclient, (JSONObject)r);
            else if (type == SprintIssue.class)
                result = (T)new SprintIssue(restclient, (JSONObject)r);
        }

        return result;
    }

    /**
     * Gets a list of GreenHopper resources from the given object.
     *
     * @param type Resource data type
     * @param ra a JSONArray instance
     * @param restclient REST client instance
     *
     * @return a list of Resources found in ra
     */
    public static <T extends GreenHopperResource> List<T> getResourceArray(
        Class<T> type, Object ra, RestClient restclient) {

        List<T> results = new ArrayList<T>();

        if (ra instanceof JSONArray) {
            for (Object v : (JSONArray)ra) {
                T item = getResource(type, v, restclient);
                if (item != null)
                    results.add(item);
            }
        }

        return results;
    }
}

