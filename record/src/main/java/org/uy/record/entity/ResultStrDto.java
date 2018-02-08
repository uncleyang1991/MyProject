package org.uy.record.entity;

import java.io.Serializable;

public class ResultStrDto implements Serializable{

    private static final long serialVersionUID = 874659142266575937L;

    private String key;

    private String value;

    public ResultStrDto() {
    }

    public ResultStrDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
