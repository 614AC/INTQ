package com.example.intq.main.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.instance.InstList;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ISearchRequest {

    @GET("instance/list")
    Observable<Result<InstList>> getInstLabelList(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("sort") String sort,
            @Query("key") String key,
            @Query("course") String course
    );
}
