package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by mariusmerkevicius on 1/30/16.
 */
public class IssueWorklogTest {

    @Test
    public void testParsing_inputValid_shouldCreateJsonObject() throws Exception {
        // Arrange
        // Act
        JSONObject worklogObject = (JSONObject) JSONSerializer.toJSON(RESPONSE_WORKLOG_BODY);

        // Assert
        assertNotNull(worklogObject);
    }

    @Test
    public void testParsing_inputValidJson_shouldCreateWorklog() throws Exception {
        // Arrange
        // Act
        WorkLog workLog = new WorkLog(mock(RestClient.class), (JSONObject) JSONSerializer.toJSON(RESPONSE_WORKLOG_BODY));

        // Assert
        assertNotNull(workLog);
        assertNotNull(workLog.getAuthor());
        assertEquals("https://jira.test.lt/rest/api/2/issue/32374/worklog/80720", workLog.getSelf());
        assertEquals("80720", workLog.getId());
        assertEquals("Test", workLog.getComment());
        assertEquals(1454179576583L, workLog.getCreatedDate().getTime());
        assertEquals(1454179576583L, workLog.getUpdatedDate().getTime());
        assertEquals(1453879853201L, workLog.getStarted().getTime());
        assertEquals("5m", workLog.getTimeSpent());
        assertEquals(300, workLog.getTimeSpentSeconds());
    }

    @Test
    public void testAdding_inputValid_shouldInvokeAdding() throws Exception {
        // Arrange
        Issue issue = mock(Issue.class);
        issue.restclient = mock(RestClient.class);
        doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

        // Act
        issue.addWorkLog("test", DateTime.now(), 60);

        // Assert
        verify(issue.restclient).post(anyString(), any(JSON.class));
    }

    @Test(expected = JiraException.class)
    public void testAdding_inputNullComment_shouldNotAdd() throws Exception {
        // Arrange
        Issue issue = mock(Issue.class);
        issue.restclient = mock(RestClient.class);
        doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

        // Act
        // Assert
        issue.addWorkLog(null, DateTime.now(), 120);
    }

    @Test(expected = JiraException.class)
    public void testAdding_inputNullDateTime_shouldNotAdd() throws Exception {
        // Arrange
        Issue issue = mock(Issue.class);
        issue.restclient = mock(RestClient.class);
        doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

        // Act
        // Assert
        issue.addWorkLog("asdf", null, 120);
    }

    @Test(expected = JiraException.class)
    public void testAdding_inputDurationTooLow_shouldNotAdd() throws Exception {
        // Arrange
        Issue issue = mock(Issue.class);
        issue.restclient = mock(RestClient.class);
        doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

        // Act
        // Assert
        issue.addWorkLog("asdf", DateTime.now(), 30);
    }


    //region Constants

    // Mock response from jira

    public static final String RESPONSE_WORKLOG_BODY = "{\"self\":\"https://jira.test.lt/rest/api/2/issue/32374/worklog/80720\",\"author\":{\"self\":\"https://jira.test.lt/rest/api/2/user?username=test%40test.lt\",\"name\":\"test@test.lt\",\"key\":\"test@test.lt\",\"emailAddress\":\"test@test.lt\",\"avatarUrls\":{\"48x48\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=48\",\"24x24\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=24\",\"16x16\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=16\",\"32x32\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=32\"},\"displayName\":\"Marius Merkevicius\",\"active\":true,\"timeZone\":\"Europe/Vilnius\"},\"updateAuthor\":{\"self\":\"https://jira.test.lt/rest/api/2/user?username=test%40test.lt\",\"name\":\"test@test.lt\",\"key\":\"test@test.lt\",\"emailAddress\":\"test@test.lt\",\"avatarUrls\":{\"48x48\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=48\",\"24x24\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=24\",\"16x16\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=16\",\"32x32\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=32\"},\"displayName\":\"Marius Merkevicius\",\"active\":true,\"timeZone\":\"Europe/Vilnius\"},\"comment\":\"Test\",\"created\":\"2016-01-30T20:46:16.583+0200\",\"updated\":\"2016-01-30T20:46:16.583+0200\",\"started\":\"2016-01-27T09:30:53.201+0200\",\"timeSpent\":\"5m\",\"timeSpentSeconds\":300,\"id\":\"80720\"}";

    //endregion

}