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

import net.rcarz.jiraclient.RestClient;

/**
 * A base class for GreenHopper resources.
 */
public abstract class GreenHopperResource {

    protected static final String RESOURCE_URI = "/rest/greenhopper/1.0/";

    protected RestClient restclient = null;
    protected int id = 0;

    /**
     * Creates a new GreenHopper resource.
     *
     * @param restclient REST client instance
     */
    public GreenHopperResource(RestClient restclient) {
        this.restclient = restclient;
    }

    /**
     * Internal JIRA ID.
     */
    public int getId() {
        return id;
    }
}

