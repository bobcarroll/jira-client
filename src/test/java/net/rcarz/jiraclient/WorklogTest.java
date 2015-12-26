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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
public class WorklogTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

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

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        final Date parse = df.parse(dateString, new ParsePosition(0));

        WorkLog workLog = new WorkLog(mockRestClient,mockJSONObject);
        assertEquals(parse.toString() + " by Joseph McCarthy",workLog.toString());
    }

    @Test
    public void testWorklog() {

        List<WorkLog> workLogs = Field.getResourceArray(WorkLog.class, Utils.getTestIssueWorklogs().get("worklogs"), null);
        assertEquals(2, workLogs.size());

        WorkLog workLog = workLogs.get(0);
        assertEquals("comment for worklog 1", workLog.getComment());
        assertEquals("6h", workLog.getTimeSpent());
        assertEquals("45517", workLog.getId());
        String author = "joseph";
        assertEquals(author, workLog.getAuthor().getName());
        String started = "2015-08-17T00:00:00.000";
        assertEquals(started, simpleDateFormat.format(workLog.getStarted()));
        String created = "2015-08-20T00:00:00.000";
        assertEquals(created, simpleDateFormat.format(workLog.getCreatedDate()));
        assertEquals(21600, workLog.getTimeSpentSeconds());
        assertEquals(author, workLog.getUpdateAuthor().getName());
        assertEquals(created, simpleDateFormat.format(workLog.getUpdatedDate()));

    }

}
