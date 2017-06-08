package net.rcarz.jiraclient;

import lombok.Getter;
import net.sf.json.JSONObject;

import java.util.Map;

@Getter
class IssueHistoryItem extends Resource {

    private String field;
    private String from;
    private String to;
    private String fromStr;
    private String toStr;

    IssueHistoryItem(RestClient restclient) {
        super(restclient);
    }

    IssueHistoryItem(RestClient restclient, JSONObject json) {
        this(restclient);
        if (json != null) {
            deserialise(restclient,json);
        }
    }

    private void deserialise(RestClient restclient, JSONObject json) {
        Map map = json;
        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        field = Field.getString(map.get("field"));
        from = Field.getString(map.get("from"));
        to = Field.getString(map.get("to"));
        fromStr = Field.getString(map.get("fromString"));
        toStr = Field.getString(map.get("toString"));
    }
}