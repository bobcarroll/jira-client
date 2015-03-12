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

import net.sf.json.JSONObject;

/**
 * Item in a {@link ChangeLogEntry}.
 */
public class ChangeLogItem extends Resource {
    /**
     * Field changed.
     */
    private String field = null;

    /**
     * Type of field changed.
     */
    private String fieldType = null;

    /**
     * What the field changed from.
     */
    private String from = null;

    /**
     * What the field changed from in user-readable format.
     */
    private String fromString = null;

    /**
     * What the field changed to.
     */
    private String to = null;

    /**
     * What the field changed to in user-readable format.
     */
    private String toString = null;

    /**
     * Creates a change log item from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected ChangeLogItem(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Deserializes the json payload.
     * @param json the json payload
     */
    private void deserialise(JSONObject json) {
        Map map = json;

        field = Field.getString(map.get("field"));
        fieldType = Field.getString(map.get("fieldtype"));
        from = Field.getString(map.get("from"));
        fromString = Field.getString(map.get("fromString"));
        to = Field.getString(map.get("to"));
        toString = Field.getString(map.get("toString"));
    }

    /**
     * Obtains the field changed.
     * @return the field changed
     */
    public String getField() {
        return field;
    }

    /**
     * Obtains the type of field changed.
     * @return the type of field
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * Obtains the value the field was changed from.
     * @return the value
     */
    public String getFrom() {
        return from;
    }

    /**
     * Obtains the value the field was changed from.
     * @return the value in user-readable format
     */
    public String getFromString() {
        return fromString;
    }

    /**
     * Obtains the value the field was changed to.
     * @return the value
     */
    public String getTo() {
        return to;
    }

    /**
     * Obtains the value the field was changed to.
     * @return the value in user-readable format
     */
    public String getToString() {
        return toString;
    }
}
