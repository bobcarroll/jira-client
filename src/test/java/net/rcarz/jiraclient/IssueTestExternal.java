package net.rcarz.jiraclient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;


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


    @Test
    public void testGetIssueStatus() throws JiraException{

        JiraClient jiraClient = new JiraClient(URL, new BasicCredentials(USERNAME,PASSWORD));
        Issue.FluentCreateComposed issuesCreator = jiraClient.createIssues(PROJECT_KEY, ISSUE_TYPE);

        Issue.IssueFields issueFieldsA = issuesCreator.createNewIssue();
        issueFieldsA.field(Field.SUMMARY, "Summary for issue A");
        issueFieldsA.field(Field.DESCRIPTION, "Description for issue A");

        Issue.IssueFields issueFieldsB = issuesCreator.createNewIssue();
        issueFieldsB.field(Field.SUMMARY, "Summary for issue B");
        issueFieldsB.field(Field.DESCRIPTION, "Description for issue B");

        Issue.IssueFields issueFieldsC = issuesCreator.createNewIssue();
        issueFieldsC.field(Field.SUMMARY, "Summary for issue C");
        issueFieldsC.field(Field.DESCRIPTION, "Description for issue C");

        List<String> ids = issuesCreator.execute();


        assertEquals(ids.size(), 3);

        List<Issue> issues = new ArrayList<Issue>();
        for (String id : ids){
            issues.add(jiraClient.getIssue(id));
        }

        assertEquals(issues.size(), 3);

        assertEquals(issues.get(0).getSummary(), "Summary for issue A");
        assertEquals(issues.get(0).getDescription(), "Description for issue A");

        assertEquals(issues.get(1).getSummary(), "Summary for issue B");
        assertEquals(issues.get(1).getDescription(), "Description for issue B");

        assertEquals(issues.get(2).getSummary(), "Summary for issue C");
        assertEquals(issues.get(2).getDescription(), "Description for issue C");

    }


}
