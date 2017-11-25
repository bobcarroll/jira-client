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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.rcarz.jiraclient.Filter;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Board Configuration.
 *
 * @author SelAnt
 */
public class BoardConfiguration extends AgileResource {

	private String type;
	private List<BoardColumn> columns;
	
    /**
     * Retrieve all sprints related to the specified board.
     *
     * @param restclient REST client instance
     * @param boardId    The Internal JIRA board ID.
     * @return The list of sprints associated to the board.
     * @throws JiraException when the retrieval fails
     */
    static BoardConfiguration get(RestClient restclient, long boardId) throws JiraException {
        return AgileResource.get(restclient, BoardConfiguration.class, RESOURCE_URI + "board/" + boardId+"/configuration");
    }

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public BoardConfiguration(RestClient restclient, JSONObject json) throws JiraException {
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
        this.type = json.getString("type");
        setName(json.getString("name"));
        //TODO: filter
        Filter filter = new Filter(getRestclient(), json.getJSONObject("filter"));
        /*
	       	"filter": {
			"id": "36869",
			"self": "https://jira.pointclickcare.com/jira/rest/api/2/filter/36869"	
			},
         */
        JSONObject columnConfig = json.getJSONObject("columnConfig");
        //TODO: columnConfig.getString("constraintType")
        columns = getResourceArray(BoardColumn.class, columnConfig , getRestclient(),"columns");

        //Statuses in each Column do not have names - need to fetch all known statuses
        List<net.rcarz.jiraclient.Status> statuses = net.rcarz.jiraclient.Status.getAll(getRestclient());
        HashMap<String,net.rcarz.jiraclient.Status> statMap = new HashMap<>(statuses.size());
		for (net.rcarz.jiraclient.Status status : statuses) {
			statMap.put(status.getId(), status);
		}
		//set names to statuses in Board Columns
		for (BoardColumn column : columns) {
			for (Status st : column.getStatuses()) {
				net.rcarz.jiraclient.Status status = statMap.get(String.valueOf(st.getId()));
				if (status != null) {
					st.setName(status.getName());
				}
			}
		}
        //TODO: estimation and ranking fields
        /*
			"estimation": {
				"type": "field",
				"field": {
					"fieldId": "customfield_10276",
					"displayName": "Estimated Points"
				}
			},
			"ranking": {
				"rankCustomFieldId": 18210
			}
         */
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, name='%s'}", getClass().getSimpleName(), getId(), getName());
    }

    public String getType() {
		return type;
	}

	public List<BoardColumn> getColumns() {
		return columns;
	}

}
