package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;

public class WatchesTest {

    @Test
    public void testWatchesInit() {
        Watches watches = new Watches(null, null);
    }

    @Test
    public void testWatchesJSON() {
        Watches watches = new Watches(null, getTestJSON());

        assertFalse(watches.isWatching());
        assertEquals(watches.getWatchCount(), 0);
        assertEquals(watches.getId(), "10");
        assertEquals(watches.getSelf(), "https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/watchers");
        assertEquals(watches.getWatchers(), new ArrayList<User>());
    }

    @Test(expected = JiraException.class)
    public void testGetWatchersNullReturned() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(restClient.get(anyString())).thenReturn(null);
        Watches.get(restClient, "someID");
    }

    @Test(expected = JiraException.class)
    public void testGetWatchersGetThrows() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(restClient.get(anyString())).thenThrow(Exception.class);
        Watches.get(restClient, "someID");
    }

    @Test
    public void testGetWatchers() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(restClient.get(anyString())).thenReturn(getTestJSON());
        final Watches watches = Watches.get(restClient, "someID");

        assertFalse(watches.isWatching());
        assertEquals(watches.getWatchCount(), 0);
        assertEquals(watches.getId(), "10");
        assertEquals(watches.getSelf(), "https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/watchers");
    }

    @Test
    public void testWatchesToString() {
        Watches watches = new Watches(null, getTestJSON());
        assertEquals(watches.toString(), "0");
    }

    private JSONObject getTestJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", "10");
        jsonObject.put("self", "https://brainbubble.atlassian.net/rest/api/2/issue/FILTA-43/watchers");
        jsonObject.put("watchCount", 0);
        jsonObject.put("isWatching", false);
        jsonObject.put("watchers", new ArrayList<User>());
        return jsonObject;
    }
}
