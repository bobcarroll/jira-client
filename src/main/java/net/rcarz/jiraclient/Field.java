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

import java.lang.Iterable;
import java.lang.UnsupportedOperationException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONNull;

/**
 * Utility functions for translating between JSON and fields.
 */
public final class Field {

    /**
     * Field metadata structure.
     */
    public static final class Meta {
        public boolean required;
        public String type;
        public String items;
        public String name;
        public String system;
        public String custom;
        public int customId;
    }

    /**
     * Field update operation.
     */
    public static final class Operation {
        public String name;
        public Object value;

        /**
         * Initialises a new update operation.
         *
         * @param name Operation name
         * @param value Field value
         */
        public Operation(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * Allowed value types.
     */
    public enum ValueType {
        KEY("key"), NAME("name"), ID_NUMBER("id"), VALUE("value");
        private String typeName;

        private ValueType(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }
    };

    /**
     * Value and value type pair.
     */
    public static final class ValueTuple {
        public final String type;
        public final Object value;

        /**
         * Initialises the value tuple.
         *
         * @param type
         * @param value
         */
        public ValueTuple(String type, Object value) {
            this.type = type;
            this.value = (value != null ? value : JSONNull.getInstance());
        }

        /**
         * Initialises the value tuple.
         *
         * @param type
         * @param value
         */
        public ValueTuple(ValueType type, Object value) {
            this(type.toString(), value);
        }
    }

    public static final String ASSIGNEE = "assignee";
    public static final String ATTACHMENT = "attachment";
    public static final String CHANGE_LOG = "changelog";
    public static final String CHANGE_LOG_ENTRIES = "histories";
    public static final String CHANGE_LOG_ITEMS = "items";
    public static final String COMMENT = "comment";
    public static final String COMPONENTS = "components";
    public static final String DESCRIPTION = "description";
    public static final String DUE_DATE = "duedate";
    public static final String FIX_VERSIONS = "fixVersions";
    public static final String ISSUE_LINKS = "issuelinks";
    public static final String ISSUE_TYPE = "issuetype";
    public static final String LABELS = "labels";
    public static final String PARENT = "parent";
    public static final String PRIORITY = "priority";
    public static final String PROJECT = "project";
    public static final String REPORTER = "reporter";
    public static final String RESOLUTION = "resolution";
    public static final String RESOLUTION_DATE = "resolutiondate";
    public static final String STATUS = "status";
    public static final String SUBTASKS = "subtasks";
    public static final String SUMMARY = "summary";
    public static final String TIME_TRACKING = "timetracking";
    public static final String VERSIONS = "versions";
    public static final String VOTES = "votes";
    public static final String WATCHES = "watches";
    public static final String WORKLOG = "worklog";
    public static final String TIME_ESTIMATE = "timeestimate";
    public static final String TIME_SPENT = "timespent";
    public static final String CREATED_DATE = "created";
    public static final String UPDATED_DATE = "updated";
    public static final String TRANSITION_TO_STATUS = "to";
    public static final String SECURITY = "security";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private Field() { }

    /**
     * Gets a boolean value from the given object.
     *
     * @param b a Boolean instance
     *
     * @return a boolean primitive or false if b isn't a Boolean instance
     */
    public static boolean getBoolean(Object b) {
        boolean result = false;

        if (b instanceof Boolean)
            result = ((Boolean)b).booleanValue();

        return result;
    }

    /**
     * Gets a list of comments from the given object.
     *
     * @param c a JSONObject instance
     * @param restclient REST client instance
     * @param issueKey key of the parent issue
     *
     * @return a list of comments found in c
     */
    public static List<Comment> getComments(Object c, RestClient restclient,
                                            String issueKey) {
        List<Comment> results = new ArrayList<Comment>();

        if (c instanceof JSONObject && !((JSONObject)c).isNullObject()) {
            results = getResourceArray(
                Comment.class,
                ((Map)c).get("comments"),
                restclient,
                issueKey
            );
        }

        return results;
    }

    /**
     * Gets a list of work logs from the given object.
     *
     * @param c a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a list of work logs found in c
     */
    public static List<WorkLog> getWorkLogs(Object c, RestClient restclient) {
        List<WorkLog> results = new ArrayList<WorkLog>();

        if (c instanceof JSONObject && !((JSONObject)c).isNullObject())
            results = getResourceArray(WorkLog.class, ((Map)c).get("worklogs"), restclient);

        return results;
    }
    
    /**
     * Gets a list of remote links from the given object.
     *
     * @param c a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a list of remote links found in c
     */
    public static List<RemoteLink> getRemoteLinks(Object c, RestClient restclient) {
        List<RemoteLink> results = new ArrayList<RemoteLink>();

        if (c instanceof JSONArray)
            results = getResourceArray(RemoteLink.class, c, restclient);

        return results;
    }

    /**
     * Gets a date from the given object.
     *
     * @param d a string representation of a date
     *
     * @return a Date instance or null if d isn't a string
     */
    public static Date getDate(Object d) {
        Date result = null;

        if (d instanceof String) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            result = df.parse((String)d, new ParsePosition(0));
        }

        return result;
    }

    /**
     * Gets a date with a time from the given object.
     *
     * @param d a string representation of a date
     *
     * @return a Date instance or null if d isn't a string
     */
    public static Date getDateTime(Object d) {
        Date result = null;

        if (d instanceof String) {
            SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
            result = df.parse((String)d, new ParsePosition(0));
        }

        return result;
    }

    /**
     * Gets an floating-point number from the given object.
     *
     * @param i an Double instance
     *
     * @return an floating-point number or null if i isn't a Double instance
     */
    public static Double getDouble(Object i) {
        Double result = null;

        if (i instanceof Double)
            result = (Double)i;

        return result;
    }

    /**
     * Gets an integer from the given object.
     *
     * @param i an Integer instance
     *
     * @return an integer primitive or 0 if i isn't an Integer instance
     */
    public static int getInteger(Object i) {
        int result = 0;

        if (i instanceof Integer)
            result = ((Integer)i).intValue();

        return result;
    }

    /**
     +     * Gets a long from the given object.
     +     *
     +     * @param i a Long or an Integer instance
     +     *
     +     * @return a long primitive or 0 if i isn't a Long or an Integer instance
     +     */
    public static long getLong(Object i) {
        long result = 0;
        if (i instanceof Long) {
            result = ((Long) i).longValue();
        } else if (i instanceof Integer) {
            result = ((Integer) i).intValue();
        }
        return result;
    }

    /**
     * Gets a generic map from the given object.
     *
     * @param keytype Map key data type
     * @param valtype Map value data type
     * @param m a JSONObject instance
     *
     * @return a Map instance with all entries found in m
     */
    public static <TK extends Object, TV extends Object> Map<TK, TV> getMap(
        Class<TK> keytype, Class<TV> valtype, Object m) {

        Map<TK, TV> result = new HashMap<TK, TV>();

        if (m instanceof JSONObject && !((JSONObject)m).isNullObject()) {
            for (Object k : ((Map)m).keySet()) {
                Object v = ((Map)m).get(k);

                if (k.getClass() == keytype && v.getClass() == valtype)
                    result.put((TK)k, (TV)v);
            }
        }

        return result;
    }

    /**
     * Gets a JIRA resource from the given object.
     *
     * @param type Resource data type
     * @param r a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a Resource instance or null if r isn't a JSONObject instance
     */
    public static <T extends Resource> T getResource(
        Class<T> type, Object r, RestClient restclient) {

        return getResource(type, r, restclient, null);
    }

    /**
     * Gets a JIRA resource from the given object.
     *
     * @param type Resource data type
     * @param r a JSONObject instance
     * @param restclient REST client instance
     * @param parentId id/key of the parent resource
     *
     * @return a Resource instance or null if r isn't a JSONObject instance
     */
    public static <T extends Resource> T getResource(
        Class<T> type, Object r, RestClient restclient, String parentId) {

        T result = null;

        if (r instanceof JSONObject && !((JSONObject)r).isNullObject()) {
            if (type == Attachment.class)
                result = (T)new Attachment(restclient, (JSONObject)r);
            else if (type == ChangeLog.class)
                result = (T)new ChangeLog(restclient, (JSONObject)r);
            else if (type == ChangeLogEntry.class)
                result = (T)new ChangeLogEntry(restclient, (JSONObject)r);
            else if (type == ChangeLogItem.class)
                result = (T)new ChangeLogItem(restclient, (JSONObject)r);
            else if (type == Comment.class)
                result = (T)new Comment(restclient, (JSONObject)r, parentId);
            else if (type == Component.class)
                result = (T)new Component(restclient, (JSONObject)r);
            else if (type == CustomFieldOption.class)
                result = (T)new CustomFieldOption(restclient, (JSONObject)r);
            else if (type == Issue.class)
                result = (T)new Issue(restclient, (JSONObject)r);
            else if (type == IssueLink.class)
                result = (T)new IssueLink(restclient, (JSONObject)r);
            else if (type == IssueType.class)
                result = (T)new IssueType(restclient, (JSONObject)r);
            else if (type == LinkType.class)
                result = (T)new LinkType(restclient, (JSONObject)r);
            else if (type == Priority.class)
                result = (T)new Priority(restclient, (JSONObject)r);
            else if (type == Project.class)
                result = (T)new Project(restclient, (JSONObject)r);
            else if (type == ProjectCategory.class)
                result = (T)new ProjectCategory(restclient, (JSONObject)r);
            else if (type == RemoteLink.class)
                result = (T)new RemoteLink(restclient, (JSONObject)r);
            else if (type == Resolution.class)
                result = (T)new Resolution(restclient, (JSONObject)r);
            else if (type == Status.class)
                result = (T)new Status(restclient, (JSONObject)r);
            else if (type == Transition.class)
                result = (T)new Transition(restclient, (JSONObject)r);
            else if (type == User.class)
                result = (T)new User(restclient, (JSONObject)r);
            else if (type == Visibility.class)
                result = (T)new Visibility(restclient, (JSONObject)r);
            else if (type == Version.class)
                result = (T)new Version(restclient, (JSONObject)r);
            else if (type == Votes.class)
                result = (T)new Votes(restclient, (JSONObject)r);
            else if (type == Watches.class)
                result = (T)new Watches(restclient, (JSONObject)r);
            else if (type == WorkLog.class)
                result = (T)new WorkLog(restclient, (JSONObject)r);
            else if (type == Security.class)
                result = (T)new Security(restclient, (JSONObject)r);
        }

        return result;
    }

    /**
     * Gets a string from the given object.
     *
     * @param s a String instance
     *
     * @return a String or null if s isn't a String instance
     */
    public static String getString(Object s) {
        String result = null;

        if (s instanceof String)
            result = (String)s;

        return result;
    }

    /**
     * Gets a list of strings from the given object.
     *
     * @param sa a JSONArray instance
     *
     * @return a list of strings found in sa
     */
    public static List<String> getStringArray(Object sa) {
        List<String> results = new ArrayList<String>();

        if (sa instanceof JSONArray) {
            for (Object s : (JSONArray)sa) {
                if (s instanceof String)
                    results.add((String)s);
            }
        }

        return results;
    }

    /**
     * Gets a list of JIRA resources from the given object.
     *
     * @param type Resource data type
     * @param ra a JSONArray instance
     * @param restclient REST client instance
     *
     * @return a list of Resources found in ra
     */
    public static <T extends Resource> List<T> getResourceArray(
        Class<T> type, Object ra, RestClient restclient) {

        return getResourceArray(type, ra, restclient, null);
    }

    /**
     * Gets a list of JIRA resources from the given object.
     *
     * @param type Resource data type
     * @param ra a JSONArray instance
     * @param restclient REST client instance
     * @param parentId id/key of the parent resource
     *
     * @return a list of Resources found in ra
     */
    public static <T extends Resource> List<T> getResourceArray(
        Class<T> type, Object ra, RestClient restclient, String parentId) {

        List<T> results = new ArrayList<T>();

        if (ra instanceof JSONArray) {
            for (Object v : (JSONArray)ra) {
                T item = null;

                if (parentId != null) {
                    item = getResource(type, v, restclient, parentId);
                } else {
                    item = getResource(type, v, restclient);
                }

                if (item != null)
                    results.add(item);
            }
        }

        return results;
    }

    /**
     * Gets a time tracking object from the given object.
     *
     * @param tt a JSONObject instance
     *
     * @return a TimeTracking instance or null if tt isn't a JSONObject instance
     */
    public static TimeTracking getTimeTracking(Object tt) {
        TimeTracking result = null;

        if (tt instanceof JSONObject && !((JSONObject)tt).isNullObject())
            result = new TimeTracking((JSONObject)tt);

        return result;
    }

    /**
     * Extracts field metadata from an editmeta JSON object.
     *
     * @param name Field name
     * @param editmeta Edit metadata JSON object
     *
     * @return a Meta instance with field metadata
     *
     * @throws JiraException when the field is missing or metadata is bad
     */
    public static Meta getFieldMetadata(String name, JSONObject editmeta)
        throws JiraException {

        if (editmeta.isNullObject() || !editmeta.containsKey(name))
            throw new JiraException("Field '" + name + "' does not exist or read-only");

        Map f = (Map)editmeta.get(name);
        Meta m = new Meta();

        m.required = Field.getBoolean(f.get("required"));
        m.name = Field.getString(f.get("name"));

        if (!f.containsKey("schema"))
            throw new JiraException("Field '" + name + "' is missing schema metadata");

        Map schema = (Map)f.get("schema");

        m.type = Field.getString(schema.get("type"));
        m.items = Field.getString(schema.get("items"));
        m.system = Field.getString(schema.get("system"));
        m.custom = Field.getString(schema.get("custom"));
        m.customId = Field.getInteger(schema.get("customId"));

        return m;
    }

    /**
     * Converts the given value to a date.
     *
     * @param value New field value
     *
     * @return a Date instance or null
     */
    public static Date toDate(Object value) {
        if (value instanceof Date || value == null)
            return (Date)value;

        String dateStr = value.toString();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        if (dateStr.length() > DATE_FORMAT.length()) {
            df = new SimpleDateFormat(DATETIME_FORMAT);
        }
        return df.parse(dateStr, new ParsePosition(0));
    }

    /**
     * Converts an iterable type to a JSON array.
     *
     * @param iter Iterable type containing field values
     * @param type Name of the item type
     * @param custom Name of the custom type
     *
     * @return a JSON-encoded array of items
     */
    public static JSONArray toArray(Iterable iter, String type, String custom) throws JiraException {
        JSONArray results = new JSONArray();

        if (type == null)
            throw new JiraException("Array field metadata is missing item type");

        for (Object val : iter) {
            Operation oper = null;
            Object realValue = null;
            Object realResult = null;

            if (val instanceof Operation) {
                oper = (Operation)val;
                realValue = oper.value;
            } else
                realValue = val;

            if (type.equals("component") || type.equals("group") ||
                type.equals("user") || type.equals("version")) {

                JSONObject itemMap = new JSONObject();

                if (realValue instanceof ValueTuple) {
                    ValueTuple tuple = (ValueTuple)realValue;
                    itemMap.put(tuple.type, tuple.value.toString());
                } else
                    itemMap.put(ValueType.NAME.toString(), realValue.toString());

                realResult = itemMap;
            } else if ( type.equals("option") ||
                    (
                    type.equals("string") && custom != null
                    && (custom.equals("com.atlassian.jira.plugin.system.customfieldtypes:multicheckboxes") ||
                    custom.equals("com.atlassian.jira.plugin.system.customfieldtypes:multiselect")))) {
                
                realResult = new JSONObject();
                ((JSONObject)realResult).put(ValueType.VALUE.toString(), realValue.toString());
            } else if (type.equals("string"))
                realResult = realValue.toString();

            if (oper != null) {
                JSONObject operMap = new JSONObject();
                operMap.put(oper.name, realResult);
                results.add(operMap);
            } else
                results.add(realResult);
        }

        return results;
    }

    /**
     * Converts the given value to a JSON object.
     *
     * @param name Field name
     * @param value New field value
     * @param editmeta Edit metadata JSON object
     *
     * @return a JSON-encoded field value
     *
     * @throws JiraException when a value is bad or field has invalid metadata
     * @throws UnsupportedOperationException when a field type isn't supported
     */
    public static Object toJson(String name, Object value, JSONObject editmeta)
        throws JiraException, UnsupportedOperationException {

        Meta m = getFieldMetadata(name, editmeta);
        if (m.type == null)
            throw new JiraException("Field '" + name + "' is missing metadata type");

        if (m.type.equals("array")) {
            if (value == null)
                value = new ArrayList();
            else if (!(value instanceof Iterable))
                throw new JiraException("Field '" + name + "' expects an Iterable value");

            return toArray((Iterable)value, m.items, m.custom);
        } else if (m.type.equals("date")) {
            if (value == null)
                return JSONNull.getInstance();

            Date d = toDate(value);
            if (d == null)
                throw new JiraException("Field '" + name + "' expects a date value or format is invalid");

            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            return df.format(d);
        } else if (m.type.equals("datetime")) {
            if (value == null)
                return JSONNull.getInstance();
            else if (!(value instanceof Timestamp))
                throw new JiraException("Field '" + name + "' expects a Timestamp value");

            SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
            return df.format(value);
        } else if (m.type.equals("issuetype") || m.type.equals("priority") ||
                m.type.equals("user") || m.type.equals("resolution") || m.type.equals("securitylevel")) {
            JSONObject json = new JSONObject();

            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof ValueTuple) {
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
            } else
                json.put(ValueType.NAME.toString(), value.toString());

            return json.toString();
        } else if (m.type.equals("project") || m.type.equals("issuelink")) {
            JSONObject json = new JSONObject();

            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof ValueTuple) {
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
            } else
                json.put(ValueType.KEY.toString(), value.toString());

            return json.toString();
        } else if (m.type.equals("string") || (m.type.equals("securitylevel") || m.type.equals("option"))) {
            if (value == null)
                return "";
            else if (value instanceof List)
                return toJsonMap((List)value);
            else if (value instanceof ValueTuple) {
                JSONObject json = new JSONObject();
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
                return json.toString();
            }

            return value.toString();
        } else if (m.type.equals("timetracking")) {
            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof TimeTracking)
                return ((TimeTracking) value).toJsonObject();
        } else if (m.type.equals("number")) {
            if (value == null) //Non mandatory number fields can be set to null
                return JSONNull.getInstance(); 
            else if(!(value instanceof java.lang.Integer) && !(value instanceof java.lang.Double) && !(value 
                    instanceof java.lang.Float) && !(value instanceof java.lang.Long) )
            {
                throw new JiraException("Field '" + name + "' expects a Numeric value");
            }
            return value;
        } else if (m.type.equals("any")) {
            if (value == null)
                return JSONNull.getInstance();
            else if (value instanceof List)
                return toJsonMap((List)value);
            else if (value instanceof ValueTuple) {
                JSONObject json = new JSONObject();
                ValueTuple tuple = (ValueTuple)value;
                json.put(tuple.type, tuple.value.toString());
                return json.toString();
            } else if (value instanceof TimeTracking)
                return ((TimeTracking) value).toJsonObject();

            return value;
        }

        throw new UnsupportedOperationException(m.type + " is not a supported field type");
    }

    /**
     * Converts the given map to a JSON object.
     *
     * @param list List of values to be converted
     *
     * @return a JSON-encoded map
     */
    public static Object toJsonMap(List list) {
        JSONObject json = new JSONObject();

        for (Object item : list) {
            if (item instanceof ValueTuple) {
                ValueTuple vt = (ValueTuple)item;
                json.put(vt.type, vt.value.toString());
            } else
                json.put(ValueType.VALUE.toString(), item.toString());
        }

        return json.toString();
    }

    /**
     * Create a value tuple with value type of key.
     *
     * @param key The key value
     *
     * @return a value tuple
     */
    public static ValueTuple valueByKey(String key) {
        return new ValueTuple(ValueType.KEY, key);
    }

    /**
     * Create a value tuple with value type of name.
     *
     * @param name The name value
     *
     * @return a value tuple
     */
    public static ValueTuple valueByName(String name) {
        return new ValueTuple(ValueType.NAME, name);
    }

    /**
     * Create a value tuple with value type of ID number.
     *
     * @param id The ID number value
     *
     * @return a value tuple
     */
    public static ValueTuple valueById(String id) {
        return new ValueTuple(ValueType.ID_NUMBER, id);
    }
}

