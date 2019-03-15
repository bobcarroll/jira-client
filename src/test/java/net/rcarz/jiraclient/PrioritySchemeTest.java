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

public class PrioritySchemeTest {

    @Test
    public void testCreatePriorityScheme() {
        new PriorityScheme(null, Utils.getTestPriorityScheme());
    }

    @Test
    public void prioritySchemeDataTest()
    {
        String self = "http://www.example.com/jira/rest/api/2/priorityschemes/1000";
        String id = "10000";
        List<String> prioritiesIds = Arrays.asList("1", "2", "3", "4", "5");
        String name = "default priority scheme";
        String description = "This is default priority scheme used by all projects without any other scheme assigned.";
        boolean defaultScheme = true;

        PriorityScheme priorityScheme = new PriorityScheme(null, Utils.getTestPriorityScheme());

        assertEquals(self, priorityScheme.getSelf());
        assertEquals(id, priorityScheme.getId());
        assertEquals(name, priorityScheme.getName());
        assertEquals(description, priorityScheme.getDescription());
        assertEquals(defaultScheme, priorityScheme.getDefaultScheme());
        assertEquals(prioritiesIds, priorityScheme.getPrioritiesIds());
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        PriorityScheme.get(mockRestClient,"prioritySchemeID");
    }

    @Test(expected = JiraException.class)
    public void testGetPrioritiesJiraExceptionFromNonJSON() throws Exception {
        PriorityScheme priorityScheme = new PriorityScheme(null, Utils.getTestPriorityScheme());
        priorityScheme.restclient = PowerMockito.mock(RestClient.class);

        priorityScheme.getPriorities();
    }

    @Test
    public void testPrioritySchemeToString() throws URISyntaxException {
        PriorityScheme priorityScheme = new PriorityScheme(new RestClient(null, new URI("/123/asd")), Utils.getTestPriorityScheme());
        assertEquals("default priority scheme",priorityScheme.toString());
    }
}
