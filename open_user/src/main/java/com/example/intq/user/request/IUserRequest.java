package com.example.intq.user.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.user.AvatarResult;
import com.example.intq.common.bean.user.UserInfoResult;
import com.google.gson.internal.LinkedTreeMap;

import io.reactivex.Observable;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IUserRequest {
    /**
     * UserInfo
     * @header token
     * @return UserInfoResult
     */
    @GET("user/info")
    Observable<Result<UserInfoResult>> getUserInfo(@Header("token") String token);

    @POST("user/changeinfo")
    Observable<Result<LinkedTreeMap>> changeinfo(@Body RequestBody body, @Header("token") String token);

    @GET("user/avatar")
    Observable<Result<AvatarResult>> avatar(@Header("token") String token);

    @POST("user/changeavatar")
    Observable<Result<AvatarResult>> changeAvatar(@Header("token") String token, @Body RequestBody body);
}