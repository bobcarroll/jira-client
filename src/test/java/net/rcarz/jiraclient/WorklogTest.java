package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
public class WorklogTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Field.DATETIME_FORMAT);

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromRestException() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(mockRestClient.get(anyString())).thenThrow(RestException.class);
        WorkLog.get(mockRestClient, "issueNumber", "someID");
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        WorkLog.get(mockRestClient,"issueNumber","someID");
    }

    @Test
    public void testToString() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject mockJSONObject = new JSONObject();
        String dateString = "2015-12-24";

        mockJSONObject.put("created",dateString);
        final JSONObject userJSON = new JSONObject();
        userJSON.put("name","Joseph McCarthy");
        mockJSONObject.put("author", userJSON);

        WorkLog workLog = new WorkLog(mockRestClient,mockJSONObject);
        assertEquals(workLog.getCreatedDate() + " by Joseph McCarthy",workLog.toString());
    }

    @Test
    public void testWorklog() throws Exception {

        List<WorkLog> workLogs = Field.getResourceArray(WorkLog.class, Utils.getTestIssueWorklogs().get("worklogs"), null);
        assertEquals(2, workLogs.size());

        WorkLog workLog = workLogs.get(0);
        assertEquals("comment for worklog 1", workLog.getComment());
        assertEquals("6h", workLog.getTimeSpent());
        assertEquals("45517", workLog.getId());
        String author = "joseph";
        assertEquals(author, workLog.getAuthor().getName());
        String started = "2015-08-17T13:19:00.000+0400";
        assertEquals(simpleDateFormat.parse(started), workLog.getStarted());
        String created = "2015-08-20T13:19:44.000+0400";
        assertEquals(simpleDateFormat.parse(created), workLog.getCreatedDate());
        assertEquals(21600, workLog.getTimeSpentSeconds());
        assertEquals(author, workLog.getUpdateAuthor().getName());
    }

}
