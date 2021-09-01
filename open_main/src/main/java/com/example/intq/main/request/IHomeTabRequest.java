package com.example.intq.main.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.instance.InstList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IHomeTabRequest {

    @GET("instance/randomlist")
    Observable<Result<InstList>> getRandomInstList(
            @Header("token") String token,
            @Query("limit") int limit,
            @Query("course") String course
    );
}
