package com.example.sb2.entity;

import java.util.Date;

public class Collection {
    private Integer colId;
    private Integer colUserId;
    private Integer colQuesId;
    private String colTime;

    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    public Integer getColUserId() {
        return colUserId;
    }

    public void setColUserId(Integer colUserId) {
        this.colUserId = colUserId;
    }

    public Integer getColQuesId() {
        return colQuesId;
    }

    public void setColQuesId(Integer colQuesId) {
        this.colQuesId = colQuesId;
    }

    public String getColTime() {
        return colTime;
    }

    public void setColTime(String colTime) {
        this.colTime = colTime;
    }
}
