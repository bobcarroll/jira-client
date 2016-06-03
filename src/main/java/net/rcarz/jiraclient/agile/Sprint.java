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

import java.util.Date;
import java.util.List;

/**
 * Represents an Agile Sprint.
 *
 * @author pldupont
 */
public class Sprint extends AgileResource {

    private String state;
    private long originBoardId;
    private Date startDate;
    private Date endDate;
    private Date completeDate;

    /**
     * Creates a rapid view from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected Sprint(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Retrieve all sprints related to the specified board.
     *
     * @param restclient REST client instance
     * @param sprintId   The Internal JIRA sprint ID.
     * @return The sprint for the specified ID.
     * @throws JiraException when the retrieval fails
     */
    public static Sprint get(RestClient restclient, long sprintId) throws JiraException {
        return AgileResource.get(restclient, Sprint.class, RESOURCE_URI + "sprint/" + sprintId);
    }

    /**
     * Retrieve all sprints related to the specified board.
     *
     * @param restclient REST client instance
     * @param boardId    The Internal JIRA board ID.
     * @return The list of sprints associated to the board.
     * @throws JiraException when the retrieval fails
     */
    public static List<Sprint> getAll(RestClient restclient, long boardId) throws JiraException {
        return AgileResource.list(restclient, Sprint.class, RESOURCE_URI + "board/" + boardId + "/sprint");
    }

    /**
     * @return All issues in the Sprint.
     * @throws JiraException when the retrieval fails
     */
    public List<Issue> getIssues() throws JiraException {
        return AgileResource.list(getRestclient(), Issue.class, RESOURCE_URI + "sprint/" + getId() + "/issue", "issues");
    }

    @Override
    protected void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);
        state = Field.getString(json.get("state"));
        originBoardId = getLong(json.get("originBoardId"));
        startDate = Field.getDateTime(json.get("startDate"));
        endDate = Field.getDateTime(json.get("endDate"));
        completeDate = Field.getDateTime(json.get("completeDate"));
    }

    public String getState() {
        return state;
    }

    public long getOriginBoardId() {
        return originBoardId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }
}

