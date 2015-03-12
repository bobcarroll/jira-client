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

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Represents an custom field option.
 */
public class CustomFieldOption extends Resource {

    private String value = null;

    /**
     * Creates a custom field option from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected CustomFieldOption(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        value = Field.getString(map.get("value"));
    }

    /**
     * Retrieves the given custom field option record.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the custom field option
     *
     * @return a custom field option instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static CustomFieldOption get(RestClient restclient, String id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "customFieldOption/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve custom field option " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new CustomFieldOption(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return value;
    }
}

