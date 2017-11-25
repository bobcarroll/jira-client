package net.rcarz.jiraclient.agile;

import java.util.ArrayList;
import java.util.List;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BoardColumn extends AgileResource {

	private List<Status> statuses;
	
	public BoardColumn(RestClient restclient, JSONObject json) throws JiraException {
		super(restclient, json);
	}

    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    @Override
    void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);
        setName(json.getString("name"));
        JSONArray statusesJSON = json.getJSONArray("statuses");
        statuses = new ArrayList<>(statusesJSON.size());
        for (int i = 0; i < statusesJSON.size(); i++) {
			JSONObject status = statusesJSON.getJSONObject(i);
			Status stat = new Status(getRestclient(),status);
			statuses.add(stat);
		}
    }

    public List<Status> getStatuses() {
		return statuses;
	}

	@Override
    public String toString() {
        return String.format("%s{name=%s, statuses='%s'}", getClass().getSimpleName(), getId(), statuses.toString());
    }
    
}
