/*
 * Copyright (c) 2012-2019 Continuum Security SLNE.  All rights reserved
 */
package net.rcarz.jiraclient;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ServerInfoTest {

    @Test
    public void testCreateServerInfo() {
        new ServerInfo(null, Utils.getTestServerInfo());
    }

    @Test
    public void prioritySchemeDataTest()
    {

        String baseUrl = "http://www.example.com/jira";
        String version = "8.0.0";
        List<Integer> versionNumbers = Arrays.asList(8, 0, 0);
        String deploymentType = "Server";
        int buildNumber = 80007;
        String buildDate = "2019-02-09T00:00:00.000+0000";
        String serverTime = "2019-03-13T12:24:49.769+0000";
        String scmInfo = "b1ea48b1170e88fff48488fb6d451a50948b5f19";
        String serverTitle = "Jira";

        ServerInfo serverInfo = new ServerInfo(null, Utils.getTestServerInfo());

        assertEquals(baseUrl, serverInfo.getBaseUrl());
        assertEquals(version, serverInfo.getVersion());
        assertEquals(versionNumbers, serverInfo.getVersionNumbers());
        assertEquals(deploymentType, serverInfo.getDeploymentType());
        assertEquals(buildNumber, serverInfo.getBuildNumber());
        assertEquals(buildDate, serverInfo.getBuildDate());
        assertEquals(serverTime, serverInfo.getServerTime());
        assertEquals(scmInfo, serverInfo.getScmInfo());
        assertEquals(serverTitle, serverInfo.getServerTitle());
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        ServerInfo.get(mockRestClient);
    }

    @Test
    public void testServerInfoToString() throws URISyntaxException {
        ServerInfo serverInfo = new ServerInfo(new RestClient(null, new URI("/serverInfo")), Utils.getTestServerInfo());
        assertEquals("Jira",serverInfo.toString());
    }

}
