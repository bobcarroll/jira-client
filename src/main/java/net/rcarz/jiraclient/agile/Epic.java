package net.rcarz.jiraclient.agile;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Created on 2016-05-20.
 * @author pldupont
 */
public class Epic extends AgileResource {

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Epic(RestClient restclient, JSONObject json) {
        super(restclient, json);
    }

    /**
     * Retrieves the epic matching the ID.
     *
     * @param restclient REST client instance
     * @param id         Internal JIRA ID of the epic
     * @return an epic instance
     * @throws JiraException when the retrieval fails
     */
    public static Epic get(RestClient restclient, long id) throws JiraException {
        return AgileResource.get(restclient, Epic.class, RESOURCE_URI + "epic/" + id);
    }
}
