package net.rcarz.jiraclient;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by dgigon on 14/09/16.
 */
public class Visibility extends Resource {
    private String type;
    private String value;

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    protected Visibility(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        type = Field.getString(map.get("type"));
        value = Field.getString(map.get("value"));
    }
}
