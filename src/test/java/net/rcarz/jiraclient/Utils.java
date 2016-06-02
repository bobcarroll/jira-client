package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Utils {

    public static JSONObject getTestIssue() {
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON("{\n" +
        "  \"expand\": \"renderedFields,names,schema,transitions,operations,editmeta,changelog\",\n" +
        "  \"id\": \"10742\",\n" +
        "  \"self\": \"https://brainbubble.atlassian.net/rest/api/latest/issue/10742\",\n" +
        "  \"key\": \"FILTA-43\",\n" +
        "  \"fields\": {\n" +
        "    \"progress\": {\n" +
        "      \"progress\": 0,\n" +
        "      \"total\": 0\n" +
        "    },\n" +
        "    \"summary\": \"Maintain Company Details\",\n" +
        "    \"timetracking\": {\n" +
        "      \"originalEstimate\": \"1w\",\n" +
        "      \"remainingEstimate\": \"2d\",\n" +
        "      \"timeSpent\": \"3d\",\n" +
        "      \"originalEstimateSeconds\": 144000,\n" +
        "      \"remainingEstimateSeconds\": 57600,\n" +
        "      \"timeSpentSeconds\": 86400\n" +
        "    },\n" +
        "    \"issuetype\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/issuetype/7\",\n" +
        "      \"id\": \"7\",\n" +
        "      \"description\": \"This is a test issue type.\",\n" +
        "      \"iconUrl\": \"https://brainbubble.atlassian.net/images/icons/issuetypes/story.png\",\n" +
        "      \"name\": \"Story\",\n" +
        "      \"subtask\": false\n" +
        "    },\n" +
        "    \"votes\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/votes\",\n" +
        "      \"votes\": 0,\n" +
        "      \"hasVoted\": false\n" +
        "    },\n" +
        "    \"resolution\": null,\n" +
        "    \"fixVersions\": [\n" +
        "      {\n" +
        "        \"self\": \"https://brainbubble.atlassian.net/rest/api/2/version/10200\",\n" +
        "        \"id\": \"10200\",\n" +
        "        \"description\": \"First Full Functional Build\",\n" +
        "        \"name\": \"1.0\",\n" +
        "        \"archived\": false,\n" +
        "        \"released\": false,\n" +
        "        \"releaseDate\": \"2013-12-01\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"resolutiondate\": null,\n" +
        "    \"timespent\": 86400,\n" +
        "    \"reporter\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
        "      \"name\": \"joseph\",\n" +
        "      \"emailAddress\": \"joseph.b.mccarthy2012@googlemail.com\",\n" +
        "      \"avatarUrls\": {\n" +
        "        \"16x16\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
        "        \"24x24\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
        "        \"32x32\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\",\n" +
        "        \"48x48\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\"\n" +
        "      },\n" +
        "      \"displayName\": \"Joseph McCarthy\",\n" +
        "      \"active\": true\n" +
        "    },\n" +
        "    \"aggregatetimeoriginalestimate\": null,\n" +
        "    \"created\": \"2013-09-29T20:16:19.854+0100\",\n" +
        "    \"updated\": \"2013-10-09T22:24:55.961+0100\",\n" +
        "    \"description\": \"{panel:title=Description|borderStyle=dashed|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#FFFFCE}\\r\\nAs a company / admin\\r\\n\\r\\nI want to update the company details like contact details / name and so on\\r\\n\\r\\nSo that their details are up to date\\r\\n{panel}\\r\\n\\r\\n{panel:title=Acceptance Criteria|borderStyle=dashed|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#FFFFCE}\\r\\nCan I change the company name?\\r\\nCan I change our emails, addresses and phone number etc?\\r\\nCan I change my invoicing details?\\r\\nCan I change our application service agreement?\\r\\n{panel}\",\n" +
        "    \"priority\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/priority/3\",\n" +
        "      \"iconUrl\": \"https://brainbubble.atlassian.net/images/icons/priorities/major.png\",\n" +
        "      \"name\": \"Major\",\n" +
        "      \"id\": \"3\"\n" +
        "    },\n" +
        "    \"duedate\": null,\n" +
        "    \"customfield_10001\": null,\n" +
        "    \"customfield_10002\": null,\n" +
        "    \"customfield_10003\": null,\n" +
        "    \"issuelinks\": [\n" +
        "      \n" +
        "    ],\n" +
        "    \"customfield_10004\": null,\n" +
        "    \"watches\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/watchers\",\n" +
        "      \"watchCount\": 0,\n" +
        "      \"isWatching\": false\n" +
        "    },\n" +
        "    \"worklog\": {\n" +
        "      \"startAt\": 0,\n" +
        "      \"maxResults\": 20,\n" +
        "      \"total\": 0,\n" +
        "      \"worklogs\": [\n" +
        "        \n" +
        "      ]\n" +
        "    },\n" +
        "    \"customfield_10000\": null,\n" +
        "    \"subtasks\": [\n" +
        "      \n" +
        "    ],\n" +
        "    \"status\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/status/10004\",\n" +
        "      \"description\": \"Issue is currently in progress.\",\n" +
        "      \"iconUrl\": \"https://brainbubble.atlassian.net/images/icons/statuses/open.png\",\n" +
        "      \"name\": \"To Do\",\n" +
        "      \"id\": \"10004\"\n" +
        "    },\n" +
        "    \"customfield_10007\": null,\n" +
        "    \"customfield_10006\": \"90\",\n" +
        "    \"labels\": [\n" +
        "      \n" +
        "    ],\n" +
        "    \"customfield_10005\": null,\n" +
        "    \"workratio\": -1,\n" +
        "    \"assignee\": null,\n" +
        "    \"attachment\": [\n" +
        "      \n" +
        "    ],\n" +
        "    \"customfield_10200\": null,\n" +
        "    \"aggregatetimeestimate\": null,\n" +
        "    \"project\": {\n" +
        "      \"self\": \"https://brainbubble.atlassian.net/rest/api/2/project/10501\",\n" +
        "      \"id\": \"10501\",\n" +
        "      \"key\": \"FILTA\",\n" +
        "      \"name\": \"Filta\",\n" +
        "      \"avatarUrls\": {\n" +
        "        \"16x16\": \"https://brainbubble.atlassian.net/secure/projectavatar?size=xsmall&pid=10501&avatarId=10307\",\n" +
        "        \"24x24\": \"https://brainbubble.atlassian.net/secure/projectavatar?size=small&pid=10501&avatarId=10307\",\n" +
        "        \"32x32\": \"https://brainbubble.atlassian.net/secure/projectavatar?size=medium&pid=10501&avatarId=10307\",\n" +
        "        \"48x48\": \"https://brainbubble.atlassian.net/secure/projectavatar?pid=10501&avatarId=10307\"\n" +
        "      }\n" +
        "    },\n" +
        "    \"versions\": [\n" +
        "      \n" +
        "    ],\n" +
        "    \"environment\": null,\n" +
        "    \"timeestimate\": 144000,\n" +
        "    \"lastViewed\": \"2013-11-24T16:37:50.358+0000\",\n" +
        "    \"aggregateprogress\": {\n" +
        "      \"progress\": 0,\n" +
        "      \"total\": 0\n" +
        "    },\n" +
        "    \"components\": [\n" +
        "      {\n" +
        "        \"self\": \"https://brainbubble.atlassian.net/rest/api/2/component/10303\",\n" +
        "        \"id\": \"10303\",\n" +
        "        \"name\": \"Account Management\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"self\": \"https://brainbubble.atlassian.net/rest/api/2/component/10301\",\n" +
        "        \"id\": \"10301\",\n" +
        "        \"name\": \"User Management\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"comment\": {\n" +
        "      \"startAt\": 0,\n" +
        "      \"maxResults\": 1,\n" +
        "      \"total\": 1,\n" +
        "      \"comments\": [\n" +
        "        {\n" +
        "          \"self\": \"https://brainbubble.atlassian.net/rest/api/2/issue/10742/comment/10500\",\n" +
        "          \"id\": \"10500\",\n" +
        "          \"author\": {\n" +
        "            \"self\": \"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
        "            \"name\": \"joseph\",\n" +
        "            \"emailAddress\": \"joseph.b.mccarthy2012@googlemail.com\",\n" +
        "            \"avatarUrls\": {\n" +
        "              \"16x16\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
        "              \"24x24\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
        "              \"32x32\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\",\n" +
        "              \"48x48\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\"\n" +
        "            },\n" +
        "            \"displayName\": \"Joseph McCarthy\",\n" +
        "            \"active\": true\n" +
        "          },\n" +
        "          \"body\": \"&#116;&#104;&#105;&#115;&#32;&#105;&#115;&#32;&#110;&#111;&#116;&#32;&#114;&#101;&#97;&#108;&#108;&#121;&#32;&#97;&#115;&#115;&#105;&#103;&#110;&#101;&#100;&#32;&#116;&#111;&#32;&#109;&#101;&#44;&#32;&#106;&#117;&#115;&#116;&#32;&#116;&#101;&#115;&#116;&#105;&#110;&#103;&#32;&#111;&#117;&#116;&#32;&#116;&#104;&#101;&#32;&#105;&#110;&#116;&#101;&#108;&#108;&#105;&#106;&#32;&#45;&#32;&#106;&#105;&#114;&#97;&#32;&#112;&#108;&#117;&#103;&#105;&#110;&#32;&#58;&#41;\",\n" +
        "          \"updateAuthor\": {\n" +
        "            \"self\": \"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
        "            \"name\": \"joseph\",\n" +
        "            \"emailAddress\": \"joseph.b.mccarthy2012@googlemail.com\",\n" +
        "            \"avatarUrls\": {\n" +
        "              \"16x16\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
        "              \"24x24\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
        "              \"32x32\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\",\n" +
        "              \"48x48\": \"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\"\n" +
        "            },\n" +
        "            \"displayName\": \"Joseph McCarthy\",\n" +
        "            \"active\": true\n" +
        "          },\n" +
        "          \"created\": \"2013-10-09T22:14:54.979+0100\",\n" +
        "          \"updated\": \"2013-10-09T22:24:55.956+0100\"\n" +
        "        }\n" +
        "      ]\n" +
        "    },\n" +
        "    \"timeoriginalestimate\": null,\n" +
        "    \"aggregatetimespent\": null\n" +
        "  }\n" +
        "}");

        return jsonObject;
    }

    public static JSONObject getTestIssueWorklogs() {
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON("{\n" +
                "   \"startAt\":0,\n" +
                "   \"maxResults\":2,\n" +
                "   \"total\":2,\n" +
                "   \"worklogs\":[  \n" +
                "      {  \n" +
                "         \"self\":\"https://brainbubble.atlassian.net/rest/api/latest/issue/10742/worklog/45517\",\n" +
                "         \"author\":{  \n" +
                "            \"self\":\"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\", \n" +
                "            \"name\":\"joseph\",\n" +
                "            \"avatarUrls\":{  \n" +
                "               \"48x48\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\",\n" +
                "               \"24x24\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
                "               \"16x16\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
                "               \"32x32\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\"\n" +
                "            },\n" +
                "            \"displayName\":\"Joseph McCarthy\",\n" +
                "            \"active\":true\n" +
                "         },\n" +
                "         \"updateAuthor\":{  \n" +
                "            \"self\":\"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
                "            \"name\":\"joseph\",\n" +
                "            \"avatarUrls\":{  \n" +
                "               \"48x48\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\",\n" +
                "               \"24x24\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
                "               \"16x16\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
                "               \"32x32\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\"\n" +
                "            },\n" +
                "            \"displayName\":\"Joseph McCarthy\",\n" +
                "            \"active\":true\n" +
                "         },\n" +
                "         \"comment\":\"comment for worklog 1\",\n" +
                "         \"created\":\"2015-08-20T13:19:44.000+0400\",\n" +
                "         \"updated\":\"2015-08-20T13:19:44.000+0400\",\n" +
                "         \"started\":\"2015-08-17T13:19:00.000+0400\",\n" +
                "         \"timeSpent\":\"6h\",\n" +
                "         \"timeSpentSeconds\":21600,\n" +
                "         \"id\":\"45517\"\n" +
                "      },\n" +
                "      {  \n" +
                "         \"self\":\"https://brainbubble.atlassian.net/rest/api/latest/issue/10742/worklog/45518\",\n" +
                "         \"author\":{  \n" +
                "            \"self\":\"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
                "            \"name\":\"joseph\",\n" +
                "            \"avatarUrls\":{  \n" +
                "               \"48x48\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\",\n" +
                "               \"24x24\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
                "               \"16x16\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
                "               \"32x32\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\"\n" +
                "            },\n" +
                "            \"displayName\":\"Joseph McCarthy\",\n" +
                "            \"active\":true\n" +
                "         },\n" +
                "         \"updateAuthor\":{  \n" +
                "            \"self\":\"https://brainbubble.atlassian.net/rest/api/2/user?username=joseph\",\n" +
                "            \"name\":\"joseph\",\n" +
                "            \"avatarUrls\":{  \n" +
                "               \"48x48\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=48\",\n" +
                "               \"24x24\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=24\",\n" +
                "               \"16x16\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=16\",\n" +
                "               \"32x32\":\"https://secure.gravatar.com/avatar/a5a271f9eee8bbb3795f41f290274f8c?d=mm&s=32\"\n" +
                "            },\n" +
                "            \"displayName\":\"Joseph McCarthy\",\n" +
                "            \"active\":true\n" +
                "         },\n" +
                "         \"comment\":\"comment for worklog 2\",\n" +
                "         \"created\":\"2015-08-20T13:19:57.000+0400\",\n" +
                "         \"updated\":\"2015-08-20T13:19:57.000+0400\",\n" +
                "         \"started\":\"2015-08-18T13:19:00.000+0400\",\n" +
                "         \"timeSpent\":\"2h\",\n" +
                "         \"timeSpentSeconds\":7200,\n" +
                "         \"id\":\"45518\"\n" +
                "      }\n" +
                "   ]\n" +
                "}");
        return jsonObject;
    }

    public static JSONObject getTestProject() {
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON("{" +
                "    \"expand\": \"description,lead,url,projectKeys\"," +
                "    \"self\": \"http://www.example.com/jira/rest/api/2/project/EX\"," +
                "    \"id\": \"10000\"," +
                "    \"key\": \"EX\"," +
                "    \"description\": \"This project was created as an example for REST.\"," +
                "    \"lead\": {" +
                "        \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\"," +
                "        \"name\": \"fred\"," +
                "        \"avatarUrls\": {" +
                "            \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\"," +
                "            \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\"," +
                "            \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\"," +
                "            \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"" +
                "        }," +
                "        \"displayName\": \"Fred F. User\"," +
                "        \"active\": false" +
                "    }," +
                "    \"components\": [" +
                "        {" +
                "            \"self\": \"http://www.example.com/jira/rest/api/2/component/10000\"," +
                "            \"id\": \"10000\"," +
                "            \"name\": \"Component 1\"," +
                "            \"description\": \"This is a JIRA component\"," +
                "            \"lead\": {" +
                "                \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\"," +
                "                \"name\": \"fred\"," +
                "                \"avatarUrls\": {" +
                "                    \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\"," +
                "                    \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\"," +
                "                    \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\"," +
                "                    \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"" +
                "                }," +
                "                \"displayName\": \"Fred F. User\"," +
                "                \"active\": false" +
                "            }," +
                "            \"assigneeType\": \"PROJECT_LEAD\"," +
                "            \"assignee\": {" +
                "                \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\"," +
                "                \"name\": \"fred\"," +
                "                \"avatarUrls\": {" +
                "                    \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\"," +
                "                    \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\"," +
                "                    \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\"," +
                "                    \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"" +
                "                }," +
                "                \"displayName\": \"Fred F. User\"," +
                "                \"active\": false" +
                "            }," +
                "            \"realAssigneeType\": \"PROJECT_LEAD\"," +
                "            \"realAssignee\": {" +
                "                \"self\": \"http://www.example.com/jira/rest/api/2/user?username=fred\"," +
                "                \"name\": \"fred\"," +
                "                \"avatarUrls\": {" +
                "                    \"48x48\": \"http://www.example.com/jira/secure/useravatar?size=large&ownerId=fred\"," +
                "                    \"24x24\": \"http://www.example.com/jira/secure/useravatar?size=small&ownerId=fred\"," +
                "                    \"16x16\": \"http://www.example.com/jira/secure/useravatar?size=xsmall&ownerId=fred\"," +
                "                    \"32x32\": \"http://www.example.com/jira/secure/useravatar?size=medium&ownerId=fred\"" +
                "                }," +
                "                \"displayName\": \"Fred F. User\"," +
                "                \"active\": false" +
                "            }," +
                "            \"isAssigneeTypeValid\": false," +
                "            \"project\": \"HSP\"," +
                "            \"projectId\": 10000" +
                "        }" +
                "    ]," +
                "    \"issueTypes\": [" +
                "        {" +
                "            \"self\": \"http://localhost:8090/jira/rest/api/2.0/issueType/3\"," +
                "            \"id\": \"3\"," +
                "            \"description\": \"A task that needs to be done.\"," +
                "            \"iconUrl\": \"http://localhost:8090/jira/images/icons/issuetypes/task.png\"," +
                "            \"name\": \"Task\"," +
                "            \"subtask\": false," +
                "            \"avatarId\": 1" +
                "        }," +
                "        {" +
                "            \"self\": \"http://localhost:8090/jira/rest/api/2.0/issueType/1\"," +
                "            \"id\": \"1\"," +
                "            \"description\": \"A problem with the software.\"," +
                "            \"iconUrl\": \"http://localhost:8090/jira/images/icons/issuetypes/bug.png\"," +
                "            \"name\": \"Bug\"," +
                "            \"subtask\": false," +
                "            \"avatarId\": 10002" +
                "        }" +
                "    ]," +
                "    \"url\": \"http://www.example.com/jira/browse/EX\"," +
                "    \"email\": \"from-jira@example.com\"," +
                "    \"assigneeType\": \"PROJECT_LEAD\"," +
                "    \"versions\": []," +
                "    \"name\": \"Example\"," +
                "    \"roles\": {" +
                "        \"Developers\": \"http://www.example.com/jira/rest/api/2/project/EX/role/10000\"" +
                "    }," +
                "    \"avatarUrls\": {" +
                "        \"48x48\": \"http://www.example.com/jira/secure/projectavatar?size=large&pid=10000\"," +
                "        \"24x24\": \"http://www.example.com/jira/secure/projectavatar?size=small&pid=10000\"," +
                "        \"16x16\": \"http://www.example.com/jira/secure/projectavatar?size=xsmall&pid=10000\"," +
                "        \"32x32\": \"http://www.example.com/jira/secure/projectavatar?size=medium&pid=10000\"" +
                "    }," +
                "    \"projectCategory\": {" +
                "        \"self\": \"http://www.example.com/jira/rest/api/2/projectCategory/10000\"," +
                "        \"id\": \"10000\"," +
                "        \"name\": \"FIRST\"," +
                "        \"description\": \"First Project Category\"" +
                "    }" +
                "}");
        return jsonObject;
    }

}
