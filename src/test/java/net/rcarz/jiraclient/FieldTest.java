package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for stuff relating to fields.
 */
public class FieldTest {

	@Test
	public void testSelectListSingleChoice() throws JiraException {

		/*
		 * Given: The JSON body, the name of the field and the value
		 */
		String name = "customfield_10002";
		Object value = "vulnerability";
		String jsonString = "{\"summary\":{\"required\":true,\"schema\":{\"type\":\"string\",\"system\":\"summary\"},\"name\":\"Summary\",\"hasDefaultValue\":false,\"operations\":[\"set\"]},\"issuetype\":{\"required\":true,\"schema\":{\"type\":\"issuetype\",\"system\":\"issuetype\"},\"name\":\"Issue Type\",\"hasDefaultValue\":false,\"operations\":[],\"allowedValues\":[{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/issuetype/10002\",\"id\":\"10002\",\"description\":\"\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/secure/viewavatar?size=xsmall&avatarId=10300&avatarType=issuetype\",\"name\":\"Internal Bug\",\"subtask\":false,\"avatarId\":10300}]},\"attachment\":{\"required\":false,\"schema\":{\"type\":\"array\",\"items\":\"attachment\",\"system\":\"attachment\"},\"name\":\"Attachment\",\"hasDefaultValue\":false,\"operations\":[]},\"duedate\":{\"required\":false,\"schema\":{\"type\":\"date\",\"system\":\"duedate\"},\"name\":\"Due Date\",\"hasDefaultValue\":false,\"operations\":[\"set\"]},\"description\":{\"required\":false,\"schema\":{\"type\":\"string\",\"system\":\"description\"},\"name\":\"Description\",\"hasDefaultValue\":false,\"operations\":[\"set\"]},\"project\":{\"required\":true,\"schema\":{\"type\":\"project\",\"system\":\"project\"},\"name\":\"Project\",\"hasDefaultValue\":false,\"operations\":[\"set\"],\"allowedValues\":[{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/project/10000\",\"id\":\"10000\",\"key\":\"IRIUSDEV\",\"name\":\"IRIUSDEV\",\"projectTypeKey\":\"business\",\"avatarUrls\":{\"48x48\":\"http://jira7.iriusrisk.com:8080/secure/projectavatar?avatarId=10324\",\"24x24\":\"http://jira7.iriusrisk.com:8080/secure/projectavatar?size=small&avatarId=10324\",\"16x16\":\"http://jira7.iriusrisk.com:8080/secure/projectavatar?size=xsmall&avatarId=10324\",\"32x32\":\"http://jira7.iriusrisk.com:8080/secure/projectavatar?size=medium&avatarId=10324\"}}]},\"reporter\":{\"required\":true,\"schema\":{\"type\":\"user\",\"system\":\"reporter\"},\"name\":\"Reporter\",\"autoCompleteUrl\":\"http://jira7.iriusrisk.com:8080/rest/api/latest/user/search?username=\",\"hasDefaultValue\":false,\"operations\":[\"set\"]},\"assignee\":{\"required\":false,\"schema\":{\"type\":\"user\",\"system\":\"assignee\"},\"name\":\"Assignee\",\"autoCompleteUrl\":\"http://jira7.iriusrisk.com:8080/rest/api/latest/user/assignable/search?issueKey=null&username=\",\"hasDefaultValue\":false,\"operations\":[\"set\"]},\"priority\":{\"required\":false,\"schema\":{\"type\":\"priority\",\"system\":\"priority\"},\"name\":\"Priority\",\"hasDefaultValue\":true,\"operations\":[\"set\"],\"allowedValues\":[{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/priority/1\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/images/icons/priorities/highest.svg\",\"name\":\"Highest\",\"id\":\"1\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/priority/2\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/images/icons/priorities/high.svg\",\"name\":\"High\",\"id\":\"2\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/priority/3\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/images/icons/priorities/medium.svg\",\"name\":\"Medium\",\"id\":\"3\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/priority/4\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/images/icons/priorities/low.svg\",\"name\":\"Low\",\"id\":\"4\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/priority/5\",\"iconUrl\":\"http://jira7.iriusrisk.com:8080/images/icons/priorities/lowest.svg\",\"name\":\"Lowest\",\"id\":\"5\"}]},\"customfield_10002\":{\"required\":true,\"schema\":{\"type\":\"option\",\"custom\":\"com.atlassian.jira.plugin.system.customfieldtypes:select\",\"customId\":10002},\"name\":\"Testing Steps\",\"hasDefaultValue\":false,\"operations\":[\"set\"],\"allowedValues\":[{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/customFieldOption/10002\",\"value\":\"step one\",\"id\":\"10002\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/customFieldOption/10003\",\"value\":\"step two\",\"id\":\"10003\"},{\"self\":\"http://jira7.iriusrisk.com:8080/rest/api/2/customFieldOption/10004\",\"value\":\"step three\",\"id\":\"10004\"}]},\"timetracking\":{\"required\":false,\"schema\":{\"type\":\"timetracking\",\"system\":\"timetracking\"},\"name\":\"Time Tracking\",\"hasDefaultValue\":false,\"operations\":[\"set\",\"edit\"]},\"labels\":{\"required\":false,\"schema\":{\"type\":\"array\",\"items\":\"string\",\"system\":\"labels\"},\"name\":\"Labels\",\"autoCompleteUrl\":\"http://jira7.iriusrisk.com:8080/rest/api/1.0/labels/suggest?query=\",\"hasDefaultValue\":false,\"operations\":[\"add\",\"set\",\"remove\"]}}";
		final JSONObject editmeta = JSONObject.fromObject(jsonString);

		/*
		 * When: Parse the field to JSON
		 */
		Object result = Field.toJson(name, value, editmeta);

		/*
		 * Then: Return the value
		 */
		assertEquals(result, "{\"value\":\"" + value + "\"}");
	}
}
