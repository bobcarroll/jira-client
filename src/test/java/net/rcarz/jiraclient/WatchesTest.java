package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

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
        return jsonObject;
    }
}
