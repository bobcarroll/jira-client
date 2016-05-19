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

package net.rcarz.jiraclient.agile;

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

import java.util.List;

/**
 * An Agile extension to the JIRA client.
 * @see "https://docs.atlassian.com/jira-software/REST/cloud/"
 */
public class AgileClient {

    private RestClient restclient = null;

    /**
     * Creates an Agile client.
     *
     * @param jira JIRA client
     */
    public AgileClient(JiraClient jira) {
        restclient = jira.getRestClient();
    }

    /**
     * Retreives the board with the given ID.
     *
     * @param id Board ID
     *
     * @return a Board instance
     *
     * @throws JiraException when something goes wrong
     */
    public Board getBoard(int id) throws JiraException {
        return Board.get(restclient, id);
    }

    /**
     * Retreives all boards visible to the session user.
     *
     * @return a list of boards
     *
     * @throws JiraException when something goes wrong
     */
    public List<Board> getBoards() throws JiraException {
        return Board.getAll(restclient);
    }
}

