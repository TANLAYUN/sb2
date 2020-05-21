package com.example.sb2.entity;

public class LikeOrNot {
    private Integer id;
    private Integer ansId;
    private Integer userId;
    private Integer likeState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLikeState() {
        return likeState;
    }

    public void setLikeState(Integer likeState) {
        this.likeState = likeState;
    }

}
