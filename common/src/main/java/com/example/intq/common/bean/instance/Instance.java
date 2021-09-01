package com.example.intq.common.bean.instance;

import android.graphics.RectF;

public class Instance {
    private String name;
    private int symbolSize;
    private RectF rectF;
    private String predicate_label;

    public Instance() {}

    public Instance(String n, int s) {
        this.name = n;
        this.symbolSize = s;
        this.rectF = null;
    }

    public Instance(String n, int s, String p) {
        this.name = n;
        this.symbolSize = s;
        this.rectF = null;
        this.predicate_label = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public String getPredicate_label() {
        return predicate_label;
    }

    public void setPredicate_label(String predicate_label) {
        this.predicate_label = predicate_label;
    }
}
