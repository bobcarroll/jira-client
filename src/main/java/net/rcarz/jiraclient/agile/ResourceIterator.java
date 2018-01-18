package net.rcarz.jiraclient.agile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Iterates over all issues in the query by getting the next page of
 * issues when the iterator reaches the last of the current page.
 */
public class ResourceIterator<T extends AgileResource> implements Iterator<T>
{
    private Iterator<T> currentPage;
    private RestClient restclient;
    private T nextIssue;
    private Class<T> type;
    private String url;
    private String listName;
    private Integer maxResults;
    private String jql;
    private String includedFields;
    private String expandFields;
    private Integer startAt;
    private List<T> issues;
    private boolean isLast;
    private int total;

    public ResourceIterator(RestClient restclient,
                            Class<T> type,
                            String url,
                            String listName,
                            String jql,
                            String includedFields,
                            String expandFields,
                            Integer maxResults,
                            Integer startAt)
        throws JiraException
    {
        this.restclient = restclient;
        this.type = type;
        this.url = url;
        this.listName = listName;
        this.jql = jql;
        this.includedFields = includedFields;
        this.expandFields = expandFields;
        this.maxResults = (maxResults == null ? 500 : maxResults);
        this.startAt = startAt;
    }

    @Override
    public boolean hasNext()
    {
        if (nextIssue != null)
        {
            return true;
        }
        try
        {
            nextIssue = getNextIssue();
        }
        catch (JiraException e)
        {
            throw new RuntimeException(e);
        }
        return nextIssue != null;
    }

    @Override
    public T next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException();
        }
        T result = nextIssue;
        nextIssue = null;
        return result;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Method remove() not support for class "
                                                + this.getClass()
                                                      .getName());
    }

    /**
     * Gets the next issue, returning null if none more available
     * Will ask the next set of issues from the server if the end
     * of the current list of issues is reached.
     * 
     * @return the next issue, null if none more available
     * @throws JiraException
     */
    private T getNextIssue()
        throws JiraException
    {
        // first call
        if (currentPage == null)
        {
            currentPage = getNextIssues().iterator();
            if (currentPage == null || !currentPage.hasNext())
            {
                return null;
            }
            else
            {
                return currentPage.next();
            }
        }

        // check if we need to get the next set of issues
        if (!currentPage.hasNext())
        {
            currentPage = getNextIssues().iterator();
        }

        // return the next item if available
        if (currentPage.hasNext())
        {
            return currentPage.next();
        }
        else
        {
            return null;
        }
    }

    /**
     * Execute the query to get the next set of issues.
     * Also sets the startAt, maxMresults, total and issues fields,
     * so that the SearchResult can access them.
     * 
     * @return the next set of issues.
     * @throws JiraException
     */
    private List<T> getNextIssues()
        throws JiraException
    {
        if (issues == null && startAt == null)
        {
            startAt = Integer.valueOf(0);
        }
        else if (issues != null)
        {
            startAt = startAt + issues.size();
        }

        if (isLast || (startAt != 0 && total != 0 && startAt >= total))
            return new ArrayList<>();

        JSON result = null;
        try
        {
            result = restclient.get(createURI());
        }
        catch (Exception ex)
        {
            throw new JiraException("Failed to retrieve a list of " + type.getSimpleName() + " : " + url, ex);
        }

        if (!(result instanceof JSONObject))
        {
            throw new JiraException("JSON payload is malformed");
        }

        Map map = (Map) result;
        this.isLast = Field.getBoolean(map.get("isLast"));
        this.maxResults = Field.getInteger(map.get("maxResults"));
        this.total = Field.getInteger(map.get("total"));
        this.issues = AgileResource.getResourceArray(type, result, restclient, listName);
        // System.out.println(startAt + " " + maxResults + " " + total + " " + isLast);
        return issues;
    }

    private URI createURI()
        throws URISyntaxException
    {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("jql", jql);
        if (maxResults != null)
        {
            queryParams.put("maxResults", String.valueOf(maxResults));
        }
        if (includedFields != null)
        {
            queryParams.put("fields", includedFields);
        }
        if (expandFields != null)
        {
            queryParams.put("expand", expandFields);
        }
        if (startAt != null)
        {
            queryParams.put("startAt", String.valueOf(startAt));
        }

        URI uri = restclient.buildURI(url, queryParams);
        // System.out.println(uri);
        return uri;
    }
}
