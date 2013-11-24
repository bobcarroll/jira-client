package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class VersionTest {

    @Test
    public void testVersionInit(){
        Version version = new Version(null,null);
    }

    @Test
    public void testVersionJSON(){
        Version version = new Version(null,getTestJSON());

        assertEquals(version.getId(),"10200");
        assertEquals(version.getName(),"1.0");
        assertFalse(version.isArchived());
        assertFalse(version.isReleased());
        assertEquals(version.getReleaseDate(),"2013-12-01");
        assertEquals(version.getDescription(),"First Full Functional Build");
    }

    @Test
    public void testVersionToString(){
        Version version = new Version(null,getTestJSON());
        assertEquals(version.toString(),"1.0");
    }

    private JSONObject getTestJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id","10200");
        jsonObject.put("description","First Full Functional Build");
        jsonObject.put("name","1.0");
        jsonObject.put("archived",false);
        jsonObject.put("released",false);
        jsonObject.put("releaseDate","2013-12-01");
        return jsonObject;
    }
}

/**
 "fixVersions": [
 {
 "self": "https://brainbubble.atlassian.net/rest/api/2/version/10200",
 "id": "10200",
 "description": "First Full Functional Build",
 "name": "1.0",
 "archived": false,
 "released": false,
 "releaseDate": "2013-12-01"
 }
 ],
 **/
