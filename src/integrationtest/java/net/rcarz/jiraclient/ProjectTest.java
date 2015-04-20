package net.rcarz.jiraclient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectTest {
    static String url() {
        return System.getenv("URL");
    }
    static String user() {
        return System.getenv("USER");
    }

    static String pwd() {
        return System.getenv("PASSWORD");
    }
    static String project() {
        return System.getenv("PROJECT");
    }

    JiraClient client;
    @Before
    public void setUp() throws Exception {
        client = new JiraClient(url(), new BasicCredentials(user(), pwd()));
    }

    @Test
    public void listAssignableUsers() throws JiraException {
        Project p = client.getProject(project());
        List<User> users = p.searchAssignableUsers();
        assertNotNull(users);
        System.out.println(users);
    }

    @Test
    public void listAssignableUsersIndex() throws JiraException {
        Project p = client.getProject(project());
        List<User> users = p.searchAssignableUsers(0,1);
        assertNotNull(users);
        assertEquals(1,users.size());
    }


}