package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * General information about JIRA server.
 */
public class ServerInfo {

    private String baseUrl = null;
    private String version = null;
    private List<Integer> versionNumbers = null;
    private String deploymentType = null;
    private Integer buildNumber = null;
    private Date buildDate = null;
    private Date serverTime = null;
    private String scmInfo = null;
    private String buildPartnerName = null;
    private String serverTitle = null;

    /**
     * Creates ServerInfo from a JSON payload.
     *
     * @param json JSON payload
     */
    public ServerInfo(JSONObject json) {
        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        baseUrl = Field.getString(map.get("baseUrl"));
        version = Field.getString(map.get("version"));
        versionNumbers = Field.getIntegerArray(map.get("versionNumbers"));
        deploymentType = Field.getString(map.get("deploymentType"));
        buildNumber = Field.getInteger(map.get("buildNumber"));
        buildDate = Field.getDateTime(map.get("buildDate"));
        serverTime = Field.getDateTime(map.get("serverTime"));
        scmInfo = Field.getString(map.get("scmInfo"));
        buildPartnerName = Field.getString(map.get("buildPartnerName"));
        serverTitle = Field.getString(map.get("serverTitle"));
    }

    public static ServerInfo get(RestClient restclient) throws JiraException {
        return new ServerInfo(realGet(restclient));
    }

    private static JSONObject realGet(RestClient restclient) throws JiraException {
        JSON result = null;

        try {
            URI uri = restclient.buildURI(String.format("/rest/api/%s/", Resource.DEFAULT_API_REV) + "serverInfo/");
            result = restclient.get(uri);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve Server Info ", ex);
        }

        if (!(result instanceof JSONObject)) {
            throw new JiraException("JSON payload is malformed");
        }

        return (JSONObject) result;
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

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public String getScmInfo() {
        return scmInfo;
    }

    public String getBuildPartnerName() {
        return buildPartnerName;
    }

    public String getServerTitle() {
        return serverTitle;
    }
}
