package com.example.sb2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Question {

    private Integer quesId;
    private Integer userId;
    private String quesTime;
    private Integer quesAnsState;
    private Integer quesState;
    private String quesTitle;
    private String quesContent;
    private Integer quesColNum;
    private Integer quesAnsNum;

    public Integer getQuesAnsNum() {
        return quesAnsNum;
    }

    public void setQuesAnsNum(Integer quesAnsNum) {
        this.quesAnsNum = quesAnsNum;
    }

    public Integer getQuesReward() {
        return quesReward;
    }

    public void setQuesReward(Integer quesReward) {
        this.quesReward = quesReward;
    }

    private Integer quesReward;

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

    public String getQuesTime() {
        return quesTime;
    }

    public void setQuesTime(String quesTime) {
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