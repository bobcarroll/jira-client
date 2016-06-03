# jira-client #

[![Build Status](https://travis-ci.org/rcarz/jira-client.svg?branch=master)](https://travis-ci.org/rcarz/jira-client)

jira-client is a simple and lightweight JIRA REST client library for Java.

The goal of the project is to provide **simple** and clean English idiomatic expressions for interacting with JIRA. In pursuit of this goal, jira-client lacks the usual verbose and cumbersome contortions often found in Java applications. And since the implementation isn't buried under 57 layers of complicated abstractions, jira-client is easy to extend and debug.

jira-client depends on [Apache HttpComponents](http://hc.apache.org/), [json-lib](http://json.sourceforge.net/), and [joda-time](http://www.joda.org/joda-time/).

## Features ##

jira-client is still under heavy development. Here's what works:

* Retrieve issues by key
* Search for issues with JQL
* Create issues
* Update issues (both system fields and custom fields)
* Finding allowed values for components and custom fields
* Transition issues to new states
* Add comments to issues
* Add attachments to issues
* Vote on issues
* Add and remove issue watchers
* Add and remove issue links
* Create sub-tasks
* Retrieval of Rapid Board backlog and sprints

## Maven Dependency ##

Point your *settings.xml* at [Maven Central](http://repo1.maven.org/maven2) and add jira-client to your project.

```xml
    <dependency>
      <groupId>net.rcarz</groupId>
      <artifactId>jira-client</artifactId>
      <version>0.5</version>
      <scope>compile</scope>
    </dependency>
```

## Contributing ##

Patches are welcome and appreciated. Please try to follow existing styles, and strive for simplicity. Make sure to add yourself to [AUTHORS](AUTHORS.md)!

## Quick Start Example ##

```java
import java.util.ArrayList;
import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class Example {

    public static void main(String[] args) {

        BasicCredentials creds = new BasicCredentials("batman", "pow! pow!");
        JiraClient jira = new JiraClient("https://jira.example.com/jira", creds);

        try {
            /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
            Issue issue = jira.getIssue("TEST-123");

            /* Print the issue key. */
            System.out.println(issue);

            /* You can also do it like this: */
            System.out.println(issue.getKey());

            /* Vote for the issue. */
            issue.vote();

            /* And also watch it. Add Robin too. */
            issue.addWatcher(jira.getSelf());
            issue.addWatcher("robin");

            /* Open the issue and assign it to batman. */
            issue.transition()
                .field(Field.ASSIGNEE, "batman")
                .execute("Open");
                
            /* Assign the issue */
            issue.update()
                .field(Field.ASSIGNEE, "batman")
                .execute();

            /* Add two comments, with one limited to the developer role. */
            issue.addComment("No problem. We'll get right on it!");
            issue.addComment("He tried to send a whole Internet!", "role", "Developers");

            /* Print the reporter's username and then the display name */
            System.out.println("Reporter: " + issue.getReporter());
            System.out.println("Reporter's Name: " + issue.getReporter().getDisplayName());

            /* Print existing labels (if any). */
            for (String l : issue.getLabels())
                System.out.println("Label: " + l);

            /* Change the summary and add two labels to the issue. The double-brace initialiser
               isn't required, but it helps with readability. */
            issue.update()
                .field(Field.SUMMARY, "tubes are clogged")
                .field(Field.LABELS, new ArrayList() {{
                    addAll(issue.getLabels());
                    add("foo");
                    add("bar");
                }})
                .field(Field.PRIORITY, Field.valueById("1")) /* you can also set the value by ID */
                .execute();

            /* You can also update values with field operations. */
            issue.update()
                .fieldAdd(Field.LABELS, "baz")
                .fieldRemove(Field.LABELS, "foo")
                .execute();

            /* Print the summary. We have to refresh first to pickup the new value. */
            issue.refresh();
            System.out.println("New Summary: " + issue.getSummary());

            /* Now let's start progress on this issue. */
            issue.transition().execute("Start Progress");

            /* Add the first comment and update it */
            Comment comment = issue.addComment("I am a comment!");
            comment.update("I am the first comment!");
            issue.getComments().get(0).update("this works too!");

            /* Pretend customfield_1234 is a text field. Get the raw field value... */
            Object cfvalue = issue.getField("customfield_1234");

            /* ... Convert it to a string and then print the value. */
            String cfstring = Field.getString(cfvalue);
            System.out.println(cfstring);

            /* And finally, change the value. */
            issue.update()
                .field("customfield_1234", "new value!")
                .execute();

            /* Pretend customfield_5678 is a multi-select box. Print out the selected values. */
            List<CustomFieldOption> cfselect = Field.getResourceArray(
                CustomFieldOption.class,
                issue.getField("customfield_5678"),
                jira.getRestClient()
            );
            for (CustomFieldOption cfo : cfselect)
                System.out.println("Custom Field Select: " + cfo.getValue());
               
            /* Print out allowed values for the custom multi-select box. */
            List<CustomFieldOption> allowedValues = jira.getCustomFieldAllowedValues("customfield_5678", "TEST", "Task");
            for (CustomFieldOption customFieldOption : allowedValues)
                System.out.println(customFieldOption.getValue());

            /* Set two new values for customfield_5678. */
            issue.update()
                .field("customfield_5678", new ArrayList() {{
                    add("foo");
                    add("bar");
                    add(Field.valueById("1234")); /* you can also update using the value ID */
                }})
                .execute();
                
            /* Add an attachment */
            File file = new File("C:\\Users\\John\\Desktop\\screenshot.jpg");
            issue.addAttachment(file);

            /* And finally let's resolve it as incomplete. */
            issue.transition()
                .field(Field.RESOLUTION, "Incomplete")
                .execute("Resolve Issue");

            /* Create a new issue. */
            Issue newIssue = jira.createIssue("TEST", "Bug")
                .field(Field.SUMMARY, "Bat signal is broken")
                .field(Field.DESCRIPTION, "Commissioner Gordon reports the Bat signal is broken.")
                .field(Field.REPORTER, "batman")
                .field(Field.ASSIGNEE, "robin")
                .execute();
            System.out.println(newIssue);

            /* Link to the old issue */
            newIssue.link("TEST-123", "Dependency");

            /* Create sub-task */
            Issue subtask = newIssue.createSubtask()
                .field(Field.SUMMARY, "replace lightbulb")
                .execute();

            /* Search for issues */
            Issue.SearchResult sr = jira.searchIssues("assignee=batman");
            System.out.println("Total: " + sr.total);
            for (Issue i : sr.issues)
                System.out.println("Result: " + i);

            /* Search with paging (optionally 10 issues at a time). There are optional
               arguments for including/expanding fields, and page size/start. */
            Issue.SearchResult sr = jira.searchIssues("project IN (GOTHAM) ORDER BY id");
            while (sr.iterator().hasNext())
                System.out.println("Result: " + sr.iterator().next());

        } catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
    }
}
```

## GreenHopper Example ##

```java
import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.Epic;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.Marker;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;
import net.rcarz.jiraclient.greenhopper.SprintIssue;
import net.rcarz.jiraclient.greenhopper.SprintReport;

public class Example {

    public static void main(String[] args) {

        BasicCredentials creds = new BasicCredentials("batman", "pow! pow!");
        JiraClient jira = new JiraClient("https://jira.example.com/jira", creds);
        GreenHopperClient gh = new GreenHopperClient(jira);

        try {
            /* Retrieve all Rapid Boards */
            List<RapidView> allRapidBoards = gh.getRapidViews();

            /* Retrieve a specific Rapid Board by ID */
            RapidView board = gh.getRapidView(123);

            /* Print the name of all current and past sprints */
            List<Sprint> sprints = board.getSprints();
            for (Sprint s : sprints)
                System.out.println(s);

            /* Get the sprint report, print the sprint start date
               and the number of completed issues */
            SprintReport sr = board.getSprintReport();
            System.out.println(sr.getSprint().getStartDate());
            System.out.println(sr.getCompletedIssues().size());

            /* Get backlog data */
            Backlog backlog = board.getBacklogData();

            /* Print epic names */
            for (Epic e : backlog.getEpics())
                System.out.println(e);

            /* Print all issues in the backlog */
            for (SprintIssue si : backlog.getIssues())
                System.out.println(si);

            /* Print the names of sprints that haven't started yet */
            for (Marker m : backlog.getMarkers())
                System.out.println(m);

            /* Get the first issue on the backlog and add a comment */
            SprintIssue firstIssue = backlog.getIssues().get(0);
            Issue jiraIssue = firstIssue.getJiraIssue();
            jiraIssue.addComment("a comment!");
        } catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
    }
}
```

## Agile API ##
https://docs.atlassian.com/jira-software/REST/cloud/

### Agile supported calls ###
| Class | Method | REST Call |
| ----- | ------ | --------- |
| [AgileClient](src/main/java/net/rcarz/jiraclient/agile/AgileClient.java) | ```List<Board> getBoards()``` | GET /rest/agile/1.0/board |
| | ```Board getBoard(long id)``` | GET /rest/agile/1.0/board/{boardId} |
| | ```Sprint getSprint(long id)``` | GET /rest/agile/1.0/sprint/{sprintId} |
| | ```Epic getEpic(long id)``` | GET /rest/agile/1.0/epic/{epicId} |
| | ```Issue getIssue(long id)``` | GET /rest/agile/1.0/issue/{issueId} |
| | ```Issue getIssue(String key)``` | GET /rest/agile/1.0/issue/{issueKey} |
| [Board](src/main/java/net/rcarz/jiraclient/agile/Board.java) | ``` static List<Board> getAll(RestClient restclient)``` | GET /rest/agile/1.0/board |
| | ```static Board get(RestClient restclient, long id)``` | GET /rest/agile/1.0/board/{boardId} |
| | ```List<Sprint> getSprints()``` | GET /rest/agile/1.0/board/{boardId}/sprint |
| * | ```List<Epic> getEpics()``` | GET /rest/agile/1.0/board/{boardId}/epic
| * | ```List<Issue> getBacklog()``` | GET /rest/agile/1.0/board/{boardId}/backlog
| * | ```List<Issue> getIssuesWithoutEpic()``` | GET /rest/agile/1.0/board/{boardId}/epic/none/issue
| [Sprint](src/main/java/net/rcarz/jiraclient/agile/Sprint.java) | ``` static Sprint get(RestClient restclient, long sprintId)``` | GET /rest/agile/1.0/sprint/{sprintId} |
| | ```static List<Sprint> getAll(RestClient restclient, long boardId)``` | GET /rest/agile/1.0/board/{boardId}/sprint |
| * | ```List<Issue> getIssues()``` | GET /rest/agile/1.0/sprint/{sprintId}/issue |
| [Epic](src/main/java/net/rcarz/jiraclient/agile/Epic.java) | ```static Epic get(RestClient restclient, long id)``` | GET /rest/agile/1.0/epic/{epicId} |
| * | ```List<Issue> getIssues()``` | GET /rest/agile/1.0/epic/{epicId}/issue |
| [Issue](src/main/java/net/rcarz/jiraclient/agile/Issue.java) | ```static Issue get(RestClient restclient, long id)``` | GET /rest/agile/1.0/issue/{issueId} |
| | ```static Issue get(RestClient restclient, String key)``` | GET /rest/agile/1.0/issue/{issueKey} |
    
    

### Agile Example ###
To see more examples, look at [AgileClientDemoTest](src/test/groovy/AgileClientDemoTest.groovy)
```java
import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.agile.Board;
import net.rcarz.jiraclient.agile.AgileClient;

public class Example {

    public static void main(String[] args) {

        BasicCredentials creds = new BasicCredentials("batman", "pow! pow!");
        JiraClient jira = new JiraClient("https://jira.example.com/jira", creds);
        AgileClient agileClient = new AgileClient(jira);

        try {
            /* Retrieve all Boards */
            List<Board> allBoards = agileClient.getBoards();
        } catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null) {
                System.err.println(ex.getCause().getMessage());
            }
        }
    }
}
```

