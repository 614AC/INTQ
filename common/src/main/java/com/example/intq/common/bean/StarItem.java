package com.example.intq.common.bean;

public class StarItem {
    private int type;
    private String label;
    private String uri;
    private String course;
    private Integer exerciseId;

    public StarItem(int type, String label, String uri, String course, Integer exerciseId) {
        this.type = type;
        this.label = label;
        this.uri = uri;
        this.course = course;
        this.exerciseId = exerciseId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
}
