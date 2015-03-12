package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


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
