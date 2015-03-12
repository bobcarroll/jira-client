package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class VotesTest {

    @Test
    public void testVotesInit(){
        Votes votes = new Votes(null,null);
    }

    @Test
    public void testVotesJSON(){
        Votes votes = new Votes(null,getTestJSON());

        assertFalse(votes.hasVoted());
        assertEquals(votes.getId(),"10");
        assertEquals(votes.getVotes(),0);
        assertEquals(votes.getSelf(),"https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/votes");
    }

    @Test
    public void testGetToString(){
        Votes votes = new Votes(null,getTestJSON());

        assertEquals(votes.toString(),"0");
    }

    private JSONObject getTestJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("self","https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/votes");
        jsonObject.put("votes",0);
        jsonObject.put("hasVoted",false);
        jsonObject.put("id","10");
        return jsonObject;
    }
}


/**
 "votes": {
 "self": "https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/votes",
 "votes": 0,
 "hasVoted": false
 },
 **/
