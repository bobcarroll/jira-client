package net.rcarz.jiraclient.agile

import net.rcarz.jiraclient.Field

/**
 * Created by pldupont on 2016-05-19.
 */
interface JSONResources {

    String BOARD_84 = """{
    "id": 84,
    "self": "http://www.example.com/jira/rest/agile/1.0/board/84",
    "name": "scrum board",
    "type": "scrum"
}"""
    String BOARD_84_SELF = "http://www.example.com/jira/rest/agile/1.0/board/84"
    String BOARD_84_NAME = "scrum board"
    String BOARD_84_TYPE = "scrum"
    int BOARD_84_ID = 84

    String LIST_OF_BOARDS = """{
    "maxResults": 2,
    "startAt": 1,
    "total": 5,
    "isLast": false,
    "values": [
        ${BOARD_84},
        {
            "id": 92,
            "self": "http://www.example.com/jira/rest/agile/1.0/board/92",
            "name": "kanban board",
            "type": "kanban"
        }
    ]
}"""

    String SPRINT_37 = """{
    "id": 37,
    "self": "http://www.example.com/jira/rest/agile/1.0/sprint/23",
    "state": "closed",
    "name": "sprint 1",
    "startDate": "2015-04-11T15:22:00.000+10:00",
    "endDate": "2015-04-20T01:22:00.000+10:00",
    "completeDate": "2015-04-20T11:04:00.000+10:00",
    "originBoardId": 84
}"""
    int SPRINT_37_ID = 37
    String SPRINT_37_NAME = "sprint 1"
    String SPRINT_37_SELF = "http://www.example.com/jira/rest/agile/1.0/sprint/23"
    String SPRINT_37_STATE = "closed"
    int SPRINT_37_ORIGIN_BOARD_ID = 84
    Date SPRINT_37_START_DATE = Field.getDateTime("2015-04-11T15:22:00.000+10:00")
    Date SPRINT_37_END_DATE = Field.getDateTime("2015-04-20T01:22:00.000+10:00")
    Date SPRINT_37_COMPLETE_DATE = Field.getDateTime("2015-04-20T11:04:00.000+10:00")

    String LIST_OF_SPRINTS = """{
    "maxResults": 2,
    "startAt": 1,
    "total": 5,
    "isLast": false,
    "values": [
        ${SPRINT_37},
        {
            "id": 72,
            "self": "http://www.example.com/jira/rest/agile/1.0/sprint/73",
            "state": "future",
            "name": "sprint 2"
        }
    ]
}"""
}
