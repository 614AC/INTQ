package com.example.intq.common.bean.instance;

import java.util.List;

public class StarInstResult {
    private List<StarInst> instList;

    public StarInstResult(List<StarInst> instList) {
        this.instList = instList;
    }

    public List<StarInst> getInstList() {
        return instList;
    }

    public void setInstList(List<StarInst> instList) {
        this.instList = instList;
    }
}
