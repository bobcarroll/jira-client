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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * A simple JIRA REST client.
 */
public class JiraClient {

    private RestClient restclient = null;
    private String username = null;

    /**
     * Creates a JIRA client.
     *
     * @param uri Base URI of the JIRA server
     */
    public JiraClient(String uri) {
        this(uri, null);
    }

    /**
     * Creates an authenticated JIRA client.
     *
     * @param uri Base URI of the JIRA server
     * @param creds Credentials to authenticate with
     */
    public JiraClient(String uri, ICredentials creds) {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        restclient = new RestClient(httpclient, creds, URI.create(uri));

        if (creds != null)
            username = creds.getLogonName();
        }

    /**
     * Creates a new issue in the given project.
     *
     * @param project Key of the project to create in
     * @param issueType Name of the issue type to create
     *
     * @return a fluent create instance
     *
     * @throws JiraException when something goes wrong
     */
    public Issue.FluentCreate createIssue(String project, String issueType)
            throws JiraException {

        return Issue.create(restclient, project, issueType);
    }

    /**
     * Retreives the issue with the given key.
     *
     * @param key Issue key (PROJECT-123)
     *
     * @return an issue instance (issue includes all fields)
     *
     * @throws JiraException when something goes wrong
     */
    public Issue getIssue(String key) throws JiraException {
        return Issue.get(restclient, key);
    }

    /**
     * Retreives the issue with the given key.
     *
     * @param key Issue key (PROJECT-123)
     *
     * @param includedFields Specifies which issue fields will be included in
     * the result.
     * <br>Some examples how this parameter works:
     * <ul>
     * <li>*all - include all fields</li>
     * <li>*navigable - include just navigable fields</li>
     * <li>summary,comment - include just the summary and comments</li>
     * <li>*all,-comment - include all fields</li>
     * </ul>
     *
     * @return an issue instance
     *
     * @throws JiraException when something goes wrong
     */
    public Issue getIssue(String key, String includedFields) throws JiraException {
        return Issue.get(restclient, key, includedFields);
    }

    /**
     * Search for issues with the given query.
     *
     * @param jql JQL statement
     *
     * @return a search result structure with results (issues include all
     * navigable fields)
     *
     * @throws JiraException when the search fails
     */
    public Issue.SearchResult searchIssues(String jql)
            throws JiraException {

        return Issue.search(restclient, jql);
    }

    /**
     * Search for issues with the given query and specify which fields to
     * retrieve.
     *
     * @param jql JQL statement
     *
     * @param includedFields Specifies which issue fields will be included in
     * the result.
     * <br>Some examples how this parameter works:
     * <ul>
     * <li>*all - include all fields</li>
     * <li>*navigable - include just navigable fields</li>
     * <li>summary,comment - include just the summary and comments</li>
     * <li>*all,-comment - include all fields</li>
     * </ul>
     *
     *
     * @return a search result structure with results
     *
     * @throws JiraException when the search fails
     */
    public Issue.SearchResult searchIssues(String jql, String includedFields)
            throws JiraException {

        return Issue.search(restclient, jql, includedFields);
    }

    /**
     *
     * @return a list of all priorities available in the Jira installation
     * @throws JiraException
     */
    public List<Priority> getPriorities() throws JiraException {
        try {
            URI uri = restclient.buildURI(Resource.RESOURCE_URI + "priority");
            JSON response = restclient.get(uri);
            JSONArray prioritiesArray = JSONArray.fromObject(response);

            List<Priority> priorities = new ArrayList<Priority>(prioritiesArray.size());
            for (int i = 0; i < prioritiesArray.size(); i++) {
                JSONObject p = prioritiesArray.getJSONObject(i);
                priorities.add(new Priority(restclient, p));
            }

            return priorities;
        } catch (Exception ex) {
            throw new JiraException(ex.getMessage(), ex);
        }
    }

    /**
     * Get a list of options for a custom field
     *
     * @param field field id
     * @param project Key of the project context
     * @param issueType Name of the issue type
     *
     * @return a search result structure with results
     *
     * @throws JiraException when the search fails
     */
    public List<CustomFieldOption> getCustomFieldAllowedValues(String field, String project, String issueType) throws JiraException {
        JSONObject createMetadata = (JSONObject) Issue.getCreateMetadata(restclient, project, issueType);
        JSONObject fieldMetadata = (JSONObject) createMetadata.get(field);
        List<CustomFieldOption> customFieldOptions = Field.getResourceArray(
                CustomFieldOption.class,
                fieldMetadata.get("allowedValues"),
            restclient
        );
        return customFieldOptions;
    }

    /**
     * Get a list of options for a components
     *
     * @param project Key of the project context
     * @param issueType Name of the issue type
     *
     * @return a search result structure with results
     *
     * @throws JiraException when the search fails
     */
    public List<Component> getComponentsAllowedValues(String project, String issueType) throws JiraException {
        JSONObject createMetadata = (JSONObject) Issue.getCreateMetadata(restclient, project, issueType);
        JSONObject fieldMetadata = (JSONObject) createMetadata.get(Field.COMPONENTS);
        List<Component> componentOptions = Field.getResourceArray(
                Component.class,
                fieldMetadata.get("allowedValues"),
            restclient
        );
        return componentOptions;
    }

    public RestClient getRestClient() {
        return restclient;
    }

    public String getSelf() {
        return username;
    }


}

