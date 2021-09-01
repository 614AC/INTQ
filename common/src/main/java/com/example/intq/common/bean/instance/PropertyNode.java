package com.example.intq.common.bean.instance;

public class PropertyNode {
    private String predicate;
    private String predicateLabel;
    private String object;
    public PropertyNode() {}
    public PropertyNode(String p, String o) {
        this.predicateLabel = p;
        this.object = o;
    }
    public String getPredicate() {
        return this.predicate;
    }
    public void setPredicate(String p) {
        this.predicate = p;
    }
    public String getPredicateLabel() {
        return this.predicateLabel;
    }
    public void setPredicateLabel(String p) {
        this.predicateLabel = p;
    }
    public String getObject() {
        return this.object;
    }
    public void setObject(String o) {
        this.object = o;
    }
}
