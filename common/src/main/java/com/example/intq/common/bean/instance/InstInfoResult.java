package com.example.intq.common.bean.instance;

public class InstInfoResult {
    private InstInfoAlter instInfo;

    public InstInfoResult(InstInfoAlter instInfo) {
        this.instInfo = instInfo;
    }

    public InstInfoAlter getInstInfo() {
        return instInfo;
    }

    public void setInstInfo(InstInfoAlter instInfo) {
        this.instInfo = instInfo;
    }
}
