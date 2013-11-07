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

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper Rapid Board.
 */
public class RapidView extends GreenHopperResource {

    private String name = null;
    private boolean canEdit = false;
    private boolean sprintSupportEnabled = false;

    /**
     * Creates a rapid view from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected RapidView(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        name = Field.getString(map.get("name"));
        canEdit = Field.getBoolean(map.get("canEdit"));
        sprintSupportEnabled = Field.getBoolean(map.get("sprintSupportEnabled"));
    }

    /**
     * Retrieves the given rapid view.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the rapid view
     *
     * @return a rapid view instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static RapidView get(RestClient restclient, int id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(RESOURCE_URI + "rapidview/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve rapid view " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new RapidView(restclient, (JSONObject)result);
    }

    /**
     * Retrieves all rapid views visible to the session user.
     *
     * @param restclient REST client instance
     *
     * @return a list of rapid views
     *
     * @throws JiraException when the retrieval fails
     */
    public static List<RapidView> getAll(RestClient restclient)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(RESOURCE_URI + "rapidview");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve rapid views", ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        JSONObject jo = (JSONObject)result;

        if (!jo.containsKey("views") || !(jo.get("views") instanceof JSONArray))
            throw new JiraException("Rapid View result is malformed");

        return GreenHopperField.getResourceArray(
            RapidView.class,
            jo.get("views"),
            restclient
        );
    }

    /**
     * Retrieves all sprints associated with this rapid view.
     *
     * @return a list of sprints
     *
     * @throws JiraException when the retrieval fails
     */
    public List<Sprint> getSprints() throws JiraException {
        JSON result = null;

        try {
            result = restclient.get(RESOURCE_URI + "sprintquery/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve sprints", ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        JSONObject jo = (JSONObject)result;

        if (!jo.containsKey("sprints") || !(jo.get("sprints") instanceof JSONArray))
            throw new JiraException("Sprints result is malformed");

        return GreenHopperField.getResourceArray(
            Sprint.class,
            jo.get("sprints"),
            restclient
        );
    }

    /**
     * Retrieves the sprint report for the given sprint.
     *
     * @param sprint Sprint to lookup
     *
     * @return the sprint report
     *
     * @throws JiraException when the retrieval fails
     */
    public SprintReport getSprintReport(Sprint sprint) throws JiraException {
        return SprintReport.get(restclient, this, sprint);
    }

    /**
     * Retrieves the backlog data for this rapid view.
     *
     * @return the backlog
     *
     * @throws JiraException when the retrieval fails
     */
    public Backlog getBacklogData() throws JiraException {
        return Backlog.get(restclient, this);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Boolean canEdit() {
        return canEdit;
    }

    public Boolean isSprintSupportEnabled() {
        return sprintSupportEnabled;
    }
}

