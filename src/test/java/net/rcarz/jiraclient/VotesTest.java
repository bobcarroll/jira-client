package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
public class VotesTest {

    @Test
    public void testVotesInit(){
        new Votes(null,null);
    }

    @Test
    public void testVoteMap() throws Exception {
        final JSONObject json = new JSONObject();
        json.put("self","someURL");
        json.put("id","1111");
        json.put("votes",12);
        json.put("hasVoted",true);
        Votes votes = new Votes(null, json);

        assertTrue(votes.hasVoted());
        assertEquals("1111",votes.getId());
        assertEquals(12,votes.getVotes());
        assertEquals("someURL",votes.getSelf());

    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromRestException() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(mockRestClient.get(anyString())).thenThrow(RestException.class);
        Votes.get(mockRestClient, "issueNumber");
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        Votes.get(mockRestClient,"issueNumber");
    }

    @Test
    public void testGetVotesFromID() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject returnedFromService = new JSONObject();

        returnedFromService.put("self", "someURL");
        returnedFromService.put("id", "1111");
        returnedFromService.put("votes", 12);
        returnedFromService.put("hasVoted", true);

        PowerMockito.when(mockRestClient.get(anyString())).thenReturn(returnedFromService);

        final Votes votes = Votes.get(mockRestClient, "issueNumber");

        assertTrue(votes.hasVoted());
        assertEquals("1111",votes.getId());
        assertEquals(12,votes.getVotes());
        assertEquals("someURL",votes.getSelf());

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
        final JSONObject json = new JSONObject();
        json.put("self","someURL");
        json.put("id","1111");
        json.put("votes",12);
        json.put("hasVoted",true);
        Votes votes = new Votes(null, json);

        assertEquals(votes.toString(),"12");
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
