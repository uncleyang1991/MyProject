package org.uy.base.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Dao层基础Dto
 */
public class BaseDto implements Serializable{

    private static final long serialVersionUID = 1204380544129142578L;

    private String id;

    //@JsonFormat(pattern = "yyyy年MM月dd日")
    private Timestamp createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
