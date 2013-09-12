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

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

import java.util.List;

/**
 * A GreenHopper extension to the JIRA client.
 */
public class GreenHopperClient {

    private RestClient restclient = null;

    /**
     * Creates a GreenHopper client.
     *
     * @param jira JIRA client
     */
    public GreenHopperClient(JiraClient jira) {
        restclient = jira.getRestClient();
    }

    /**
     * Retreives the rapid view with the given ID.
     *
     * @param id Rapid View ID
     *
     * @return a RapidView instance
     *
     * @throws JiraException when something goes wrong
     */
    public RapidView getRapidView(int id) throws JiraException {
        return RapidView.get(restclient, id);
    }

    /**
     * Retreives all rapid views visible to the session user.
     *
     * @return a list of rapid views
     *
     * @throws JiraException when something goes wrong
     */
    public List<RapidView> getRapidViews() throws JiraException {
        return RapidView.getAll(restclient);
    }
}

