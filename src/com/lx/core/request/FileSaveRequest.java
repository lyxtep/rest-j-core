package com.lx.core.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author lx.ds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileSaveRequest {

    private String filename;
    private String originalFilename;
    private Integer size;

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the originalFilename
     */
    public String getOriginalFilename() {
        return originalFilename;
    }

    /**
     * @param originalFilename the originalFilename to set
     */
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}
