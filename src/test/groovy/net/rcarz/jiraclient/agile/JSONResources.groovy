package net.rcarz.jiraclient.agile

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
}
