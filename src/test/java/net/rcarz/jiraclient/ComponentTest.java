package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

public class ComponentTest {

    private Component component;

    @Before
    public void setup() {
        component = new Component(null, getComponentJson());
    }

    @Test
    public void getName() {
        assertEquals("Component 1", component.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("This is a JIRA component", component.getDescription());
    }

    @Test
    public void getLead() {
        assertEquals("fred", component.getLead().getName());
    }

    @Test
    public void updateComponent() throws Exception {
        RestClient restClient = PowerMockito.mock(RestClient.class);
        component = new Component(restClient, getComponentJson());
        ArgumentCaptor<JSONObject> payloadSpy = ArgumentCaptor.forClass(JSONObject.class);
        PowerMockito.when(restClient.put(anyString(), payloadSpy.capture())).thenReturn(getComponentJson());
        component.update()
                .name("New Component 1")
                .description("New Description")
                .leadUserName("marcello")
                .execute();
        assertEquals("New Component 1", payloadSpy.getValue().get("name"));
        assertEquals("New Description", payloadSpy.getValue().get("description"));
        assertEquals("marcello", payloadSpy.getValue().get("leadUserName"));
    }

    private JSONObject getComponentJson() {
        return (JSONObject) JSONSerializer.toJSON("{\n" +
                "    \"self\": \"http://www.example.com/jira/rest/api/2/component/10000\",\n" +
                "    \"id\": \"10000\",\n" +
                "    \"name\": \"Component 1\",\n" +
                "    \"description\": \"This is a JIRA component\",\n" +
                "    \"lead\": {\n" +
                "        \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\",\n" +
                "        \"name\": \"fred\",\n" +
                "        \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\",\n" +
                "            \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\",\n" +
                "            \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\",\n" +
                "            \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"\n" +
                "        },\n" +
                "        \"displayName\": \"Fred F. User\",\n" +
                "        \"active\": false\n" +
                "    },\n" +
                "    \"assigneeType\": \"PROJECT_LEAD\",\n" +
                "    \"assignee\": {\n" +
                "        \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\",\n" +
                "        \"name\": \"fred\",\n" +
                "        \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\",\n" +
                "            \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\",\n" +
                "            \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\",\n" +
                "            \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"\n" +
                "        },\n" +
                "        \"displayName\": \"Fred F. User\",\n" +
                "        \"active\": false\n" +
                "    },\n" +
                "    \"realAssigneeType\": \"PROJECT_LEAD\",\n" +
                "    \"realAssignee\": {\n" +
                "        \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\",\n" +
                "        \"name\": \"fred\",\n" +
                "        \"avatarUrls\": {\n" +
                "            \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\",\n" +
                "            \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\",\n" +
                "            \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\",\n" +
                "            \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"\n" +
                "        },\n" +
                "        \"displayName\": \"Fred F. User\",\n" +
                "        \"active\": false\n" +
                "    },\n" +
                "    \"isAssigneeTypeValid\": false,\n" +
                "    \"project\": \"HSP\",\n" +
                "    \"projectId\": 10000\n" +
                "}");
    }
}