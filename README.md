# jira-client #

jira-client is a simple and lightweight JIRA REST client library for Java.

The goal of the project is to provide **simple** and clean English idiomatic expressions for interacting with JIRA. In pursuit of this goal, jira-client lacks the usual verbose and cumbersome contortions often found in Java applications. And since the implementation isn't buried under 57 layers of complicated abstractions, jira-client is easy to extend and debug.

jira-client depends on [Apache HttpComponents](http://hc.apache.org/) and [json-lib](http://json.sourceforge.net/).

## Features ##

jira-client is still under heavily development. Here's what works:

* Retrieve issues by key
* Create issues
* Update issues (both system fields and custom fields)
* Transition issues to new states
* Add comments to issues
* Vote on issues
* Add and remove issue watchers
* Add and remove issue links
* Create sub-tasks

## Contributing ##

Patches are welcome and appreciated. Please try to follow existing styles, and strive for simplicity. Make sure to add yourself to [AUTHORS](AUTHORS.md)!

## Quick Start Example ##

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class Example {

    public static void main(String[] args) {

        BasicCredentials creds = new BasicCredentials();
        creds.host = "jira.example.com"; /* this isn't required, but is good for security */
        creds.username = "batman";
        creds.password = "pow! pow!";

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

            /* Add two comments, with one limited to the developer role. */
            issue.addComment("No problem. We'll get right on it!");
            issue.addComment("He tried to send a whole Internet!", "role", "Developers");

            /* Print the reporter's username and then the display name */
            System.out.println("Reporter: " + issue.getReporter());
            System.out.println("Reporter's Name: " + issue.getReporter().getDisplayName());

            /* Print existing labels (if any). */
            for (String l : issue.getLabels())
                System.out.println("Label: " + l);

            /* Change the summary and add two labels to issue. The double-brace initialiser
               isn't required, but it helps with readability. */
            issue.update()
                .field(Field.SUMMARY, "tubes are clogged")
                .field(Field.LABELS, new ArrayList() {{
                    addAll(issue.getLabels());
                    add("foo");
                    add("bar");
                }})
                .execute();

            /* Print the summary. We have to refresh first to pickup the new value. */
            issue.refresh();
            System.out.println("New Summary: " + issue.getSummary());

            /* Now let's start progress on this issue. */
            issue.transition().execute("Start Progress");

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

            /* Set two new values for customfield_5678. */
            issue.update()
                .field("customfield_5678", new HashMap() {{
                    put("value", "foo");
                    put("value", "bar");
                }})
                .execute();

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
        } catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
    }
}
```
