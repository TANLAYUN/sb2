package com.example.sb2.entity;

import java.util.Date;

public class Answer {

    private Integer ansId;
    private Integer quesId;
    private Integer userId;
    private String ansTime;
    private Integer ansState;
    private Integer goodCount;
    private Integer badCount;
    private Integer bestAnswer;
    private String ansContent;

    public Integer getAnsId() {
        return ansId;
    }

    public void setAnsId(Integer ansId) {
        this.ansId = ansId;
    }

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

    public String getAnsTime() {
        return ansTime;
    }

    public void setAnsTime(String ansTime) {
        this.ansTime = ansTime;
    }

    public Integer getAnsState() {
        return ansState;
    }

    public void setAnsState(Integer ansState) {
        this.ansState = ansState;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Integer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public String getAnsContent() {
        return ansContent;
    }

    public void setAnsContent(String ansContent) {
        this.ansContent = ansContent == null ? null : ansContent.trim();
    }
}