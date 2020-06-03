package com.example.sb2.entity;

import java.util.Date;

public class Comment {

    private Integer comId;
    private Integer ansId;
    private Integer userId;
    private String comTime;
    private Integer comState;
    private String comContent;
    private Integer ansComId;

    public Integer getAnsComId() {
        return ansComId;
    }

    public void setAnsComId(Integer ansComId) {
        this.ansComId = ansComId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getAnsId() {
        return ansId;
    }

    public void setAnsId(Integer ansId) {
        this.ansId = ansId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public Integer getComState() {
        return comState;
    }

    public void setComState(Integer comState) {
        this.comState = comState;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent == null ? null : comContent.trim();
    }
}