package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.Field

/**
 * Created on 2016-05-19.
 * @author pldupont
 */
interface JSONResources {

    long BOARD_ID = 84L
    String BOARD_SELF = "http://www.example.com/jira/rest/agile/1.0/board/${BOARD_ID}"
    String BOARD_NAME = "scrum board"
    String BOARD_TYPE = "scrum"
    String BOARD = """{
    "id": ${BOARD_ID},
    "self": "${BOARD_SELF}",
    "name": "${BOARD_NAME}",
    "type": "${BOARD_TYPE}"
}"""

    String LIST_OF_BOARDS = """{
    "maxResults": 2,
    "startAt": 1,
    "total": 2,
    "isLast": true,
    "values": [
        ${BOARD},
        {
            "id": 92,
            "self": "http://www.example.com/jira/rest/agile/1.0/board/92",
            "name": "kanban board",
            "type": "kanban"
        }
    ]
}"""

    long SPRINT_ID = 37L
    String SPRINT_NAME = "sprint 1"
    String SPRINT_SELF = "http://www.example.com/jira/rest/agile/1.0/sprint/${SPRINT_ID}"
    String SPRINT_STATE = "closed"
    long SPRINT_ORIGIN_BOARD_ID = BOARD_ID
    String SPRINT_START_DATE_STR = "2015-04-11T15:22:00.000+10:00"
    Date SPRINT_START_DATE = Field.getDateTime(SPRINT_START_DATE_STR)
    String SPRINT_END_DATE_STR = "2015-04-20T01:22:00.000+10:00"
    Date SPRINT_END_DATE = Field.getDateTime(SPRINT_END_DATE_STR)
    String SPRINT_COMPLETE_DATE_STR = "2015-04-20T11:04:00.000+10:00"
    Date SPRINT_COMPLETE_DATE = Field.getDateTime(SPRINT_COMPLETE_DATE_STR)
    String SPRINT = """{
    "id": ${SPRINT_ID},
    "self": "${SPRINT_SELF}",
    "state": "${SPRINT_STATE}",
    "name": "${SPRINT_NAME}",
    "startDate": "${SPRINT_START_DATE_STR}",
    "endDate": "${SPRINT_END_DATE_STR}",
    "completeDate": "${SPRINT_COMPLETE_DATE_STR}",
    "originBoardId": ${BOARD_ID}
}"""

    String LIST_OF_SPRINTS = """{
    "maxResults": 2,
    "startAt": 1,
    "total": 2,
    "isLast": true,
    "values": [
        ${SPRINT},
        {
            "id": 72,
            "self": "http://www.example.com/jira/rest/agile/1.0/sprint/73",
            "state": "future",
            "name": "sprint 2"
        }
    ]
}"""

    long EPIC_ID = 23
    String EPIC_SELF = "http://www.example.com/jira/rest/agile/1.0/epic/${EPIC_ID}"
    String EPIC_NAME = "epic 1"
    String EPIC_KEY = "EX"
    String EPIC_SUMMARY = "epic 1 summary"
    boolean EPIC_DONE = true
    String EPIC = """{
    "id": ${EPIC_ID},
    "self": "${EPIC_SELF}",
    "name": "${EPIC_NAME}",
    "key": "${EPIC_KEY}",
    "summary": "${EPIC_SUMMARY}",
    "color": {
        "key": "color_4"
    },
    "done": ${EPIC_DONE}
}"""
    String LIST_OF_EPICS = """{
    "maxResults": 2,
    "startAt": 1,
    "total": 5,
    "isLast": false,
    "values": [
        ${EPIC},
        {
            "id": 37,
            "self": "http://www.example.com/jira/rest/agile/1.0/epic/13",
            "name": "epic 2",
            "summary": "epic 2 summary",
            "color": {
                "key": "color_2"
            },
            "done": false
        }
    ]
}"""

    long PROJECT_ID = 10000L
    String PROJECT_KEY = "EX"
    String PROJECT_NAME = "Example"
    String PROJECT_SELF = "http://www.example.com/jira/rest/api/2/project/${PROJECT_KEY}"
    String PROJECT = """{
            "self": "${PROJECT_SELF}",
            "id": "${PROJECT_ID}",
            "key": "${PROJECT_KEY}",
            "name": "${PROJECT_NAME}",
            "avatarUrls": {
                "48x48": "http://www.example.com/jira/secure/projectavatar?size=large&pid=10000",
                "24x24": "http://www.example.com/jira/secure/projectavatar?size=small&pid=10000",
                "16x16": "http://www.example.com/jira/secure/projectavatar?size=xsmall&pid=10000",
                "32x32": "http://www.example.com/jira/secure/projectavatar?size=medium&pid=10000"
            },
            "projectCategory": {
                "self": "http://www.example.com/jira/rest/api/2/projectCategory/10000",
                "id": "10000",
                "name": "FIRST",
                "description": "First Project Category"
            }
        }"""

    String USER_NAME = "Example"
    String USER_SELF = "https://www.example.com/rest/api/2/user?username=${USER_NAME}"
    String USER_EMAIL_ADDRESS = "pldupont@example.com"
    String USER_DISPLAY_NAME = "Pierre-Luc Dupont"
    boolean USER_ACTIVE = true
    String USER_TIME_ZONE = "America/New_York"

    String USER = """{
			"self" : "${USER_SELF}",
			"name" : "${USER_NAME}",
			"key" : "pldupont",
			"emailAddress" : "${USER_EMAIL_ADDRESS}",
			"avatarUrls" : {
				"48x48" : "https://www.example.com/secure/useravatar?ownerId=pldupont&avatarId=11828",
				"24x24" : "https://www.example.com/secure/useravatar?size=small&ownerId=pldupont&avatarId=11828",
				"16x16" : "https://www.example.com/secure/useravatar?size=xsmall&ownerId=pldupont&avatarId=11828",
				"32x32" : "https://www.example.com/secure/useravatar?size=medium&ownerId=pldupont&avatarId=11828"
			},
			"displayName" : "${USER_DISPLAY_NAME}",
			"active" : ${USER_ACTIVE},
			"timeZone" : "${USER_TIME_ZONE}"
		}"""

    String ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE = "10m"
    String ISSUE_TIMETRACKING_REMAINING_ESTIMATE = "3m"
    String ISSUE_TIMETRACKING_TIME_SPENT = "6m"
    long ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE_SECONDS = 600L
    long ISSUE_TIMETRACKING_REMAINING_ESTIMATE_SECONDS = 200L
    long ISSUE_TIMETRACKING_TIME_SPENT_SECONDS = 400L
    String ISSUE_TIMETRACKING = """{
            "originalEstimate": "${ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE}",
            "remainingEstimate": "${ISSUE_TIMETRACKING_REMAINING_ESTIMATE}",
            "timeSpent": "${ISSUE_TIMETRACKING_TIME_SPENT}",
            "originalEstimateSeconds": ${ISSUE_TIMETRACKING_ORIGINAL_ESTIMATE_SECONDS},
            "remainingEstimateSeconds": ${ISSUE_TIMETRACKING_REMAINING_ESTIMATE_SECONDS},
            "timeSpentSeconds": ${ISSUE_TIMETRACKING_TIME_SPENT_SECONDS}
        }"""

    long ISSUE_WORKLOG_ID = 100028L
    String ISSUE_WORKLOG_SELF = "http://www.example.com/jira/rest/api/2/issue/10010/worklog${ISSUE_WORKLOG_ID}"
    String ISSUE_WORKLOG_COMMENT = "I did some work here."
    String ISSUE_WORKLOG_CREATED_STR = "2016-03-21T15:25:17.882+0100"
    Date ISSUE_WORKLOG_CREATED = Field.getDateTime(ISSUE_WORKLOG_CREATED_STR)
    String ISSUE_WORKLOG_UPDATED_STR = "2016-03-21T15:26:17.882+0100"
    Date ISSUE_WORKLOG_UPDATED = Field.getDateTime(ISSUE_WORKLOG_UPDATED_STR)
    String ISSUE_WORKLOG_STARTED_STR = "2016-03-21T15:26:17.881+0100"
    Date ISSUE_WORKLOG_STARTED = Field.getDateTime(ISSUE_WORKLOG_STARTED_STR)
    String ISSUE_WORKLOG_TIMESPEND = "3h 20m"
    long ISSUE_WORKLOG_TIMESPEND_SECONDS = 12000
    String ISSUE_WORKLOG = """{
                "self": "${ISSUE_WORKLOG_SELF}",
                "author": ${USER},
                "updateAuthor": ${USER},
                "comment": "${ISSUE_WORKLOG_COMMENT}",
                "created": "${ISSUE_WORKLOG_CREATED_STR}",
                "updated": "${ISSUE_WORKLOG_UPDATED_STR}",
                "visibility": {
                    "type": "group",
                    "value": "jira-developers"
                },
                "started": "${ISSUE_WORKLOG_STARTED_STR}",
                "timeSpent": "${ISSUE_WORKLOG_TIMESPEND}",
                "timeSpentSeconds": ${ISSUE_WORKLOG_TIMESPEND_SECONDS},
                "id": "${ISSUE_WORKLOG_ID}",
                "issueId": "10002"
            }"""

    long ISSUE_COMMENT_ID = 9999L
    String ISSUE_COMMENT_SELF = "http://www.example.com/jira/rest/api/2/issue/10010/comment/${ISSUE_COMMENT_ID}"
    String ISSUE_COMMENT_BODY = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper."
    String ISSUE_COMMENT_CREATED_STR = "2016-05-11T10:58:01.000-0400"
    Date ISSUE_COMMENT_CREATED = Field.getDateTime(ISSUE_COMMENT_CREATED_STR)
    String ISSUE_COMMENT_UPDATED_STR = "2016-05-30T14:20:29.000-0400"
    Date ISSUE_COMMENT_UPDATED = Field.getDateTime(ISSUE_COMMENT_UPDATED_STR)
    String ISSUE_COMMENT = """{
                "self": "${ISSUE_COMMENT_SELF}",
                "id": "${ISSUE_COMMENT_ID}",
                "author": ${USER},
                "body": "${ISSUE_COMMENT_BODY}",
                "updateAuthor": ${USER},
                "created": "${ISSUE_COMMENT_CREATED_STR}",
                "updated": "${ISSUE_COMMENT_UPDATED_STR}",
                "visibility": {
                    "type": "role",
                    "value": "Administrators"
                }
            }"""
    long ISSUE_TYPE_ID = 1L
    String ISSUE_TYPE_NAME = "Bug"
    String ISSUE_TYPE_SELF = "https://jira.acquisio.com/rest/api/2/issuetype/${ISSUE_TYPE_ID}"
    String ISSUE_TYPE_DESCRIPTION = "A problem which impairs or prevents the functions of the product."
    boolean ISSUE_TYPE_SUB_TASK = true
    String ISSUE_TYPE = """{
			"self" : "${ISSUE_TYPE_SELF}",
			"id" : "${ISSUE_TYPE_ID}",
			"description" : "${ISSUE_TYPE_DESCRIPTION}",
			"iconUrl" : "https://www.example.com/images/icons/issuetypes/bug.png",
			"name" : "${ISSUE_TYPE_NAME}",
			"subtask" : ${ISSUE_TYPE_SUB_TASK}
		}"""
    long ISSUE_RESOLUTION_ID = 6L
    String ISSUE_RESOLUTION_NAME = "Not a bug"
    String ISSUE_RESOLUTION_SELF = "https://jira.acquisio.com/rest/api/2/resolution/${ISSUE_RESOLUTION_ID}"
    String ISSUE_RESOLUTION_DESCRIPTION = "The problem is not a problem"
    String ISSUE_RESOLUTION = """{
			"self" : "${ISSUE_RESOLUTION_SELF}",
			"id" : "${ISSUE_RESOLUTION_ID}",
			"description" : "${ISSUE_RESOLUTION_DESCRIPTION}",
			"name" : "${ISSUE_RESOLUTION_NAME}"
		}"""

    long ISSUE_STATUS_ID = 6L
    String ISSUE_STATUS_NAME = "Closed"
    String ISSUE_STATUS_DESCRIPTION = "The issue is considered finished, the resolution is correct. Issues which are closed can be reopened."
    String ISSUE_STATUS_SELF = "https://www.example.com/rest/api/2/status/${ISSUE_STATUS_ID}"
    String ISSUE_STATUS = """{
			"self" : "${ISSUE_STATUS_SELF}",
			"description" : "${ISSUE_STATUS_DESCRIPTION}",
			"iconUrl" : "https://www.example.com/images/icons/statuses/closed.png",
			"name" : "${ISSUE_STATUS_NAME}",
			"id" : "${ISSUE_STATUS_ID}",
			"statusCategory" : {
				"self" : "https://www.example.com/rest/api/2/statuscategory/3",
				"id" : 3,
				"key" : "done",
				"colorName" : "green",
				"name" : "Done"
			}
		}"""
    long ISSUE_PRIORITY_ID = 2L
    String ISSUE_PRIORITY_NAME = "Critical"
    String ISSUE_PRIORITY_SELF = "https://www.example.com/rest/api/2/priority/${ISSUE_PRIORITY_ID}"
    String ISSUE_PRIORITY = """{
			"self" : "${ISSUE_PRIORITY_SELF}",
			"iconUrl" : "https://www.example.com/images/icons/priorities/critical.png",
			"name" : "${ISSUE_PRIORITY_NAME}",
			"id" : "${ISSUE_PRIORITY_ID}"
		}"""
    long ISSUE_ID = 10001L
    String ISSUE_SELF = "http://www.example.com/jira/rest/agile/1.0/board/92/issue/10001"
    String ISSUE_KEY = "HSP-1"
    String ISSUE_SUMMARY = "Issue summary"
    boolean ISSUE_FLAGGED = true
    String ISSUE_DESCRIPTION = "example bug report"
    String ISSUE_ENVIRONMENT = "PROD"
    String ISSUE_CREATED_STR = "2016-05-11T10:58:01.000-0400"
    Date ISSUE_CREATED = Field.getDateTime(ISSUE_CREATED_STR)
    String ISSUE_UPDATED_STR = "2016-05-30T14:20:29.000-0400"
    Date ISSUE_UPDATED = Field.getDateTime(ISSUE_UPDATED_STR)
    String ISSUE = """{
    "expand": "",
    "id": "${ISSUE_ID}",
    "self": "${ISSUE_SELF}",
    "key": "${ISSUE_KEY}",
    "fields": {
		"flagged": ${ISSUE_FLAGGED},
        "sprint": ${SPRINT},
        "summary": "${ISSUE_SUMMARY}",
        "closedSprint": {
            "closedSprints": [
                {
                    "id": 21,
                    "self": "http://www.example.com/jira/rest/agile/1.0/sprint/21",
                    "state": "closed",
                    "name": "sprint 1",
                    "startDate": "2015-04-11T15:22:00.000+10:00",
                    "endDate": "2015-04-20T01:22:00.000+10:00",
                    "completeDate": "2015-04-20T11:04:00.000+10:00"
                },
                {
                    "id": 22,
                    "self": "http://www.example.com/jira/rest/agile/1.0/sprint/22",
                    "state": "closed",
                    "name": "sprint 1",
                    "startDate": "2015-04-11T15:22:00.000+10:00",
                    "endDate": "2015-04-20T01:22:00.000+10:00",
                    "completeDate": "2015-04-20T11:04:00.000+10:00"
                },
                {
                    "id": 23,
                    "self": "http://www.example.com/jira/rest/agile/1.0/sprint/23",
                    "state": "closed",
                    "name": "sprint 1",
                    "startDate": "2015-04-11T15:22:00.000+10:00",
                    "endDate": "2015-04-20T01:22:00.000+10:00",
                    "completeDate": "2015-04-20T11:04:00.000+10:00"
                }
            ]
        },
        "description": "${ISSUE_DESCRIPTION}",
        "project": ${PROJECT},
        "comment": {
            "comments" : [
                ${ISSUE_COMMENT}
            ]
        },
        "epic": ${EPIC},
        "worklog": {
            "worklogs": [
                ${ISSUE_WORKLOG}
            ]
        },
        "timetracking": ${ISSUE_TIMETRACKING},
        "environment": "${ISSUE_ENVIRONMENT}",
		"issuetype" : ${ISSUE_TYPE},
		"resolution" : ${ISSUE_RESOLUTION},
		"assignee" : ${USER},
		"creator" : ${USER},
		"reporter" : ${USER},
		"created" : "${ISSUE_CREATED_STR}",
		"updated" : "${ISSUE_UPDATED_STR}",
		"status" : ${ISSUE_STATUS},
		"priority" : ${ISSUE_PRIORITY},
    }
}"""

    long BLANK_ISSUE1_ID = 10010
    String BLANK_ISSUE1_SELF = "http://www.example.com/jira/rest/agile/1.0/board/92/issue/${BLANK_ISSUE1_ID}"
    String BLANK_ISSUE1_KEY = "HSP-1"
    String BLANK_ISSUE1 = """{
    "expand": "",
    "id": "${BLANK_ISSUE1_ID}",
    "self": "${BLANK_ISSUE1_SELF}",
    "key": "${BLANK_ISSUE1_KEY}",
}"""

    long BLANK_ISSUE2_ID = 10011
    String BLANK_ISSUE2_SELF = "http://www.example.com/jira/rest/agile/1.0/board/92/issue/${BLANK_ISSUE2_ID}"
    String BLANK_ISSUE2_KEY = "HSP-1"
    String BLANK_ISSUE2 = """{
    "expand": "",
    "id": "${BLANK_ISSUE2_ID}",
    "self": "${BLANK_ISSUE2_SELF}",
    "key": "${BLANK_ISSUE2_KEY}",
    "fields": {
        "flagged": false,
    }
}"""

    String LIST_OF_ISSUES = """{
    "expand": "names,schema",
    "startAt": 0,
    "maxResults": 50,
    "total": 1,
    "issues": [
        ${ISSUE},
        ${BLANK_ISSUE1},
        ${BLANK_ISSUE2},
        {
            "expand": "",
            "id": "10005",
            "self": "http://www.example.com/jira/rest/agile/1.0/board/92/issue/10005",
            "key": "HSP-9",
            "fields": {
                "flagged": true,
                "sprint": ${SPRINT},
                "closedSprint" : {
                    "closedSprints": [
                        {
                            "id": 37,
                            "self": "http://www.example.com/jira/rest/agile/1.0/sprint/23",
                            "state": "closed",
                            "name": "sprint 1",
                            "startDate": "2015-04-11T15:22:00.000+10:00",
                            "endDate": "2015-04-20T01:22:00.000+10:00",
                            "completeDate": "2015-04-20T11:04:00.000+10:00"
                        }
                    ]
                },
                "description": "example bug report",
                "project": {
                    "self": "http://www.example.com/jira/rest/api/2/project/EX",
                    "id": "10000",
                    "key": "EX",
                    "name": "Example",
                    "avatarUrls": {
                        "48x48": "http://www.example.com/jira/secure/projectavatar?size=large&pid=10000",
                        "24x24": "http://www.example.com/jira/secure/projectavatar?size=small&pid=10000",
                        "16x16": "http://www.example.com/jira/secure/projectavatar?size=xsmall&pid=10000",
                        "32x32": "http://www.example.com/jira/secure/projectavatar?size=medium&pid=10000"
                    },
                    "projectCategory": {
                        "self": "http://www.example.com/jira/rest/api/2/projectCategory/10000",
                        "id": "10000",
                        "name": "FIRST",
                        "description": "First Project Category"
                    }
                },
                "comment": {
                    "comments": [
                        {
                            "self": "http://www.example.com/jira/rest/api/2/issue/10010/comment/10000",
                            "id": "10000",
                            "author": {
                                "self": "http://www.example.com/jira/rest/api/2/user?username=fred",
                                "name": "fred",
                                "displayName": "Fred F. User",
                                "active": false
                            },
                            "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.",
                            "updateAuthor": {
                                "self": "http://www.example.com/jira/rest/api/2/user?username=fred",
                                "name": "fred",
                                "displayName": "Fred F. User",
                                "active": false
                            },
                            "created": "2016-03-21T15:26:17.875+0100",
                            "updated": "2016-03-21T15:26:17.878+0100",
                            "visibility": {
                                "type": "role",
                                "value": "Administrators"
                            }
                        }
                    ]
                },
                "epic": ${EPIC},
                "worklog": {
                    "worklogs": [
                        {
                            "self": "http://www.example.com/jira/rest/api/2/issue/10010/worklog/10000",
                            "author": {
                                "self": "http://www.example.com/jira/rest/api/2/user?username=fred",
                                "name": "fred",
                                "displayName": "Fred F. User",
                                "active": false
                            },
                            "updateAuthor": {
                                "self": "http://www.example.com/jira/rest/api/2/user?username=fred",
                                "name": "fred",
                                "displayName": "Fred F. User",
                                "active": false
                            },
                            "comment": "I did some work here.",
                            "updated": "2016-03-21T15:26:17.882+0100",
                            "visibility": {
                                "type": "group",
                                "value": "jira-developers"
                            },
                            "started": "2016-03-21T15:26:17.881+0100",
                            "timeSpent": "3h 20m",
                            "timeSpentSeconds": 12000,
                            "id": "100028",
                            "issueId": "10002"
                        }
                    ]
                },
                "updated": 1,
                "timetracking": {
                    "originalEstimate": "10m",
                    "remainingEstimate": "3m",
                    "timeSpent": "6m",
                    "originalEstimateSeconds": 600,
                    "remainingEstimateSeconds": 200,
                    "timeSpentSeconds": 400
                }
            }
        }
    ]
}"""
}
