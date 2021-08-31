package com.example.intq.common.bean;

import android.graphics.RectF;

public class InstanceNode {
    private String name;
    private int symbolSize;
    private RectF rectF;

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
}
