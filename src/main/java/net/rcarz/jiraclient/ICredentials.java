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

import org.apache.http.HttpRequest;

public interface ICredentials {

    void initialize(RestClient client) throws JiraException;
    /**
     * Sets the Authorization header for the given request.
     *
     * @param req HTTP request to authenticate
     */
    void authenticate(HttpRequest req);

    /**
     * Gets the logon name representing these credentials.
     *
     * @return logon name as a string
     */
    String getLogonName();

    void logout(RestClient client) throws JiraException;
}

