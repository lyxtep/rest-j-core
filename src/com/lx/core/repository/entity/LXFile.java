package com.lx.core.repository.entity;

import com.lx.core.service.generic.MetaColumn;

/**
 *
 * @author lx.ds
 */
public class LXFile {

    @MetaColumn(primaryKey = true, length = 10, allowNull = false)
    private String id;
        
    @MetaColumn(length = 200, allowNull = false)
    private String name;
    
    @MetaColumn(length = 200, allowNull = false)
    private String originalName;
    
    @MetaColumn(allowNull = false)
    private Long size;
        
    //@MetaColumn(length = 200)
    private String note;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the originalName
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * @param originalName the originalName to set
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
}
