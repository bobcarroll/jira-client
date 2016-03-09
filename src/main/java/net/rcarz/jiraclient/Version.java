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

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Represents a product version.
 */
public class Version extends Resource {

    /**
     * Used to chain fields to a create action.
     */
    public static final class FluentCreate {
        /**
         * The Jira rest client.
         */
        RestClient restclient = null;

        /**
         * The JSON request that will be built incrementally as fluent methods
         * are invoked.
         */
        JSONObject req = new JSONObject();

        /**
         * Creates a new fluent.
         * @param restclient the REST client
         * @param project the project key
         */
        private FluentCreate(RestClient restclient, String project) {
            this.restclient = restclient;
            req.put("project", project);
        }

        /**
         * Sets the name of the version.
         * @param name the name
         * @return <code>this</code>
         */
        public FluentCreate name(String name) {
            req.put("name", name);
            return this;
        }

        /**
         * Sets the description of the version.
         * @param description the description
         * @return <code>this</code>
         */
        public FluentCreate description(String description) {
            req.put("description", description);
            return this;
        }

        /**
         * Sets the archived status of the version.
         * @param isArchived archived status
         * @return <code>this</code>
         */
        public FluentCreate archived(boolean isArchived) {
            req.put("archived", isArchived);
            return this;
        }

        /**
         * Sets the released status of the version.
         * @param isReleased released status
         * @return <code>this</code>
         */
        public FluentCreate released(boolean isReleased) {
            req.put("released", isReleased);
            return this;
        }

        /**
         * Sets the release date of the version.
         * @param releaseDate release Date
         * @return <code>this</code>
         */
        public FluentCreate releaseDate(Date releaseDate) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            req.put("releaseDate", df.format(releaseDate));
            return this;
        }




        /**
         * Executes the create action.
         * @return the created Version
         *
         * @throws JiraException when the create fails
         */
        public Version execute() throws JiraException {
            JSON result = null;

            try {
                result = restclient.post(getRestUri(null), req);
            } catch (Exception ex) {
                throw new JiraException("Failed to create version", ex);
            }

            if (!(result instanceof JSONObject) || !((JSONObject) result).containsKey("id")
                    || !(((JSONObject) result).get("id") instanceof String)) {
                throw new JiraException("Unexpected result on create version");
            }

            return new Version(restclient, (JSONObject) result);
        }
    }

    private String name = null;
    private boolean archived = false;
    private boolean released = false;
    private String releaseDate;
    private String description = null;

    /**
     * Creates a version from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected Version(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }
    
    /**
     * Merges the given version with current version
     * 
     * @param version
     *            The version to merge
     */
    public void mergeWith(Version version) throws JiraException {
    
        JSONObject req = new JSONObject();
        req.put("description", version.getDescription());
        req.put("name", version.getName());
        req.put("archived", version.isArchived());
        req.put("released", version.isReleased());
        req.put("releaseDate", version.getReleaseDate());

        try {
            restclient.put(Resource.getBaseUri() + "version/" + id, req);
        } catch (Exception ex) {
            throw new JiraException("Failed to merge", ex);
        }
    }

    /**
    * Copies the version to the given project
    * 
    * @param project
    *            The project the version will be copied to
    */
    public void copyTo(Project project) throws JiraException {
    
        JSONObject req = new JSONObject();
        req.put("description", getDescription());
        req.put("name", getName());
        req.put("archived", isArchived());
        req.put("released", isReleased());
        req.put("releaseDate", getReleaseDate());
        req.put("project", project.getKey());
        req.put("projectId", project.getId());
      
        try {
            restclient.post(Resource.getBaseUri() + "version/", req);
        } catch (Exception ex) {
            throw new JiraException("Failed to copy to project '" + project.getKey() + "'", ex);
        }
    }

    /**
     * Retrieves the given version record.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the version
     * @return a version instance
     * @throws JiraException when the retrieval fails
     */
    public static Version get(RestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "version/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve version " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Version(restclient, (JSONObject) result);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        name = Field.getString(map.get("name"));
        archived = Field.getBoolean(map.get("archived"));
        released = Field.getBoolean(map.get("released"));
        releaseDate = Field.getString(map.get("releaseDate"));
        description = Field.getString(map.get("description"));
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isReleased() {
        return released;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;

    }

    private static String getRestUri(String id) {
        return getBaseUri() + "version/" + (id != null ? id : "");
    }

    /**
     * Creates a new JIRA Version.
     *
     * @param restclient REST client instance
     * @param project Key of the project to create the version in
     *
     * @return a fluent create instance
     */
    public static FluentCreate create(RestClient restclient, String project) {
        FluentCreate fc = new FluentCreate(restclient, project);
        return fc;
    }
}

