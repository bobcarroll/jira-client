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

import org.apache.http.Header;

/**
 * An exception for JIRA REST errors.
 */
public class RestException extends Exception {

    private int status;
    private String result;
    private Header[] headers;

    public RestException(String msg, int status, String result, Header[] headers) {
        super(msg);

        this.status = status;
        this.result = result;
        this.headers = headers;
    }

    public int getHttpStatusCode() {
        return status;
    }

    public String getHttpResult() {
        return result;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public String getMessage() {
        return String.format("%s %s: %s", Integer.toString(status), super.getMessage(), result);
    }
}
