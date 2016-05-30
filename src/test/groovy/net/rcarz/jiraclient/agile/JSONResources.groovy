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
    Date SPRINT_START_DATE = Field.getDateTime("2015-04-11T15:22:00.000+10:00")
    Date SPRINT_END_DATE = Field.getDateTime("2015-04-20T01:22:00.000+10:00")
    Date SPRINT_COMPLETE_DATE = Field.getDateTime("2015-04-20T11:04:00.000+10:00")
    String SPRINT = """{
    "id": ${SPRINT_ID},
    "self": "${SPRINT_SELF}",
    "state": "${SPRINT_STATE}",
    "name": "${SPRINT_NAME}",
    "startDate": "${SPRINT_START_DATE}",
    "endDate": "${SPRINT_END_DATE}",
    "completeDate": "${SPRINT_COMPLETE_DATE}",
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
    String EPIC_SUMMARY = "epic 1 summary"
    boolean EPIC_DONE = true
    String EPIC = """{
    "id": ${EPIC_ID},
    "self": "${EPIC_SELF}",
    "name": "${EPIC_NAME}",
    "summary": "${EPIC_SUMMARY}",
    "color": {
        "key": "color_4"
    },
    "done": ${EPIC_DONE}
}"""

    long ISSUE_ID = 10001L
    String ISSUE_SELF = "http://www.example.com/jira/rest/agile/1.0/board/92/issue/10001"
    String ISSUE_KEY = "HSP-1"
    String ISSUE = """{
    "expand": "",
    "id": "${ISSUE_ID}",
    "self": "${ISSUE_SELF}",
    "key": "${ISSUE_KEY}",
    "fields": {
        "flagged": true,
        "sprint": {
            "id": ${SPRINT_ID},
            "self": "http://www.example.com/jira/rest/agile/1.0/sprint/${SPRINT_ID}",
            "state": "future",
            "name": "sprint 2"
        },
        "closedSprints": [
            {
                "id": 23,
                "self": "http://www.example.com/jira/rest/agile/1.0/sprint/23",
                "state": "closed",
                "name": "sprint 1",
                "startDate": "2015-04-11T15:22:00.000+10:00",
                "endDate": "2015-04-20T01:22:00.000+10:00",
                "completeDate": "2015-04-20T11:04:00.000+10:00"
            }
        ],
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
        "comment": [
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
        ],
        "epic": {
            "id": 37,
            "self": "http://www.example.com/jira/rest/agile/1.0/epic/23",
            "name": "epic 1",
            "summary": "epic 1 summary",
            "color": {
                "key": "color_4"
            },
            "done": true
        },
        "worklog": [
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
        ],
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
}"""
}
