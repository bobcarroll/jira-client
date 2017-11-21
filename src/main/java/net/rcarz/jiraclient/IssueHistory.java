package net.rcarz.jiraclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IssueHistory extends Resource {

    private static final long serialVersionUID = 1L;
    private User user;
    private ArrayList<IssueHistoryItem> changes;
    private Date created;

    /**
     * Creates an issue history record from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected IssueHistory(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null) {
            deserialise(restclient,json);
        }
    }

    public IssueHistory(IssueHistory record, ArrayList<IssueHistoryItem> changes) {
        super(record.restclient);
        user = record.user;
        id = record.id;
        self = record.self;
        created = record.created;
        this.changes = changes;
    }

    private void deserialise(RestClient restclient, JSONObject json) {
        Map map = json;
        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        user = new User(restclient,(JSONObject)map.get("author"));
        created = Field.getDateTime(map.get("created"));
        JSONArray items = JSONArray.fromObject(map.get("items"));
        changes = new ArrayList<IssueHistoryItem>(items.size());
        for (int i = 0; i < items.size(); i++) {
            JSONObject p = items.getJSONObject(i);
            changes.add(new IssueHistoryItem(restclient, p));
        }
    }

    public User getUser() {
        return user;
    }

    public ArrayList<IssueHistoryItem> getChanges() {
        return changes;
    }

    public Date getCreated() {
        return created;
    }

}
