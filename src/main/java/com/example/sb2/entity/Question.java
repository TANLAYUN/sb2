package com.example.sb2.entity;

import java.util.Date;

public class Question {

    private Integer quesId;
    private Integer userId;
    private Date quesTime;
    private Integer quesAnsState;
    private Integer quesState;
    private String quesTitle;
    private String quesContent;
    private Integer quesColNum;

    public Integer getQuesId() {
        return quesId;
    }

    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getQuesTime() {
        return quesTime;
    }

    public void setQuesTime(Date quesTime) {
        this.quesTime = quesTime;
    }

    public Integer getQuesAnsState() {
        return quesAnsState;
    }

    public void setQuesAnsState(Integer quesAnsState) {
        this.quesAnsState = quesAnsState;
    }

    public Integer getQuesState() {
        return quesState;
    }

    public void setQuesState(Integer quesState) {
        this.quesState = quesState;
    }

    public String getQuesTitle() {
        return quesTitle;
    }

    public void setQuesTitle(String quesTitle) {
        this.quesTitle = quesTitle == null ? null : quesTitle.trim();
    }

    public String getQuesContent() {
        return quesContent;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent == null ? null : quesContent.trim();
    }

    public Integer getQuesColNum() {
        return quesColNum;
    }

    public void setQuesColNum(Integer quesColNum) {
        this.quesColNum = quesColNum;
    }

}