package com.example.intq.user.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.user.UserInfoResult;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IUserInfoRequest {
    /**
     * UserInfo
     * @header token
     * @return UserInfoResult
     */
    @GET("user/info")
    Observable<Result<UserInfoResult>> getUserInfo(@Header("token") String token);
}