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
import net.sf.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static net.rcarz.jiraclient.Resource.getBaseUri;

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

    public static class SearchResult {
        public int start = 0;
        public int max = 0;
        public int total = 0;
        public List<Sprint> sprints = null;
        private SprintIterator sprintIterator;

        public SearchResult(RestClient restclient, Long boardId,
                            Integer maxResults, Integer startAt)
                throws JiraException {
            this.sprintIterator = new SprintIterator(
                    restclient,
                    boardId,
                    maxResults,
                    startAt
            );
            /* backwards compatibility shim - first page only */
            this.sprintIterator.hasNext();
            this.max = sprintIterator.maxResults;
            this.start = sprintIterator.startAt;
            this.sprints = sprintIterator.sprints;
            this.total = sprintIterator.total;
        }

        /**
         * All sprints found.
         *
         * @return All sprints found.
         */
        public Iterator<Sprint> iterator() {
            return sprintIterator;
        }
    }

    private static class SprintIterator implements Iterator<Sprint> {
        private Iterator<Sprint> currentPage;
        private RestClient restclient;
        private Sprint nextSprint;
        private Integer maxResults = -1;
        private Long boardId;
        private Integer startAt;
        private List<Sprint> sprints;
        private int total;

        public SprintIterator(RestClient restclient, Long boardId,  Integer maxResults, Integer startAt) throws JiraException {
            this.restclient = restclient;
            this.boardId = boardId;
            this.maxResults = maxResults;
            this.startAt = startAt;
        }

        public boolean hasNext() {
            if (nextSprint != null) {
                return true;
            }
            try {
                nextSprint = getNextSprint();
            } catch (JiraException e) {
                throw new RuntimeException(e);
            }
            return nextSprint != null;
        }

        public Sprint next() {
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            Sprint result = nextSprint;
            nextSprint = null;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException("Method remove() not support for class " +
                    this.getClass().getName());
        }

        private Sprint getNextSprint() throws JiraException {
            // first call
            if (currentPage == null) {
                currentPage = getNextSprints().iterator();
                if (currentPage == null || !currentPage.hasNext()) {
                    return null;
                } else {
                    return currentPage.next();
                }
            }

            // check if we need to get the next set of sprintss
            if (! currentPage.hasNext()) {
                currentPage = getNextSprints().iterator();
            }

            // return the next item if available
            if (currentPage.hasNext()) {
                return currentPage.next();
            } else {
                return null;
            }
        }

        private List<Sprint> getNextSprints() throws JiraException {
            if (sprints == null && startAt == null) {
                startAt = Integer.valueOf(0);
            } else if (sprints != null) {
                startAt = startAt + sprints.size();
            }

            JSON result = null;

            try {
                URI searchUri = createSearchURI(restclient, boardId, maxResults, startAt);
                result = restclient.get(searchUri);
            } catch (Exception ex) {
                throw new JiraException("Failed to search sprints", ex);
            }

            if (!(result instanceof JSONObject)) {
                throw new JiraException("JSON payload is malformed");
            }


            Map map = (Map) result;

            this.startAt = Field.getInteger(map.get("startAt"));
            this.maxResults = Field.getInteger(map.get("maxResults"));
            this.total = Field.getInteger(map.get("total"));
            this.sprints = AgileResource.getResourceArray(Sprint.class, map, restclient, "values");
            return sprints;
        }
    }

    private static URI createSearchURI(RestClient restclient, Long boardId, Integer maxResults,
                                       Integer startAt) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap<String, String>();
        if(maxResults != null){
            queryParams.put("maxResults", String.valueOf(maxResults));
        }
        if (startAt != null) {
            queryParams.put("startAt", String.valueOf(startAt));
        }

        URI searchUri = restclient.buildURI(RESOURCE_URI + "board/" + boardId + "/sprint", queryParams);
        return searchUri;
    }

    public static SearchResult getAllSprints(RestClient restclient, Long boardId, Integer maxResults, Integer startAt)
            throws JiraException {
        return new SearchResult(restclient, boardId, maxResults, startAt);
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
        startDate = Field.getDateTimeSprint(json.get("startDate"));
        endDate = Field.getDateTimeSprint(json.get("endDate"));
        completeDate = Field.getDateTimeSprint(json.get("completeDate"));
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

