package net.rcarz.jiraclient;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ProjectTest {

    @Test
    public void testCreateProject() {
        new Project(null, Utils.getTestProject());
    }

    @Test
    public void projectDataTest()
    {
        String email = "from-jira@example.com";
        String assigneeType = "PROJECT_LEAD";
        String name = "Example";
        String self =  "http://www.example.com/jira/rest/api/2/project/EX";
        String id = "10000";
        String key = "EX";
        String description = "This project was created as an example for REST.";
        Project project = new Project(null, Utils.getTestProject());

        assertEquals(description, project.getDescription());
        assertEquals(name, project.getName());
        assertEquals(id, project.getId());
        assertEquals(email, project.getEmail());
        assertEquals(assigneeType, project.getAssigneeType());
        assertTrue(project.getVersions().isEmpty());
        assertEquals(name, project.getName());
        assertEquals(self, project.getSelf());
        assertEquals( key, project.getKey());
    }
    @Test
    public void projectIssueTypesTest()
    {
        Project project = new Project(null, Utils.getTestProject());
        assertEquals(2, project.getIssueTypes().size());
        assertEquals("Task", project.getIssueTypes().get(0).getName());
        assertEquals("Bug", project.getIssueTypes().get(1).getName());
    }
    @Test
    public void projectCategoryTest()
    {
        String name = "FIRST";
        String id = "10000";
        String description = "First Project Category";

        Project project = new Project(null, Utils.getTestProject());
        assertNotNull(project.getCategory());
        assertEquals(description, project.getCategory().getDescription());

        assertEquals(name, project.getCategory().getName());
        assertEquals(id, project.getCategory().getId());
    }
}
