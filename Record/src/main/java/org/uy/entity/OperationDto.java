package org.uy.entity;

import org.uy.base.dto.BaseDto;

import java.util.Date;

public class OperationDto extends BaseDto {

    private static final long serialVersionUID = -5568261643229156332L;

    private String type;

    private String name;

    private String action;

    private Date actionTime;

    public OperationDto() {
    }

    public OperationDto(String id,String type, String name, String action) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
