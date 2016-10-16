package net.rcarz.jiraclient;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    assertThat(workLog).isNotNull();
    assertThat(workLog.getAuthor()).isNotNull();
    assertThat(workLog.getSelf()).isEqualTo("https://jira.test.lt/rest/api/2/issue/32374/worklog/80720");
    assertThat(workLog.getId()).isEqualTo("80720");
    assertThat(workLog.getComment()).isEqualTo("Test");
    assertThat(workLog.getCreatedDate().getTime()).isEqualTo(1454104800000L);
    assertThat(workLog.getUpdatedDate().getTime()).isEqualTo(1454104800000L);
    assertThat(workLog.getStarted().getTime()).isEqualTo(1453879853201L);
    assertThat(workLog.getTimeSpent()).isEqualTo("5m");
    assertThat(workLog.getTimeSpentSeconds()).isEqualTo(300);
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

  @Test
  public void testAdding_inputNullComment_shouldNotAdd() throws Exception {
    // Arrange
    Issue issue = mock(Issue.class);
    issue.restclient = mock(RestClient.class);
    doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

    // Act
    try {
      issue.addWorkLog(null, DateTime.now(), 120);
    } catch (JiraException e) {
      assertThat(e).hasMessageContaining("Failed add worklog to issue");
    }

    // Assert
    verify(issue.restclient, never()).post(anyString(), any(JSON.class));
  }

  @Test
  public void testAdding_inputNullDateTime_shouldNotAdd() throws Exception {
    // Arrange
    Issue issue = mock(Issue.class);
    issue.restclient = mock(RestClient.class);
    doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

    // Act
    try {
      issue.addWorkLog("asdf", null, 120);
    } catch (JiraException e) {
      assertThat(e).hasMessageContaining("Failed add worklog to issue");
    }

    // Assert
    verify(issue.restclient, never()).post(anyString(), any(JSON.class));
  }

  @Test
  public void testAdding_inputDurationTooLow_shouldNotAdd() throws Exception {
    // Arrange
    Issue issue = mock(Issue.class);
    issue.restclient = mock(RestClient.class);
    doCallRealMethod().when(issue).addWorkLog(anyString(), any(DateTime.class), anyLong());

    // Act
    try {
      issue.addWorkLog("asdf", DateTime.now(), 30);
    } catch (JiraException e) {
      assertThat(e).hasMessageContaining("Failed add worklog to issue");
    }

    // Assert
    verify(issue.restclient, never()).post(anyString(), any(JSON.class));
  }


  //region Constants

  // Mock response from jira
  public static final String RESPONSE_WORKLOG_BODY = "{\"self\":\"https://jira.test.lt/rest/api/2/issue/32374/worklog/80720\",\"author\":{\"self\":\"https://jira.test.lt/rest/api/2/user?username=test%40test.lt\",\"name\":\"test@test.lt\",\"key\":\"test@test.lt\",\"emailAddress\":\"test@test.lt\",\"avatarUrls\":{\"48x48\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=48\",\"24x24\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=24\",\"16x16\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=16\",\"32x32\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=32\"},\"displayName\":\"Marius Merkevicius\",\"active\":true,\"timeZone\":\"Europe/Vilnius\"},\"updateAuthor\":{\"self\":\"https://jira.test.lt/rest/api/2/user?username=test%40test.lt\",\"name\":\"test@test.lt\",\"key\":\"test@test.lt\",\"emailAddress\":\"test@test.lt\",\"avatarUrls\":{\"48x48\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=48\",\"24x24\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=24\",\"16x16\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=16\",\"32x32\":\"https://secure.gravatar.com/avatar/e4dacfe8f27cb89341bf990e556a4be0?d=mm&s=32\"},\"displayName\":\"Marius Merkevicius\",\"active\":true,\"timeZone\":\"Europe/Vilnius\"},\"comment\":\"Test\",\"created\":\"2016-01-30T20:46:16.583+0200\",\"updated\":\"2016-01-30T20:46:16.583+0200\",\"started\":\"2016-01-27T09:30:53.201+0200\",\"timeSpent\":\"5m\",\"timeSpentSeconds\":300,\"id\":\"80720\"}";

  //endregion


}