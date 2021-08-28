package com.example.intq.common.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class QAChat {
    @Id
    long id;
    private Boolean question;
    private String content;

    public QAChat(Boolean question, String content) {
        this.question = question;
        this.content = content;
    }

    public Boolean getQuestion() {
        return question;
    }

    public void setQuestion(Boolean question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
