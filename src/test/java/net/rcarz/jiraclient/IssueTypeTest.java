package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;


public class IssueTypeTest {

    @Test
    public void testIssueTypeInit() {
        IssueType issueType = new IssueType(null, null);
    }

    @Test
    public void testGetIssueType() {
        IssueType issueType = new IssueType(null, getTestJSON());

        assertFalse(issueType.isSubtask());
        assertEquals(issueType.getName(), "Story");
        assertEquals(issueType.getId(), "7");
        assertEquals(issueType.getIconUrl(), "https://brainbubble.atlassian.net/images/icons/issuetypes/story.png");
        assertEquals(issueType.getDescription(), "This is a test issue type.");
    }

    @Test
    public void testFields() throws Exception {
        final JSONObject testJSON = getTestJSON();
        final JSONObject fields = new JSONObject();
        fields.put("key1","key1Value");
        fields.put("key2","key2Value");
        testJSON.put("fields", fields);
        IssueType issueType = new IssueType(null, testJSON);
        assertEquals(2,issueType.getFields().size());
        assertSame("key1Value",issueType.getFields().getString("key1"));
        assertSame("key2Value",issueType.getFields().getString("key2"));

    }

    @Test
    public void testLoadIssueType() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        when(restClient.get(anyString())).thenReturn(getTestJSON());
        IssueType issueType = IssueType.get(restClient,"someID");
        assertFalse(issueType.isSubtask());
        assertEquals(issueType.getName(), "Story");
        assertEquals(issueType.getId(), "7");
        assertEquals(issueType.getIconUrl(), "https://brainbubble.atlassian.net/images/icons/issuetypes/story.png");
        assertEquals(issueType.getDescription(), "This is a test issue type.");
    }


    @Test(expected = JiraException.class)
    public void testJiraExceptionFromRestException() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        when(mockRestClient.get(anyString())).thenThrow(RestException.class);
        IssueType.get(mockRestClient, "issueNumber");
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        IssueType.get(mockRestClient,"issueNumber");
    }

    @Test
    public void testIssueTypeToString(){
        IssueType issueType = new IssueType(null, getTestJSON());

        assertEquals(issueType.toString(),"Story");
    }

    private JSONObject getTestJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("self", "https://brainbubble.atlassian.net/rest/api/2/issuetype/7");
        jsonObject.put("id", "7");
        jsonObject.put("description", "This is a test issue type.");
        jsonObject.put("iconUrl", "https://brainbubble.atlassian.net/images/icons/issuetypes/story.png");
        jsonObject.put("name", "Story");
        jsonObject.put("subtask", false);

        return jsonObject;
    }
}
