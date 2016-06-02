import net.rcarz.jiraclient.BasicCredentials
import net.rcarz.jiraclient.JiraClient
import net.rcarz.jiraclient.agile.*
import org.junit.Ignore
import org.junit.Test

/**
 * Demo test, used to show how to use the AgileClient API.
 * @author pldupont
 */
class AgileClientDemoTest {

    private static final long BOARD_ID = 507L;
    private static final long SPRINT_ID = 1165L;
    private static final long EPIC_ID = 62133L;
    private static final long ISSUE_ID = 63080L;
    private static final String ISSUE_KEY = "TEST-1551";

    @Test
    @Ignore("Demo to use the AgileClient")
    public void demoUsingAgileClient() {
        // Init Agile client
        AgileClient agileClient = new AgileClient(new JiraClient("https://jira.example.com/jira", new BasicCredentials("batman", "pow! pow!")))

        demoBoard(agileClient)
        demoSprint(agileClient)
        demoEpic(agileClient)
        demoIssue(agileClient)
    }

    static void demoSprint(AgileClient agileClient) {
        println "********** Sprint demo"
        // Retrieve all sprints
        List<Sprint> sprints = Sprint.getAll(agileClient.getRestclient(), BOARD_ID)
        println sprints

        // Retrieve a specific Sprint
        Sprint sprint1 = agileClient.getSprint(SPRINT_ID)
        println sprint1
        Sprint sprint2 = Sprint.get(agileClient.getRestclient(), SPRINT_ID)
        println sprint2
        println sprint1.toString() == sprint2.toString()
        println sprint1.getSelfURL()

        // Retrieve issues associated to the sprint
        List<Issue> issues = sprint1.getIssues()
        println issues
    }

    static void demoIssue(AgileClient agileClient) {
        println "********** Issue demo"
        // Retrieve a specific Issue
        Issue issue1 = agileClient.getIssue(ISSUE_ID)
        println issue1
        Issue issue2 = Issue.get(agileClient.getRestclient(), ISSUE_ID)
        println issue2
        println issue1.toString() == issue2.toString()
        Issue issue3 = agileClient.getIssue(ISSUE_KEY)
        println issue3
        println issue1.toString() == issue3.toString()
        println issue1.getSelfURL()

        // Retrieve the issue attribute
        println issue1.getProject()
        println issue1.getEpic()
        println issue1.getSprint()
        println issue1.getKey();
        println issue1.isFlagged();
        println issue1.getDescription();
        println issue1.getComments();
        println issue1.getWorklogs();
        println issue1.getTimeTracking();
        println issue1.getIssueType();
        println issue1.getStatus();
        println issue1.getResolution();
        println issue1.getCreated();
        println issue1.getUpdated();
        println issue1.getPriority();
        println issue1.getAssignee();
        println issue1.getCreator();
        println issue1.getReporter();
        println issue1.getEnvironment();
    }

    static void demoEpic(AgileClient agileClient) {
        println "********** Epic demo"
        // Retrieve a specific Epic
        Epic epic1 = agileClient.getEpic(EPIC_ID)
        println epic1
        Epic epic2 = Epic.get(agileClient.getRestclient(), EPIC_ID)
        println epic2
        println epic1.toString() == epic2.toString()
        println epic1.getSelfURL()

        // Retrieve the epic as a normal Issue
        Issue issue = epic1.asIssue(true)
        println issue

        // pldupont: Doesn't work with my version of JIRA, but the doc says otherwise.
//        // Retrieve issues associated to the Epic
//        List<Issue> issues = epic1.getIssues()
//        println issues
    }

    static void demoBoard(AgileClient agileClient) {
        println "********** Board demo"
        // Retrieve all board
        List<Board> boards = agileClient.getBoards()
        println boards

        // Retrieve a specific Board
        Board board1 = agileClient.getBoard(BOARD_ID)
        println board1
        Board board2 = Board.get(agileClient.getRestclient(), BOARD_ID)
        println board2
        println board1.toString() == board2.toString()
        println board1.getSelfURL()

        // Retrieve sprints associated to the board
        List<Sprint> sprints = board1.getSprints()
        println sprints

        // Retrieve sprints associated to the board
        List<Epic> epics = board1.getEpics()
        println epics

        // Retrieve sprints associated to the board
        List<Issue> backlog = board1.getBacklog()
        println backlog

        // pldupont: Doesn't work with my version of JIRA, but the doc says otherwise.
//        // Retrieve sprints associated to the board
//        List<Sprint> issuesWithoutEpics = board1.getIssuesWithoutEpic()
//        println sprints

    }
}
