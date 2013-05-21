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

import java.net.URI;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import net.sf.json.JSONObject;

/**
 * A simple JIRA REST client.
 */
public class JiraClient {

    private RestClient restclient = null;

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
    public JiraClient(String uri, BasicCredentials creds) {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        if (creds != null && creds.username != null && !creds.username.equals("")) {
            httpclient.getCredentialsProvider().setCredentials(
                new AuthScope(creds.host, 443),
                new UsernamePasswordCredentials(creds.username, creds.password));
        }

        restclient = new RestClient(httpclient, URI.create(uri));
    }

    /**
     * Retreives the issue with the given key.
     *
     * @param key Issue key (PROJECT-123)
     *
     * @return an issue instance
     *
     * @throws JiraException when something goes wrong
     */
    public Issue getIssue(String key) throws JiraException {
        return Issue.get(restclient, key);
    }

    public RestClient getRestClient() {
        return restclient;
    }
}

