package net.rcarz.jiraclient;

import lombok.Getter;
import net.sf.json.JSONObject;

import java.util.Map;

@Getter
class RemoteLink extends Resource {
    private String remoteUrl;
    private String title;

    public RemoteLink(RestClient restclient, JSONObject json) {
        super(restclient);
        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        
        Map object = (Map)map.get("object");
        
        remoteUrl = Field.getString(object.get("url"));
        title = Field.getString(object.get("title"));
    }
}
