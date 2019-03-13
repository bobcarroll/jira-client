/*
 * Copyright (c) 2012-2019 Continuum Security SLNE.  All rights reserved
 */
package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Represents the server info.
 */
public class ServerInfo extends Resource {

    private String baseUrl;
    private String version;
    private List<Integer> versionNumbers;
    private String deploymentType;
    private int buildNumber;
    private String buildDate;
    private String serverTime;
    private String scmInfo;
    private String serverTitle;

    /**
     * Creates a server info from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    public ServerInfo(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    public void deserialise(JSONObject json) {

        Map map = json;

        baseUrl = Field.getString(map.get("baseUrl"));
        version = Field.getString(map.get("version"));
        versionNumbers = Field.getIntegerArray(map.get("versionNumbers"));
        deploymentType = Field.getString(map.get("deploymentType"));
        buildNumber = Field.getInteger(map.get("buildNumber"));
        buildDate = Field.getString(map.get("buildDate"));
        serverTime = Field.getString(map.get("serverTime"));
        scmInfo = Field.getString(map.get("scmInfo"));
        serverTitle = Field.getString(map.get("serverTitle"));
    }

    /**
     * Retrieves the server info.
     *
     * @param restclient REST client instance
     *
     * @return a server info instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static ServerInfo get(RestClient restclient)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "serverInfo");
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve ServerInfo", ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new ServerInfo(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return getServerTitle();
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public String getVersion() {
        return version;
    }

    public List<Integer> getVersionNumbers() {
        return versionNumbers;
    }

    public String getDeploymentType() {
        return deploymentType;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getServerTime() {
        return serverTime;
    }

    public String getScmInfo() {
        return scmInfo;
    }

    public String getServerTitle() {
        return serverTitle;
    }
}
