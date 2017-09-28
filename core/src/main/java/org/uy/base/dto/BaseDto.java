package org.uy.base.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class BaseDto implements Serializable{

    private String id;

    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
