package org.uy.record.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Dao层基础Dto
 */
public class BaseDto implements Serializable{

    private static final long serialVersionUID = 1204380544129142578L;

    protected String id;

    //@JsonFormat(pattern = "yyyy年MM月dd日")
    protected Timestamp createTime;

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
