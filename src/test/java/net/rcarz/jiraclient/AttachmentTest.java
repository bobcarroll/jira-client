package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttachmentTest {

    @Test
    public void testId() {
        assertEquals("99248", attachment().getId());
    }

    @Test
    public void testName() {
        assertEquals("foobar.log", attachment().getFileName());
    }

    @Test
    public void testSize() {
        assertEquals(53, attachment().getSize());
    }

    @Test
    public void testMimeType() {
        assertEquals("text/plain", attachment().getMimeType());
    }

    @Test
    public void testContentUrl() {
        assertEquals("http://localhost:8080/secure/attachment/99248/foobar.log", attachment().getContentUrl());
    }

    @Test
    public void testAuthor() {
        assertEquals("admin", attachment().getAuthor().getName());
    }

    private Attachment attachment() {
        return new Attachment(null, getTestJson());
    }

    private JSONObject getTestJson() {
        return (JSONObject) JSONSerializer.toJSON(
                "{\n" +
                    "\"self\": \"http://localhost:8080/rest/api/2/attachment/99248\",\n" +
                    "\"filename\": \"foobar.log\",\n" +
                    "\"author\": {\n" +
                        "\"self\": \"http://localhost:8080/rest/api/2/user?username=admin\",\n" +
                        "\"key\": \"admin\",\n" +
                        "\"name\": \"admin\",\n" +
                        "\"avatarUrls\": {\n" +
                            "\"48x48\": \"http://localhost:8080/secure/useravatar?ownerId=admin&avatarId=18600\",\n" +
                            "\"24x24\": \"http://localhost:8080/secure/useravatar?size=small&ownerId=admin&avatarId=18600\",\n" +
                            "\"16x16\": \"http://localhost:8080/secure/useravatar?size=xsmall&ownerId=admin&avatarId=18600\",\n" +
                            "\"32x32\": \"http://localhost:8080/secure/useravatar?size=medium&ownerId=admin&avatarId=18600\"\n" +
                            "},\n" +
                        "\"displayName\": \"admin\",\n" +
                        "\"active\": true\n" +
                    "},\n" +
                    "\"created\": \"2021-03-12T16:35:48.287+0100\",\n" +
                    "\"size\": 53,\n" +
                    "\"mimeType\": \"text/plain\",\n" +
                    "\"properties\": { },\n" +
                    "\"content\": \"http://localhost:8080/secure/attachment/99248/foobar.log\"\n" +
                "}");
    }
}
