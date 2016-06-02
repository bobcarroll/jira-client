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
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * A base class for Agile resources.
 *
 * @author pldupont
 * @see "https://docs.atlassian.com/jira-software/REST/cloud/"
 */
public abstract class AgileResource {

    public static final String ATTR_ID = "id";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_SELF = "self";

    public static final String RESOURCE_URI = "/rest/agile/1.0/";

    private RestClient restclient = null;
    private long id = 0;
    private String name;
    private String self;
    private JSONObject attributes = new JSONObject();

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     * @throws JiraException when the retrieval fails
     */
    public AgileResource(RestClient restclient, JSONObject json) throws JiraException {
        this.restclient = restclient;
        if (json != null) {
            deserialize(json);
        }
    }

    /**
     * Gets an Agile resource from the given object.
     *
     * @param type       Resource data type
     * @param r          a JSONObject instance
     * @param restclient REST client instance
     * @return a Resource instance or null if r isn't a JSONObject instance
     * @throws JiraException when the retrieval fails
     */
    protected static <T extends AgileResource> T getResource(
            Class<T> type, Object r, RestClient restclient) throws JiraException {

        if (!(r instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        T result = null;

        if (!((JSONObject) r).isNullObject()) {
            try {
                Constructor<T> constructor = type.getDeclaredConstructor(RestClient.class, JSONObject.class);
                result = constructor.newInstance(restclient, r);
            } catch (Exception e) {
                throw new JiraException("Failed to deserialize object.", e);
            }
        }

        return result;
    }

    /**
     * Gets a list of GreenHopper resources from the given object.
     *
     * @param type       Resource data type
     * @param ra         a JSONArray instance
     * @param restclient REST client instance
     * @param listName   The name of the list of items from the JSON result.
     * @return a list of Resources found in ra
     * @throws JiraException when the retrieval fails
     */
    protected static <T extends AgileResource> List<T> getResourceArray(
            Class<T> type, Object ra, RestClient restclient, String listName) throws JiraException {
        if (!(ra instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        JSONObject jo = (JSONObject) ra;

        if (!jo.containsKey(listName) || !(jo.get(listName) instanceof JSONArray)) {
            throw new JiraException("No array found for name '" + listName + "'");
        }

        List<T> results = new ArrayList<T>();

        for (Object v : (JSONArray) jo.get(listName)) {
            T item = getResource(type, v, restclient);
            if (item != null) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Retrieves all boards visible to the session user.
     *
     * @param restclient REST client instance
     * @param type       The type of the object to deserialize.
     * @param url        The URL to call.
     * @return a list of boards
     * @throws JiraException when the retrieval fails
     */
    static <T extends AgileResource> List<T> list(
            RestClient restclient, Class<T> type, String url) throws JiraException {
        return list(restclient, type, url, "values");
    }

    /**
     * Retrieves all boards visible to the session user.
     *
     * @param restclient REST client instance
     * @param type       The type of the object to deserialize.
     * @param url        The URL to call.
     * @param listName   The name of the list of items in the JSON response.
     * @return a list of boards
     * @throws JiraException when the retrieval fails
     */
    static <T extends AgileResource> List<T> list(
            RestClient restclient, Class<T> type, String url, String listName) throws JiraException {

        JSON result;
        try {
            result = restclient.get(url);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve a list of " + type.getSimpleName() + " : " + url, ex);
        }

        return getResourceArray(
                type,
                result,
                restclient,
                listName
        );
    }

    /**
     * Retrieves all boards visible to the session user.
     *
     * @param restclient REST client instance
     * @return a list of boards
     * @throws JiraException when the retrieval fails
     */
    static <T extends AgileResource> T get(RestClient restclient, Class<T> type, String url) throws JiraException {

        JSON result;
        try {
            result = restclient.get(url);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve " + type.getSimpleName() + " : " + url, ex);
        }

        return getResource(
                type,
                result,
                restclient
        );
    }

    /**
     * Extract from a sub list the Resource array, if present.
     *
     * @param type         Resource data type
     * @param subJson      a JSONObject instance
     * @param resourceName The name of the list of items from the JSON result.
     * @param <T>          The type of Agile resource to return.
     * @return The list of resources if present.
     * @throws JiraException when the retrieval fails
     */
    <T extends AgileResource> List<T> getSubResourceArray(
            Class<T> type, JSONObject subJson, String resourceName) throws JiraException {
        List<T> result = null;
        if (subJson.containsKey(resourceName)) {
            result = getResourceArray(type, subJson.get(resourceName), getRestclient(), resourceName + "s");
        }
        return result;
    }

    /**
     * Extract from a sub list the Resource, if present.
     *
     * @param type         Resource data type
     * @param subJson      a JSONObject instance
     * @param resourceName The name of the item from the JSON result.
     * @param <T>          The type of Agile resource to return.
     * @return The resource if present.
     * @throws JiraException when the retrieval fails
     */
    <T extends AgileResource> T getSubResource(
            Class<T> type, JSONObject subJson, String resourceName) throws JiraException {
        T result = null;
        if (subJson.containsKey(resourceName) && !subJson.get(resourceName).equals("null")) {
            result = getResource(type, subJson.get(resourceName), getRestclient());
        }
        return result;
    }

    /**
     * @return Internal JIRA ID.
     */
    public long getId() {
        return id;
    }

    /**
     * @return The resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Setter for the resource name. In some case, the name is called something else.
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * @return The resource URL.
     */
    public String getSelfURL() {
        return self;
    }

    /**
     * @return The REST client used to access the current resource.
     */
    protected RestClient getRestclient() {
        return restclient;
    }

    /**
     * Retrieve the specified attribute as a generic object.
     *
     * @param name The name of the attribute to retrieve.
     * @return The value of the attribute.
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    void deserialize(JSONObject json) throws JiraException {

        id = getLong(json.get("id"));
        name = Field.getString(json.get("name"));
        self = Field.getString(json.get("self"));
        addAttributes(json);
    }

    /**
     * Allow to add more attributes.
     *
     * @param json The json object to extract attributes from.
     */
    void addAttributes(JSONObject json) {
        attributes.putAll(json);
    }

    long getLong(Object o) {
        if (o instanceof Integer || o instanceof Long) {
            return Field.getLong(o);
        } else if (o instanceof String && NumberUtils.isDigits((String) o)) {
            return NumberUtils.toLong((String) o, 0L);
        } else {
            return 0L;
        }
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, name='%s'}", getClass().getSimpleName(), id, name);
    }
}

