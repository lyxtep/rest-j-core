package com.lx.core.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author lx.ds
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class ValidationResponse {

    private String value;

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
}
