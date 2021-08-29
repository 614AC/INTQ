package com.example.intq.common.core;


import com.example.intq.common.core.exception.ApiException;

/**
 * desc
 * author VcStrong
 * github VcStrong
 * date 2020/5/28 1:42 PM
 */
public interface DataCall<T> {

    void success(T data);
    void fail(ApiException data);

}
