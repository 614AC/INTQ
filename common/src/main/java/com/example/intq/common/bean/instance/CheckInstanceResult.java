package com.example.intq.common.bean.instance;

import java.util.List;

public class CheckInstanceResult {
    private List<Boolean> if_star;


    public CheckInstanceResult(List<Boolean> if_star) {
        this.if_star = if_star;
    }

    public List<Boolean> getIf_star() {
        return if_star;
    }

    public void setIf_star(List<Boolean> if_star) {
        this.if_star = if_star;
    }
}
