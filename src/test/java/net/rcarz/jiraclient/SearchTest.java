package net.rcarz.jiraclient;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchTest {

  @Test
  public void testSimpleSearch() throws JiraException {
    JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);

    String key = "JRA-1";
    Issue.SearchResult searchResult = jira.searchIssues("key = " + key);

    assertNotNull(searchResult);
    assertEquals("should return exactly 1 issue", 1, searchResult.issues.size());
    assertEquals("with key " + key, key, searchResult.issues.get(0).getKey());
    assertEquals("and resolution Fixed", "Fixed", searchResult.issues.get(0).getResolution().getName());
  }

  @Test
  public void testExpandingChangeLogInSearch() throws JiraException {
    JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);

    String key = "JRA-2048";
    Issue.SearchResult searchResult = jira.searchIssues("key = " + key, null, "changelog");

    assertEquals("should return exactly 1 issue", 1, searchResult.issues.size());

    ChangeLog changeLog = searchResult.issues.get(0).getChangeLog();
    assertNotNull("returned issue should have a changeLog", changeLog);

    boolean closedStatusFound = false;
    for (ChangeLogEntry entry : changeLog.getEntries()) {
      for (ChangeLogItem item : entry.getItems()) {
        closedStatusFound |= "Closed".equals(item.getToString());
      }
    }

    assertTrue("ChangeLog should contain Closed entry", closedStatusFound);
  }
}
