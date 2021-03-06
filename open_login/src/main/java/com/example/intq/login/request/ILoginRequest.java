package com.example.intq.login.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.user.UserLoginResult;
import com.google.gson.internal.LinkedTreeMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
  * desc
  * author VcStrong
  * github VcStrong
  * date 2020/5/28 1:42 PM
  */
public interface ILoginRequest {

    /**
     * 密码规则是数字加字母超过8位即可
     * @return
     */
    @POST("user/register")
    Observable<Result<LinkedTreeMap>> register(@Body RequestBody info);

    /**
     * 密码规则是数字加字母超过8位即可
     * @return
     */
    @POST("user/login")
    Observable<Result<UserLoginResult>> login(@Body RequestBody info);
}
