package com.example.intq.common.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class InstInfo {
    @Id(assignable = false)
    long instId;
    private String property;
    private String content;
    private String label;
    private String course;
    private String uri;
    private String exercise;

    public InstInfo(String property, String content, String label, String course, String uri, String exercise) {
        this.property = property;
        this.content = content;
        this.label = label;
        this.course = course;
        this.uri = uri;
        this.exercise = exercise;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
