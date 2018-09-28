package net.rcarz.jiraclient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Represens a Jira filter.
 */
public class Filter extends Resource {

	private String name;
	private String jql;
	private boolean favourite;

	public Filter(RestClient restclient, JSONObject json) {
		super(restclient);

		if (json != null) {
      deserialise(json);
    }
	}

	private void deserialise(JSONObject json) {
		Map map = json;

		id = Field.getString(map.get("id"));
		self = Field.getString(map.get("self"));
		name = Field.getString(map.get("name"));
		jql = Field.getString(map.get("jql"));
		favourite = Field.getBoolean(map.get("favourite"));
	}

	public boolean isFavourite() {
		return favourite;
	}

	public String getJql() {
		return jql;
	}

	public String getName() {
		return name;
	}

	public static List<Filter> getAll(final RestClient restclient) throws JiraException {
	  JSON result = null;

	  try {
	    URI uri = restclient.buildURI(getBaseUri() + "filter/");
	    result = restclient.get(uri);
	  } catch (Exception ex) {
	    throw new JiraException("Failed to retrieve filters", ex);
	  }

	  if (!(result instanceof JSONArray)) {
	    throw new JiraException("JSON payload is malformed");
	  }
	  List<Filter> filters = new ArrayList<Filter>();
	  for (int i = 0; i < ((JSONArray)result).size(); i++) {
	    if (!(((JSONArray)result).get(i) instanceof JSONObject)) {
	      throw new JiraException("JSON payload is malformed");
	    }
	    filters.add(new Filter(restclient, (JSONObject) ((JSONArray)result).get(i)));
	  }
	  return filters;
	}

	public static Filter get(final RestClient restclient, final String id) throws JiraException {
		JSON result = null;

		try {
			URI uri = restclient.buildURI(getBaseUri() + "filter/" + id);
			result = restclient.get(uri);
		} catch (Exception ex) {
			throw new JiraException("Failed to retrieve filter with id " + id, ex);
		}

		if (!(result instanceof JSONObject)) {
			throw new JiraException("JSON payload is malformed");
		}

		return new Filter(restclient, (JSONObject) result);
	}

	@Override
	public String toString() {
		return "Filter{" +
				"favourite=" + favourite +
				", name='" + name + '\'' +
				", jql='" + jql + '\'' +
				'}';
	}


}
