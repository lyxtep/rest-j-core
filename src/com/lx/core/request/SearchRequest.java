package com.lx.core.request;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author lx.ds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    private int start;
    private int limit;
    private String value;
    private List<String> tags;
    private Object entity;
    
    public SearchRequest() {
        initizalize(0, 0, null, null, null);
    }
    
    public SearchRequest(int start, int limit, String value, List<String> tags, Object entity) {
        initizalize(start, limit, value, tags, entity);
    }
    
    private void initizalize(int start, int limit, String value, List<String> tags, Object entity) {
        this.start = start;
        this.limit = limit;
        this.value = value;
        this.tags = tags;
        this.entity = entity;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the entity
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
