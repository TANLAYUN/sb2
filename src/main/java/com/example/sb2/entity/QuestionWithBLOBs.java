package com.example.sb2.entity;

public class QuestionWithBLOBs extends Question {

    private String quesTitle;
    private String quesContent;

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
}