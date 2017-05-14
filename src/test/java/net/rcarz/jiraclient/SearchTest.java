package net.rcarz.jiraclient;

import org.junit.Test;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SearchTest {

  @Test
  public void testSimpleSearch() throws JiraException {
    JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);

    String key = "JRASERVER-1";
    Issue.SearchResult searchResult = jira.searchIssues("key = " + key);

    assertNotNull(searchResult);
    assertEquals("should return exactly 1 issue", 1, searchResult.issues.size());
    assertEquals("with key " + key, key, searchResult.issues.get(0).getKey());
    assertEquals("and resolution Fixed", "Fixed", searchResult.issues.get(0).getResolution().getName());
  }

  @Test
  public void testEmptySearchGivesEmptyResult() throws JiraException {
    final JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);

    //Valid jql query that will always yield no results.
    final String q = "key = NotExisting-9999999 AND key = blah-8833772";
    Issue.SearchResult searchResult = jira.searchIssues(q);

    final String assertMsg = "Searches that yield no results, should return an empty "
            + Issue.SearchResult.class.getSimpleName() + " instance";
    assertTrue(assertMsg, searchResult.issues.isEmpty());
    assertFalse(assertMsg, searchResult.issues.iterator().hasNext());
    assertEquals(assertMsg, 0, searchResult.total);
    assertEquals(assertMsg, 0, searchResult.start);
  }

  @Test
  public void testSearchResultIteratorWithinMaxResultLimit() throws JiraException {
    final JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);
    final int usedMax = 2;
    //Will return everything from the public Jira for Jira
    final Issue.SearchResult searchResult = jira.searchIssues("", usedMax);
    final List<Issue> iterResults = new ArrayList<Issue>(usedMax);
    final Iterator<Issue> iterator = searchResult.iterator();
    for (int i = 0 ; i < usedMax ; i++) {
      iterResults.add(iterator.next());
    }
    assertEquals(searchResult.issues, iterResults);
  }

  @Test
  public void testIterateBeyondMaxResult() throws JiraException {
    final JiraClient jira = new JiraClient("https://jira.atlassian.com/", null);

    //Will return everything from the public Jira for Jira (at the time of writing 163697 issues).
    final int usedMax = 2;
    Issue.SearchResult searchResult = jira.searchIssues("", usedMax);
    final Iterator<Issue> iterator = searchResult.iterator();
    System.out.println(searchResult.issues);
    for (int i = 0 ; i < 3 ; i++) {
      //Running this 3 times without failing, ensures the it can fetch issues beyond the first fetch batch size, as the used max result is only 2.
      iterator.next();
    }
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
