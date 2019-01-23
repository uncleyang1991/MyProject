package org.uy.record.entity;

import java.util.Date;

public class OperationDto extends BaseDto {

    private static final long serialVersionUID = -5568261643229156332L;

    private String rid;

    private String name;

    private String action;

    private Date actionTime;

    public OperationDto() {
    }

    public OperationDto(String id,String rid, String action) {
        this.id = id;
        this.rid = rid;
        this.action = action;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
