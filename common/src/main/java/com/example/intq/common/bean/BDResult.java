package com.example.intq.common.bean;

public class BDResult<T> {
    int status;
    T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg(){
        return data.toString();
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
