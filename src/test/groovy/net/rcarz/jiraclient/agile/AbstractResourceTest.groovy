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

    static void "Assert equals to Board 84"(Board board) {
        assertThat board, new IsNot<>(new IsNull())
        assertThat board.getId(), new IsEqual<Long>(JSONResources.BOARD_ID)
        assertThat board.getName(), new IsEqual<String>(JSONResources.BOARD_NAME)
        assertThat board.getType(), new IsEqual<String>(JSONResources.BOARD_TYPE)
        assertThat board.getSelfURL(), new IsEqual<String>(JSONResources.BOARD_SELF)
        assertThat board.toString(), new IsEqual<String>(
                String.format("Board{id=%s, name='%s'}",
                        JSONResources.BOARD_ID, JSONResources.BOARD_NAME))

    }

    static void "Assert equals to Sprint 37"(Sprint sprint) {
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

    static void "Assert equals to Epic 23"(Epic epic) {
        assertThat epic, new IsNot<>(new IsNull())
        assertThat epic.getId(), new IsEqual<Long>(JSONResources.EPIC_ID)
        assertThat epic.getName(), new IsEqual<String>(JSONResources.EPIC_NAME)
        assertThat epic.getSelfURL(), new IsEqual<String>(JSONResources.EPIC_SELF)

    }

    static void "Assert equals to Project 10000"(Project project) {
        assertThat project, new IsNot<>(new IsNull())
        assertThat project.getId(), new IsEqual<Long>(JSONResources.PROJECT_ID)
        assertThat project.getName(), new IsEqual<String>(JSONResources.PROJECT_NAME)
        assertThat project.getSelfURL(), new IsEqual<String>(JSONResources.PROJECT_SELF)

    }

    static void "Assert equals to Comment 9999"(Comment comment) {
        assertThat comment, new IsNot<>(new IsNull())
        assertThat comment.getId(), new IsEqual<Long>(JSONResources.ISSUE_COMMENT_ID)
        assertThat comment.getName(), new IsNull<String>()
        assertThat comment.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_COMMENT_SELF)

    }

    static void "Assert equals to TimeTracking"(TimeTracking timeTracking) {
        assertThat timeTracking, new IsNot<>(new IsNull())
        assertThat timeTracking.getId(), new IsEqual<Long>(0L)
        assertThat timeTracking.getName(), new IsNull<String>()
        assertThat timeTracking.getSelfURL(), new IsNull<String>()

    }

    static void "Assert equals to IssueType"(IssueType issueType) {
        assertThat issueType, new IsNot<>(new IsNull())
        assertThat issueType.getId(), new IsEqual<Long>(JSONResources.ISSUE_TYPE_ID)
        assertThat issueType.getName(), new IsEqual<String>(JSONResources.ISSUE_TYPE_NAME)
        assertThat issueType.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_TYPE_SELF)
    }

    static void "Assert equals to Status"(Status status) {
        assertThat status, new IsNot<>(new IsNull())
        assertThat status.getId(), new IsEqual<Long>(JSONResources.ISSUE_STATUS_ID)
        assertThat status.getName(), new IsEqual<String>(JSONResources.ISSUE_STATUS_NAME)
        assertThat status.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_STATUS_SELF)
    }

    static void "Assert equals to Resolution"(Resolution resolution) {
        assertThat resolution, new IsNot<>(new IsNull())
        assertThat resolution.getId(), new IsEqual<Long>(JSONResources.ISSUE_RESOLUTION_ID)
        assertThat resolution.getName(), new IsEqual<String>(JSONResources.ISSUE_RESOLUTION_NAME)
        assertThat resolution.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_RESOLUTION_SELF)
    }

    static void "Assert equals to Priority"(Priority priority) {
        assertThat priority, new IsNot<>(new IsNull())
        assertThat priority.getId(), new IsEqual<Long>(JSONResources.ISSUE_PRIORITY_ID)
        assertThat priority.getName(), new IsEqual<String>(JSONResources.ISSUE_PRIORITY_NAME)
        assertThat priority.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_PRIORITY_SELF)
    }

    static void "Assert equals to User"(User user) {
        assertThat user, new IsNot<>(new IsNull())
        assertThat user.getId(), new IsEqual<Long>(0L)
        assertThat user.getName(), new IsEqual<String>(JSONResources.USER_NAME)
        assertThat user.getSelfURL(), new IsEqual<String>(JSONResources.USER_SELF)
    }

    static void "Assert equals to Worklog 100028"(Worklog worklog) {
        assertThat worklog, new IsNot<>(new IsNull())
        assertThat worklog.getId(), new IsEqual<Long>(JSONResources.ISSUE_WORKLOG_ID)
        assertThat worklog.getName(), new IsNull<String>()
        assertThat worklog.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_WORKLOG_SELF)
    }

    static void "Assert equals to Issue 10001"(Issue issue) {
        assertThat issue, new IsNot<>(new IsNull())
        assertThat issue.getId(), new IsEqual<Long>(JSONResources.ISSUE_ID)
        assertThat issue.getName(), new IsNull<String>()
        assertThat issue.getSelfURL(), new IsEqual<String>(JSONResources.ISSUE_SELF)
        assertThat issue.getKey(), new IsEqual<String>(JSONResources.ISSUE_KEY)
        assertThat issue.isFlagged(), new IsEqual<Boolean>(JSONResources.ISSUE_FLAGGED)
        "Assert equals to Sprint ${issue.getSprint().getId()}"(issue.getSprint())
        assertThat issue.getClosedSprints(), new IsNot<>(new IsNull<>())
        assertThat issue.getClosedSprints().size(), new IsEqual<Integer>(3)
        assertThat issue.getDescription(), new IsEqual<String>(JSONResources.ISSUE_DESCRIPTION)
        "Assert equals to Project ${issue.getProject().getId()}"(issue.getProject())
        assertThat issue.getComments(), new IsNot<>(new IsNull<>())
        assertThat issue.getComments().size(), new IsEqual<Integer>(1)
        "Assert equals to Comment ${issue.getComments().get(0).getId()}"(issue.getComments().get(0))
        "Assert equals to Epic ${issue.getEpic().getId()}"(issue.getEpic())
        "Assert equals to TimeTracking"(issue.getTimeTracking())
        assertThat issue.getWorklogs(), new IsNot<>(new IsNull<>())
        assertThat issue.getWorklogs().size(), new IsEqual<Integer>(1)
        "Assert equals to Worklog ${issue.getWorklogs().get(0).getId()}"(issue.getWorklogs().get(0))
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


    }

    static void "Assert equals to Issue 10010"(Issue issue) {
        assertThat issue, new IsNot<>(new IsNull())
        assertThat issue.getId(), new IsEqual<Long>(JSONResources.BLANK_ISSUE1_ID)
        assertThat issue.getName(), new IsNull<String>()
        assertThat issue.getSelfURL(), new IsEqual<String>(JSONResources.BLANK_ISSUE1_SELF)

    }

    static void "Assert equals to Issue HSP-1"(Issue issue) {
        "Assert equals to Issue 10001"(issue)

    }
}
