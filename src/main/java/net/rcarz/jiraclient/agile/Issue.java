package net.rcarz.jiraclient.agile;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by pldupont on 2016-05-20.
 */
public class Issue extends AgileResource {

    /**
     * Creates a new Agile Issue resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Issue(RestClient restclient, JSONObject json) {
        super(restclient, json);
    }

    /**
     * Retrieves the issue matching the ID.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the issue
     * @return an issue instance
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, int id) throws JiraException {
        return AgileResource.get(restclient, Issue.class, RESOURCE_URI + "issue/" + id);
    }

    /**
     * Retrieves the issue matching the ID.
     *
     * @param restclient REST client instance
     * @param key        JIRA key of the issue
     * @return an issue instance
     * @throws JiraException when the retrieval fails
     */
    public static Issue get(RestClient restclient, String key) throws JiraException {
        return AgileResource.get(restclient, Issue.class, RESOURCE_URI + "issue/" + key);
    }
}
