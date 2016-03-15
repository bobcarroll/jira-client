package net.rcarz.jiraclient;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JSONObject.class)
public class VersionTest {

    @Test
    public void testVersionInit() {
        new Version(null, null);
    }

    @Test
    public void testVersionJSON() {
        Version version = new Version(null, getTestJSON());

        assertEquals(version.getId(), "10200");
        assertEquals(version.getName(), "1.0");
        assertFalse(version.isArchived());
        assertFalse(version.isReleased());
        assertEquals(version.getReleaseDate(), "2013-12-01");
        assertEquals(version.getDescription(), "First Full Functional Build");
    }

    @Test
    public void testGetVersion() throws Exception {
        final RestClient restClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(restClient.get(anyString())).thenReturn(getTestJSON());
        Version version = Version.get(restClient, "id");

        assertEquals(version.getId(), "10200");
        assertEquals(version.getName(), "1.0");
        assertFalse(version.isArchived());
        assertFalse(version.isReleased());
        assertEquals(version.getReleaseDate(), "2013-12-01");
        assertEquals(version.getDescription(), "First Full Functional Build");
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromRestException() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        PowerMockito.when(mockRestClient.get(anyString())).thenThrow(RestException.class);
        Version.get(mockRestClient, "id");
    }

    @Test(expected = JiraException.class)
    public void testJiraExceptionFromNonJSON() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        Version.get(mockRestClient, "id");
    }

    private JSONObject getTestJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", "10200");
        jsonObject.put("description", "First Full Functional Build");
        jsonObject.put("name", "1.0");
        jsonObject.put("archived", false);
        jsonObject.put("released", false);
        jsonObject.put("releaseDate", "2013-12-01");
        return jsonObject;
    }

    @Test
    public void testMergeWith() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject mockJSON = PowerMockito.mock(JSONObject.class);
        Version version = new Version(mockRestClient,mockJSON);
        version.mergeWith(new Version(mockRestClient,mockJSON));
        verify(mockRestClient, times(1)).put(anyString(), any(JSONObject.class));
    }

    @Test(expected = JiraException.class)
    public void testMergeWithFailed() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject mockJSON = PowerMockito.mock(JSONObject.class);
        when(mockRestClient.put(anyString(),any(JSONObject.class))).thenThrow(Exception.class);
        Version version = new Version(mockRestClient,mockJSON);
        version.mergeWith(new Version(mockRestClient,mockJSON));
    }

    @Test
    public void testCopyTo() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject mockJSON = PowerMockito.mock(JSONObject.class);
        Version version = new Version(mockRestClient,getTestJSON());
        version.copyTo(new Project(mockRestClient,mockJSON));
        verify(mockRestClient, times(1)).post(anyString(),any(JSONObject.class));
    }

    @Test(expected = JiraException.class)
    public void testCopyToFailed() throws Exception {
        final RestClient mockRestClient = PowerMockito.mock(RestClient.class);
        final JSONObject mockJSON = PowerMockito.mock(JSONObject.class);
        when(mockRestClient.post(anyString(), any(JSONObject.class))).thenThrow(Exception.class);
        Version version = new Version(mockRestClient,getTestJSON());
        version.copyTo(new Project(mockRestClient,mockJSON));
    }


    @Test
    public void testToString() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertEquals(version.toString(), "1.0");
    }

    @Test
    public void testGetName() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertEquals(version.getName(), "1.0");
    }

    @Test
    public void testIsArchived() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertFalse(version.isArchived());
    }

    @Test
    public void testIsReleased() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertFalse(version.isReleased());
    }

    @Test
    public void testGetReleaseDate() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertEquals("2013-12-01",version.getReleaseDate());
    }

    @Test
    public void testGetDescription() throws Exception {
        Version version = new Version(null, getTestJSON());
        assertEquals("First Full Functional Build",version.getDescription());
    }
}

