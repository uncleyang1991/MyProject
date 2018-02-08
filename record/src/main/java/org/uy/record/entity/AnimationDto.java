package org.uy.record.entity;

import java.util.Date;

public class AnimationDto extends BaseDto{

    private static final long serialVersionUID = -240742581211628338L;

    //名称
    private String name;

    //剧情类型
    private String type;

    //集数
    private Integer total;

    //种类
    private String dramaType;

    //编剧
    private String writer;

    //状态
    private String isEnd;

    //播出时间
    private Date showTime;

    //观看时间
    private Date watchTime;

    //观看状态
    private String watchState;

    //星级
    private int level;

    //声优
    private String performers;

    //剧情简介
    private String introduce;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Date getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Date watchTime) {
        this.watchTime = watchTime;
    }

    public String getWatchState() {
        return watchState;
    }

    public void setWatchState(String watchState) {
        this.watchState = watchState;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPerformers() {
        return performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDramaType() {
        return dramaType;
    }

    public void setDramaType(String dramaType) {
        this.dramaType = dramaType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
