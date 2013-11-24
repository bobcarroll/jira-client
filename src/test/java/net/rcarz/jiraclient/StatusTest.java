package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;

public class StatusTest {

    private String statusID = "10004";
    private String description = "Issue is currently in progress.";
    private String iconURL = "https://site/images/icons/statuses/open.png";

    @Test
    public void testJSONDeserializer() throws IOException, URISyntaxException {
        Status status = new Status(new RestClient(null, new URI("/123/asd")), getTestJSON());
        assertEquals(status.getDescription(), description);
        assertEquals(status.getIconUrl(), iconURL);
        assertEquals(status.getName(), "Open");
        assertEquals(status.getId(), statusID);
    }

    private JSONObject getTestJSON() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("name", "Open");
        json.put("iconUrl", iconURL);
        json.put("id", statusID);

        return json;
    }

    @Test
    public void testStatusToString() throws URISyntaxException {
        Status status = new Status(new RestClient(null, new URI("/123/asd")), getTestJSON());
        assertEquals("Open",status.toString());
    }


}
