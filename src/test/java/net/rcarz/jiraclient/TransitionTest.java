package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TransitionTest {

    @Test
    public void TransitionInit(){
        Transition transition = new Transition(null, getTestJson());
    }

    @Test
    public void testDeserializeJSON(){
        Transition transition = new Transition(null, getTestJson());
        assertEquals("21", transition.getId());
        assertEquals("Done", transition.getName());
    }

    @Test
    public void testToStatus() {
        Transition transition = new Transition(null, getTestJson());
        Status toStatus = transition.getToStatus();
        assertEquals("Done", toStatus.getName());
        assertEquals("A description of great importance.", toStatus.getDescription());
    }

    @Test
    public void testTransitionToString() throws URISyntaxException {
        Transition transition = new Transition(new RestClient(null, new URI("/123/asd")), getTestJson());
        assertEquals("Done", transition.toString());
    }

    @Test
    public void testGetFields() throws Exception {
        Transition transition = new Transition(new RestClient(null, new URI("/123/asd")), getTestJson());
        final Map fields = transition.getFields();
        Assert.assertEquals(2,fields.size());

    }

    public static JSONObject getTestJson() {
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(
                "{\n" +
                        "  \"id\": \"21\",\n" +
                        "  \"name\": \"Done\",\n" +
                        "  \"to\": {\n" +
                        "    \"self\": \"https://jira-client.atlassian.net/rest/api/2/status/10001\",\n" +
                        "    \"description\": \"A description of great importance.\",\n" +
                        "    \"iconUrl\": \"https://jira-client.atlassian.net/images/icons/statuses/closed.png\",\n" +
                        "    \"name\": \"Done\",\n" +
                        "    \"id\": \"10001\",\n" +
                        "    \"statusCategory\": {\n" +
                        "      \"self\": \"https://jira-client.atlassian.net/rest/api/2/statuscategory/3\",\n" +
                        "      \"id\": 3,\n" +
                        "      \"key\": \"done\",\n" +
                        "      \"colorName\": \"green\",\n" +
                        "      \"name\": \"Done\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"fields\": {\n" +
                        "    \"issuelinks\": {\n" +
                        "      \"required\": false,\n" +
                        "      \"schema\": {\n" +
                        "        \"type\": \"array\",\n" +
                        "        \"items\": \"issuelinks\",\n" +
                        "        \"system\": \"issuelinks\"\n" +
                        "      },\n" +
                        "      \"name\": \"Linked Issues\",\n" +
                        "      \"autoCompleteUrl\": \"https://jira-client.atlassian.net/rest/api/2/issue/picker?currentProjectId\\u003d\\u0026showSubTaskParent\\u003dtrue\\u0026showSubTasks\\u003dtrue\\u0026currentIssueKey\\u003dJIR-1\\u0026query\\u003d\",\n" +
                        "      \"operations\": [\n" +
                        "        \"add\"\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"resolution\": {\n" +
                        "      \"required\": true,\n" +
                        "      \"schema\": {\n" +
                        "        \"type\": \"resolution\",\n" +
                        "        \"system\": \"resolution\"\n" +
                        "      },\n" +
                        "      \"name\": \"Resolution\",\n" +
                        "      \"operations\": [\n" +
                        "        \"set\"\n" +
                        "      ],\n" +
                        "      \"allowedValues\": [\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/1\",\n" +
                        "          \"name\": \"Fixed\",\n" +
                        "          \"id\": \"1\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/2\",\n" +
                        "          \"name\": \"Won\\u0027t Fix\",\n" +
                        "          \"id\": \"2\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/3\",\n" +
                        "          \"name\": \"Duplicate\",\n" +
                        "          \"id\": \"3\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/4\",\n" +
                        "          \"name\": \"Incomplete\",\n" +
                        "          \"id\": \"4\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/5\",\n" +
                        "          \"name\": \"Cannot Reproduce\",\n" +
                        "          \"id\": \"5\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/10000\",\n" +
                        "          \"name\": \"Done\",\n" +
                        "          \"id\": \"10000\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"self\": \"https://jira-client.atlassian.net/rest/api/latest/resolution/10001\",\n" +
                        "          \"name\": \"Won\\u0027t Do\",\n" +
                        "          \"id\": \"10001\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");

        return  jsonObject;
    }

}
