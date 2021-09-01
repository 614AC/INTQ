package com.example.intq.common.bean.instance;

public class StarInst {
    private String course;
    private String label;
    private String uri;

    public StarInst(String course, String label, String uri) {
        this.course = course;
        this.label = label;
        this.uri = uri;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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
}
