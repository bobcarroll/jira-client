package net.rcarz.jiraclient;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class WorklogTest {

    private String author = "joseph";
    private String started = "2015-08-17T00:00:00.000";
    private String created = "2015-08-20T00:00:00.000";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Test
    public void testWorklog() {

        List<WorkLog> workLogs = Field.getResourceArray(WorkLog.class, Utils.getTestIssueWorklogs().get("worklogs"), null);
        assertEquals(2, workLogs.size());

        WorkLog workLog = workLogs.get(0);
        assertEquals("comment for worklog 1", workLog.getComment());
        assertEquals("6h", workLog.getTimeSpent());
        assertEquals("45517", workLog.getId());
        assertEquals(author, workLog.getAuthor().getName());
        assertEquals(started, simpleDateFormat.format(workLog.getStarted()));
        assertEquals(created, simpleDateFormat.format(workLog.getCreatedDate()));
        assertEquals(21600, workLog.getTimeSpentSeconds());
        assertEquals(author, workLog.getUpdateAuthor().getName());
        assertEquals(created, simpleDateFormat.format(workLog.getUpdatedDate()));

    }

}
