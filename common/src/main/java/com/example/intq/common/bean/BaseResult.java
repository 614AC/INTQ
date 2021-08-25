package com.example.intq.common.bean;

public class BaseResult {
    private String status;
    private String body;
    public BaseResult(String status, String body){
        this.status = status;
        this.body = body;
    }

    public String getBody(){
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
