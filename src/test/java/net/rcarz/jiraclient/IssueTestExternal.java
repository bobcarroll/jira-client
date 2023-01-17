package net.rcarz.jiraclient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class IssueTestExternal {

    /**
     *In order to make this test to work we need to lo deploy a Jira docker in local machine with the following conf
     * -A user named 'admin' with password 'password1*'
     * -A project with key 'PROJ' and an issue type named 'Bug'
     *
     * To deploy docker in local: docker run --detach --name jira7131 --publish 15000:8080 cptactionhank/atlassian-jira:7.13.1
     */

    private static final String URL = "http://localhost:15000/";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password1*";
    private static final String PROJECT_KEY = "PROJ";
    private static final String ISSUE_TYPE = "Bug";
    private static final String SERVER_TYPE = "Server";


    @Test
    public void testGetIssueStatus() throws JiraException{

        JiraClient jiraClient = new JiraClient(URL, new BasicCredentials(USERNAME,PASSWORD));
        Issue.FluentCreateComposed issuesCreator =
                jiraClient.createIssues(Issue.getCreateMetadata(jiraClient.getRestClient(), PROJECT_KEY, ISSUE_TYPE),
                        PROJECT_KEY, ISSUE_TYPE, SERVER_TYPE);

        Issue.IssueFields issueFieldsA = issuesCreator.createNewIssue();
        issueFieldsA.field(Field.SUMMARY, "Summary for issue A");
        issueFieldsA.field(Field.DESCRIPTION, "Description for issue A");

        Issue.IssueFields issueFieldsB = issuesCreator.createNewIssue();
        issueFieldsB.field(Field.SUMMARY, "Summary for issue B");
        issueFieldsB.field(Field.DESCRIPTION, "Description for issue B");
        issueFieldsB.field(Field.PRIORITY, Integer.toString(Integer.MAX_VALUE));

        Issue.IssueFields issueFieldsC = issuesCreator.createNewIssue();
        issueFieldsC.field(Field.SUMMARY, "Summary for issue C");
        issueFieldsC.field(Field.DESCRIPTION, "Description for issue C");

        Issue.Results results = issuesCreator.execute();


        assertEquals(results.created.size(), 2);
        assertEquals(results.failed.size(), 1);
        assertEquals(results.created.get(0).name, "Summary for issue A");
        assertEquals(results.created.get(1).name, "Summary for issue C");
        assertEquals(results.failed.get(0).name, "Summary for issue B");
        assertTrue(results.failed.get(0).messages.contains("priority -> The priority selected is invalid."));

        List<Issue> issues = new ArrayList<Issue>();
        for (Issue.ResultCreated rc : results.created){
            issues.add(jiraClient.getIssue(rc.key));
        }

        assertEquals(issues.size(), 2);

        assertEquals(issues.get(0).getSummary(), "Summary for issue A");
        assertEquals(issues.get(0).getDescription(), "Description for issue A");

        assertEquals(issues.get(1).getSummary(), "Summary for issue C");
        assertEquals(issues.get(1).getDescription(), "Description for issue C");

    }


}
