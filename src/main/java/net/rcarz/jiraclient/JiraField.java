package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents Jira Field (both system and custom)
 *
 * TODO: add "schema" field
 */
public class JiraField {

    private String id = null;
    private String key = null;
    private String name = null;
    private Boolean custom = null;
    private Boolean orderable = null;
    private Boolean navigable = null;
    private Boolean searchable = null;
    private List<String> clauseNames = null;

    public JiraField(JSONObject json) {
        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getString(map.get("id"));
        key = Field.getString(map.get("key"));
        name = Field.getString(map.get("name"));
        custom = Field.getBoolean(map.get("custom"));
        orderable = Field.getBoolean(map.get("orderable"));
        navigable = Field.getBoolean(map.get("navigable"));
        searchable = Field.getBoolean(map.get("searchable"));
        clauseNames = Field.getStringArray(map.get("clauseNames"));
    }

    /**
     * Returns a list of all fields.
     *
     * @param restclient REST client instance
     *
     * @return a list of fields
     *
     * @throws JiraException when the retrieval fails
     */
    public static List<JiraField> get(RestClient restclient) throws JiraException {
        JSON result = null;

        try {
            URI uri = restclient.buildURI(String.format("/rest/api/%s/", Resource.DEFAULT_API_REV) + "field/");
            result = restclient.get(uri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve fields list", ex);
        }

        if (!(result instanceof JSONArray))
            throw new JiraException("JSON payload is malformed");

        List<JiraField> results = new ArrayList<JiraField>();

        for (Object v : (JSONArray)result) {
            JiraField item = null;

            if (v instanceof JSONObject && !((JSONObject)v).isNullObject()) {
                item = new JiraField((JSONObject) v);
            }

            results.add(item);
        }

        return results;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Boolean getCustom() {
        return custom;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public Boolean getNavigable() {
        return navigable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public List<String> getClauseNames() {
        return clauseNames;
    }
}
