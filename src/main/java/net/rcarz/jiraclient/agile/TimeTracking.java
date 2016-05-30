package net.rcarz.jiraclient.agile;

import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Created by pldupont on 2016-05-20.
 */
public class TimeTracking extends AgileResource {

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public TimeTracking(RestClient restclient, JSONObject json) {
        super(restclient, json);
    }
}
