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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * A base class for JIRA resources.
 */
public abstract class Resource {

    protected static final String RESOURCE_URI = "/rest/api/2/";

    protected RestClient restclient = null;

    protected String id = null;
    protected String self = null;

    /**
     * Creates a new JIRA resource.
     *
     * @param restclient REST client instance
     */
    public Resource(RestClient restclient) {
        this.restclient = restclient;
    }

    /**
     * Internal JIRA ID.
     */
    public String getId() {
        return id;
    }

    /**
     * REST API resource URL.
     */
    public String getUrl() {
        return self;
    }
}

