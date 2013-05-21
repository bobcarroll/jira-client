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

import java.lang.UnsupportedOperationException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Utility functions for translating between JSON and fields.
 */
public final class Field {

    public static final String ASSIGNEE = "assignee";
    public static final String ATTACHMENT = "attachment";
    public static final String COMMENT = "comment";
    public static final String COMPONENTS = "components";
    public static final String DESCRIPTION = "description";
    public static final String DUE_DATE = "duedate";
    public static final String FIX_VERSIONS = "fixVersions";
    public static final String ISSUE_TYPE = "issuetype";
    public static final String LABELS = "labels";
    public static final String PRIORITY = "priority";
    public static final String REPORTER = "reporter";
    public static final String STATUS = "status";
    public static final String SUMMARY = "summary";
    public static final String TIME_TRACKING = "timetracking";
    public static final String VERSIONS = "versions";

    private Field() { }

    /**
     * Gets a boolean value from the given object.
     *
     * @param b a Boolean instance
     *
     * @return a boolean primitive or false if b isn't a Boolean instance
     */
    public static boolean getBoolean(Object b) {
        boolean result = false;

        if (b instanceof Boolean)
            result = ((Boolean)b).booleanValue();

        return result;
    }

    /**
     * Gets a list of comments from the given object.
     *
     * @param c a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a list of components found in c
     */
    public static List<Comment> getComments(Object c, RestClient restclient) {
        List<Comment> results = new ArrayList<Comment>();

        if (c instanceof JSONObject && !((JSONObject)c).isNullObject())
            results = getResourceArray(Comment.class, ((Map)c).get("comments"), restclient);

        return results;
    }

    /**
     * Gets a date from the given object.
     *
     * @param d a string representation of a date
     *
     * @return a Date instance or null if d isn't a string
     */
    public static Date getDate(Object d) {
        Date result = null;

        if (d instanceof String) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            result = df.parse((String)d, new ParsePosition(0));
        }

        return result;
    }

    /**
     * Gets an integer from the given object.
     *
     * @param i an Integer instance
     *
     * @return an integer primitive or 0 if i isn't an Integer instance
     */
    public static int getInteger(Object i) {
        int result = 0;

        if (i instanceof Integer)
            result = ((Integer)i).intValue();

        return result;
    }

    /**
     * Gets a generic map from the given object.
     *
     * @param keytype Map key data type
     * @param valtype Map value data type
     * @param m a JSONObject instance
     *
     * @return a Map instance with all entries found in m
     */
    public static <TK extends Object, TV extends Object> Map<TK, TV> getMap(
        Class<TK> keytype, Class<TV> valtype, Object m) {

        Map<TK, TV> result = new HashMap<TK, TV>();

        if (m instanceof JSONObject && !((JSONObject)m).isNullObject()) {
            for (Object k : ((Map)m).keySet()) {
                Object v = ((Map)m).get(k);

                if (k.getClass() == keytype && v.getClass() == valtype)
                    result.put((TK)k, (TV)v);
            }
        }

        return result;
    }

    /**
     * Gets a JIRA resource from the given object.
     *
     * @param type Resource data type
     * @param r a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a Resource instance or null if r isn't a JSONObject instance
     */
    public static <T extends Resource> T getResource(
        Class<T> type, Object r, RestClient restclient) {

        T result = null;

        if (r instanceof JSONObject && !((JSONObject)r).isNullObject()) {
            if (type == Attachment.class)
                result = (T)new Attachment(restclient, (JSONObject)r);
            else if (type == Comment.class)
                result = (T)new Comment(restclient, (JSONObject)r);
            else if (type == Component.class)
                result = (T)new Component(restclient, (JSONObject)r);
            else if (type == IssueType.class)
                result = (T)new IssueType(restclient, (JSONObject)r);
            else if (type == Priority.class)
                result = (T)new Priority(restclient, (JSONObject)r);
            else if (type == Status.class)
                result = (T)new Status(restclient, (JSONObject)r);
            else if (type == User.class)
                result = (T)new User(restclient, (JSONObject)r);
            else if (type == Version.class)
                result = (T)new Version(restclient, (JSONObject)r);
        }

        return result;
    }

    /**
     * Gets a string from the given object.
     *
     * @param s a String instance
     *
     * @return a String or null if s isn't a String instance
     */
    public static String getString(Object s) {
        String result = null;

        if (s instanceof String)
            result = (String)s;

        return result;
    }

    /**
     * Gets a list of strings from the given object.
     *
     * @param sa a JSONArray instance
     *
     * @return a list of strings found in sa
     */
    public static List<String> getStringArray(Object sa) {
        List<String> results = new ArrayList<String>();

        if (sa instanceof JSONArray) {
            for (Object s : (JSONArray)sa) {
                if (s instanceof String)
                    results.add((String)s);
            }
        }

        return results;
    }

    /**
     * Gets a list of JIRA resources from the given object.
     *
     * @param type Resource data type
     * @param ra a JSONArray instance
     * @param restclient REST client instance
     *
     * @return a list of Resources found in ra
     */
    public static <T extends Resource> List<T> getResourceArray(
        Class<T> type, Object ra, RestClient restclient) {

        List<T> results = new ArrayList<T>();

        if (ra instanceof JSONArray) {
            for (Object v : (JSONArray)ra)
                if (type == Attachment.class)
                    results.add((T)new Attachment(restclient, (JSONObject)v));
                else if (type == Comment.class)
                    results.add((T)new Comment(restclient, (JSONObject)v));
                else if (type == Component.class)
                    results.add((T)new Component(restclient, (JSONObject)v));
                else if (type == IssueType.class)
                    results.add((T)new IssueType(restclient, (JSONObject)v));
                else if (type == Priority.class)
                    results.add((T)new Priority(restclient, (JSONObject)v));
                else if (type == Status.class)
                    results.add((T)new Status(restclient, (JSONObject)v));
                else if (type == User.class)
                    results.add((T)new User(restclient, (JSONObject)v));
                else if (type == Version.class)
                    results.add((T)new Version(restclient, (JSONObject)v));
        }

        return results;
    }

    /**
     * Gets a time tracking object from the given object.
     *
     * @param tt a JSONObject instance
     *
     * @return a TimeTracking instance or null if tt isn't a JSONObject instance
     */
    public static TimeTracking getTimeTracking(Object tt) {
        TimeTracking result = null;

        if (tt instanceof JSONObject && !((JSONObject)tt).isNullObject())
            result = new TimeTracking((JSONObject)tt);

        return result;
    }
}

