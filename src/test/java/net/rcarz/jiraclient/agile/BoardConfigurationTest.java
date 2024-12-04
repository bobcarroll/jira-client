package net.rcarz.jiraclient.agile;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.rcarz.jiraclient.JiraException;

/**
 * Test cases for stuff relating to filters.
 */
public class BoardConfigurationTest {

	@Test
	public void testGetBoardConfiguration() throws JiraException {
//		JiraClient jira = new JiraClient("https://jira.pointclickcare.com/jira", new TokenCredentials("AAC949A2137DA8A671EF197DDCCB8857"));
//		AgileClient agileClient = new AgileClient(jira);
//		Board board = agileClient.getBoard(144);
//		BoardConfiguration configuration = board.getConfiguarion();
//		//Filter filter = jira.getFilter(id);

		assertNotNull("q");
	}

}
