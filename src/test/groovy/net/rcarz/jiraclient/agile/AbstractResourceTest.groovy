package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.JiraClient
import net.rcarz.jiraclient.RestClient
import net.sf.json.JSONObject
import net.sf.json.JSONSerializer
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNull

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 *  Created on 2016-05-19.
 * @author pldupont
 */
class AbstractResourceTest {
    AgileClient agileClient;
    RestClient mockRestClient;

    void "given an Agile Client"() {
        mockRestClient = mock RestClient.class
        def mockJiraClient = mock JiraClient.class
        when(mockJiraClient.getRestClient()).thenReturn(mockRestClient)
        agileClient = new AgileClient(mockJiraClient)
    }

    RestClient "given a REST Client"() {
        mockRestClient = mock RestClient.class
        return mockRestClient
    }

    Issue "given an Issue"() {
        mockRestClient = mock RestClient.class
        return new Issue(mockRestClient, JSONSerializer.toJSON(JSONResources.ISSUE) as JSONObject)
    }

    static void "Assert equals to Board"(Board board) {
        assertThat board, new IsNot<>(new IsNull())
        assertThat board.getId(), new IsEqual<Long>(JSONResources.BOARD_ID)
        assertThat board.getName(), new IsEqual<String>(JSONResources.BOARD_NAME)
        assertThat board.getType(), new IsEqual<String>(JSONResources.BOARD_TYPE)
        assertThat board.getSelfURL(), new IsEqual<String>(JSONResources.BOARD_SELF)
        assertThat board.toString(), new IsEqual<String>(
                String.format("Board{id=%s, name='%s'}",
                        JSONResources.BOARD_ID, JSONResources.BOARD_NAME))

    }

    static void "Assert equals to Sprint"(Sprint sprint) {
        assertThat sprint, new IsNot<>(new IsNull())
        assertThat sprint.getId(), new IsEqual<Long>(JSONResources.SPRINT_ID)
        assertThat sprint.getName(), new IsEqual<String>(JSONResources.SPRINT_NAME)
        assertThat sprint.getSelfURL(), new IsEqual<String>(JSONResources.SPRINT_SELF)
        assertThat sprint.getState(), new IsEqual<String>(JSONResources.SPRINT_STATE)
        assertThat sprint.getOriginBoardId(), new IsEqual<Long>(JSONResources.SPRINT_ORIGIN_BOARD_ID)
        assertThat sprint.getStartDate(), new IsEqual<Date>(JSONResources.SPRINT_START_DATE)
        assertThat sprint.getEndDate(), new IsEqual<Date>(JSONResources.SPRINT_END_DATE)
        assertThat sprint.getCompleteDate(), new IsEqual<Date>(JSONResources.SPRINT_COMPLETE_DATE)
        assertThat sprint.toString(), new IsEqual<String>(
                String.format("Sprint{id=%s, name='%s'}",
                        JSONResources.SPRINT_ID, JSONResources.SPRINT_NAME))
    }

    static void "Assert equals to Epic"(Epic epic) {
        assertThat epic, new IsNot<>(new IsNull())
        assertThat epic.getId(), new IsEqual<Long>(JSONResources.EPIC_ID)
        assertThat epic.getName(), new IsEqual<String>(JSONResources.EPIC_NAME)
        assertThat epic.getSelfURL(), new IsEqual<String>(JSONResources.EPIC_SELF)
        assertThat epic.getKey(), new IsEqual<String>(JSONResources.EPIC_KEY)
        assertThat epic.getSummary(), new IsEqual<String>(JSONResources.EPIC_SUMMARY)
        assertThat epic.isDone(), new IsEqual<Boolean>(JSONResources.EPIC_DONE)
        assertThat epic.toString(), new IsEqual<String>(
                String.format("Epic{id=%s, name='%s'}",
                        JSONResources.EPIC_ID, JSONResources.EPIC_NAME))
    }

    static void "Assert equals to Project"(Project project) {
        assertThat project, new IsNot<>(new IsNull())
        assertThat project.getId(), new IsEqual<Long>(JSONResources.PROJECT_ID)
        assertThat project.getName(), new IsEqual<String>(JSONResources.PROJECT_NAME)
        assertThat project.getSelfURL(), new IsEqual<String>(JSONResources.PROJECT_SELF)
        assertThat project.getKey(), new IsEqual<String>(JSONResources.PROJECT_KEY)
        assertThat project.toString(), new IsEqual<String>(
                String.format("Project{id=%s, name='%s'}",
                        JSONResources.PROJECT_ID, JSONResources.PROJECT_NAME))
    }

    static void "Assert equals to Comment"(Comment comment) {
        assertThat comment, new IsNot<>(new IsNull())
        assertThat comment.getId(), new IsEqual<Long>(JSONResources.ISSUE_COMMENT_ID)
        assertThat comment.getName(), new IsNull<String>()
        assertThat comment.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_COMMENT_SELF)
        assertThat comment.getBody(), new IsEqual<String>(JSONResources.ISSUE_COMMENT_BODY)
        assertThat comment.getCreated(), new IsEqual<Date>(JSONResources.ISSUE_COMMENT_CREATED)
        assertThat comment.getUpdated(), new IsEqual<Date>(JSONResources.ISSUE_COMMENT_UPDATED)
        "Assert equals to User"(comment.getAuthor())
        "Assert equals to User"(comment.getUpdateAuthor())
        assertThat comment.toString(), new IsEqual<String>(
                String.format("Comment{id=%s, body='%s'}",
                        JSONResources.ISSUE_COMMENT_ID, JSONResources.ISSUE_COMMENT_BODY))
    }

    static void "Assert equals to TimeTracking"(TimeTracking timeTracking) {
        assertThat timeTracking, new IsNot<>(new IsNull())
        assertThat timeTracking.getId(), new IsEqual<Long>(0L)
        assertThat timeTracking.getName(), new IsNull<String>()
        assertThat timeTracking.getSelfURL(), new IsNull<String>()
        assertThat timeTracking.getOriginalEstimate(), new IsEqual<String>(JSONResources.ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE)
        assertThat timeTracking.getRemainingEstimate(), new IsEqual<String>(JSONResources.ISSUE_TIMETRACKING_REMAINING_ESTIMATE)
        assertThat timeTracking.getTimeSpent(), new IsEqual<String>(JSONResources.ISSUE_TIMETRACKING_TIME_SPENT)
        assertThat timeTracking.getOriginalEstimateSeconds(), new IsEqual<Long>(JSONResources.ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE_SECONDS)
        assertThat timeTracking.getRemainingEstimateSeconds(), new IsEqual<Long>(JSONResources.ISSUE_TIMETRACKING_REMAINING_ESTIMATE_SECONDS)
        assertThat timeTracking.getTimeSpentSeconds(), new IsEqual<Long>(JSONResources.ISSUE_TIMETRACKING_TIME_SPENT_SECONDS)
        assertThat timeTracking.toString(), new IsEqual<String>(
                String.format("TimeTracking{original='%s', remaining='%s', timeSpent='%s'}",
                        JSONResources.ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE,
                        JSONResources.ISSUE_TIMETRACKING_REMAINING_ESTIMATE,
                        JSONResources.ISSUE_TIMETRACKING_TIME_SPENT))
    }

    static void "Assert equals to IssueType"(IssueType issueType) {
        assertThat issueType, new IsNot<>(new IsNull())
        assertThat issueType.getId(), new IsEqual<Long>(JSONResources.ISSUE_TYPE_ID)
        assertThat issueType.getName(), new IsEqual<String>(JSONResources.ISSUE_TYPE_NAME)
        assertThat issueType.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_TYPE_SELF)
        assertThat issueType.getDescription(), new IsEqual<String>(JSONResources.ISSUE_TYPE_DESCRIPTION)
        assertThat issueType.isSubTask(), new IsEqual<Boolean>(JSONResources.ISSUE_TYPE_SUB_TASK)
        assertThat issueType.toString(), new IsEqual<String>(
                String.format("IssueType{id=%s, name='%s'}",
                        JSONResources.ISSUE_TYPE_ID, JSONResources.ISSUE_TYPE_NAME))
    }

    static void "Assert equals to Status"(Status status) {
        assertThat status, new IsNot<>(new IsNull())
        assertThat status.getId(), new IsEqual<Long>(JSONResources.ISSUE_STATUS_ID)
        assertThat status.getName(), new IsEqual<String>(JSONResources.ISSUE_STATUS_NAME)
        assertThat status.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_STATUS_SELF)
        assertThat status.getDescription(), new IsEqual<String>(JSONResources.ISSUE_STATUS_DESCRIPTION)
        assertThat status.toString(), new IsEqual<String>(
                String.format("Status{id=%s, name='%s'}",
                        JSONResources.ISSUE_STATUS_ID, JSONResources.ISSUE_STATUS_NAME))
    }

    static void "Assert equals to Resolution"(Resolution resolution) {
        assertThat resolution, new IsNot<>(new IsNull())
        assertThat resolution.getId(), new IsEqual<Long>(JSONResources.ISSUE_RESOLUTION_ID)
        assertThat resolution.getName(), new IsEqual<String>(JSONResources.ISSUE_RESOLUTION_NAME)
        assertThat resolution.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_RESOLUTION_SELF)
        assertThat resolution.getDescription(), new IsEqual<String>(JSONResources.ISSUE_RESOLUTION_DESCRIPTION)
        assertThat resolution.toString(), new IsEqual<String>(
                String.format("Resolution{id=%s, name='%s'}",
                        JSONResources.ISSUE_RESOLUTION_ID, JSONResources.ISSUE_RESOLUTION_NAME))
    }

    static void "Assert equals to Priority"(Priority priority) {
        assertThat priority, new IsNot<>(new IsNull())
        assertThat priority.getId(), new IsEqual<Long>(JSONResources.ISSUE_PRIORITY_ID)
        assertThat priority.getName(), new IsEqual<String>(JSONResources.ISSUE_PRIORITY_NAME)
        assertThat priority.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_PRIORITY_SELF)
        assertThat priority.toString(), new IsEqual<String>(
                String.format("Priority{id=%s, name='%s'}",
                        JSONResources.ISSUE_PRIORITY_ID, JSONResources.ISSUE_PRIORITY_NAME))
    }

    static void "Assert equals to User"(User user) {
        assertThat user, new IsNot<>(new IsNull())
        assertThat user.getId(), new IsEqual<Long>(0L)
        assertThat user.getName(), new IsEqual<String>(JSONResources.USER_NAME)
        assertThat user.getSelfURL(), new IsEqual<String>(JSONResources.USER_SELF)
        assertThat user.getEmailAddress(), new IsEqual<String>(JSONResources.USER_EMAIL_ADDRESS)
        assertThat user.getDisplayName(), new IsEqual<String>(JSONResources.USER_DISPLAY_NAME)
        assertThat user.isActive(), new IsEqual<Boolean>(JSONResources.USER_ACTIVE)
        assertThat user.getTimeZone(), new IsEqual<String>(JSONResources.USER_TIME_ZONE)
        assertThat user.toString(), new IsEqual<String>(
                String.format("User{name='%s', Display Name='%s'}",
                        JSONResources.USER_NAME, JSONResources.USER_DISPLAY_NAME))
    }

    static void "Assert equals to Worklog"(Worklog worklog) {
        assertThat worklog, new IsNot<>(new IsNull())
        assertThat worklog.getId(), new IsEqual<Long>(JSONResources.ISSUE_WORKLOG_ID)
        assertThat worklog.getName(), new IsNull<String>()
        assertThat worklog.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_WORKLOG_SELF)
        assertThat worklog.getComment(), new IsEqual<String>(JSONResources.ISSUE_WORKLOG_COMMENT)
        assertThat worklog.getCreated(), new IsEqual<Date>(JSONResources.ISSUE_WORKLOG_CREATED)
        assertThat worklog.getUpdated(), new IsEqual<Date>(JSONResources.ISSUE_WORKLOG_UPDATED)
        assertThat worklog.getStarted(), new IsEqual<Date>(JSONResources.ISSUE_WORKLOG_STARTED)
        assertThat worklog.getTimeSpent(), new IsEqual<String>(JSONResources.ISSUE_WORKLOG_TIMESPEND)
        assertThat worklog.getTimeSpentSeconds(), new IsEqual<Long>(JSONResources.ISSUE_WORKLOG_TIMESPEND_SECONDS)
        "Assert equals to User"(worklog.getAuthor())
        "Assert equals to User"(worklog.getUpdateAuthor())
        assertThat worklog.toString(), new IsEqual<String>(
                String.format("Worklog{id=%s, comment='%s'}",
                        JSONResources.ISSUE_WORKLOG_ID, JSONResources.ISSUE_WORKLOG_COMMENT))
    }

    static void "Assert equals to Issue"(Issue issue) {
        assertThat issue, new IsNot<>(new IsNull())
        assertThat issue.getAttribute("fields"), new IsNot<>(new IsNull())
        assertThat issue.getId(), new IsEqual<Long>(JSONResources.ISSUE_ID)
        assertThat issue.getName(), new IsEqual<String>(JSONResources.ISSUE_SUMMARY)
        assertThat issue.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_SELF)
        assertThat issue.getKey(), new IsEqual<String>(JSONResources.ISSUE_KEY)
        assertThat issue.isFlagged(), new IsEqual<Boolean>(JSONResources.ISSUE_FLAGGED)
        "Assert equals to Sprint"(issue.getSprint())
        assertThat issue.getClosedSprints(), new IsNot<>(new IsNull<>())
        assertThat issue.getClosedSprints().size(), new IsEqual<Integer>(3)
        assertThat issue.getDescription(), new IsEqual<String>(JSONResources.ISSUE_DESCRIPTION)
        "Assert equals to Project"(issue.getProject())
        assertThat issue.getComments(), new IsNot<>(new IsNull<>())
        assertThat issue.getComments().size(), new IsEqual<Integer>(1)
        "Assert equals to Comment"(issue.getComments().get(0))
        "Assert equals to Epic"(issue.getEpic())
        "Assert equals to TimeTracking"(issue.getTimeTracking())
        assertThat issue.getWorklogs(), new IsNot<>(new IsNull<>())
        assertThat issue.getWorklogs().size(), new IsEqual<Integer>(1)
        "Assert equals to Worklog"(issue.getWorklogs().get(0))
        assertThat issue.getEnvironment(), new IsEqual<String>(JSONResources.ISSUE_ENVIRONMENT)
        "Assert equals to IssueType"(issue.getIssueType())
        "Assert equals to Status"(issue.getStatus())
        "Assert equals to Resolution"(issue.getResolution())
        assertThat issue.getCreated(), new IsEqual<Date>(JSONResources.ISSUE_CREATED)
        assertThat issue.getUpdated(), new IsEqual<Date>(JSONResources.ISSUE_UPDATED)
        "Assert equals to User"(issue.getAssignee())
        "Assert equals to User"(issue.getCreator())
        "Assert equals to User"(issue.getReporter())
        "Assert equals to Priority"(issue.getPriority())
        assertThat issue.toString(), new IsEqual<String>(
                String.format("Issue{id=%s, name='%s'}",
                        JSONResources.ISSUE_ID, JSONResources.ISSUE_SUMMARY))
    }

    static void "Assert equals to Issue Blank"(Issue issue) {
        assertThat issue, new IsNot<>(new IsNull())
        assertThat issue.getAttribute("fields"), new IsNull()
        assertThat issue.getId(), new IsEqual<Long>(JSONResources.BLANK_ISSUE1_ID)
        assertThat issue.getName(), new IsNull<String>()
        assertThat issue.getSelfURL(), new IsEqual<String>(JSONResources.BLANK_ISSUE1_SELF)
        assertThat issue.toString(), new IsEqual<String>(
                String.format("Issue{id=%s, name='%s'}",
                        JSONResources.BLANK_ISSUE1_ID, null))
    }
}
