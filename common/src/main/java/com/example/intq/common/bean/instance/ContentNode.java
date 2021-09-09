package com.example.intq.common.bean.instance;

public class ContentNode {
    private String predicate;
    private String predicate_label;
    private String object;
    private String object_label;
    private String subject;
    private String subject_label;
    public ContentNode() {}
    public ContentNode(String p, String o) {
        this.predicate_label = p;
        this.object = o;
    }
    public String getPredicate() {
        return this.predicate;
    }
    public void setPredicate(String p) {
        this.predicate = p;
    }
    public String getPredicate_label() {
        return this.predicate_label;
    }
    public void setPredicate_label(String p) {
        this.predicate_label = p;
    }
    public String getObject() {
        return this.object;
    }
    public void setObject(String o) {
        this.object = o;
    }
    public String getObject_label() {
        return this.object_label;
    }
    public void setObject_label(String o) {
        this.object_label = o;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String s) {
        this.subject = s;
    }
    public String getSubject_label() {
        return this.subject_label;
    }
    public void setSubject_label(String s) {
        this.subject_label = s;
    }
}
