package com.example.sb2.entity;

import java.util.Date;

public class User {

    private String mail;
    private Integer userId;
    private String name;
    private String pwd;
    private Integer state;
    private String addTime;
    private Integer capital;

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}