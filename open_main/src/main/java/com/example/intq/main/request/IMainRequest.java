package com.example.intq.main.request;

import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.instance.InstInfoResult;
import com.example.intq.common.bean.instance.InstList;
import com.example.intq.common.bean.instance.LinkInstanceResult;
import com.example.intq.common.bean.question.SolveResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMainRequest {

    /**
     * 主页Tab随机实体列表
     */
    @GET("instance/randomlist")
    Observable<Result<InstList>> getRandomInstList(
            @Header("token") String token,
            @Query("limit") int limit,
            @Query("course") String course
    );

    /**
     * 实体搜索
     */
    @GET("instance/list")
    Observable<Result<InstList>> getInstList(
            @Header("token") String token,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("sort") String sort,
            @Query("key") String key,
            @Query("course") String course
    );

    /**
     * 知识问答
     */
    @POST("question/solve")
    Observable<Result<SolveResult>> solve(@Query("question") String question, @Query("course") String course);

    /**
     * 实体详情
     */
    @GET("instance/info")
    Observable<Result<InstInfoResult>> getInstanceInfo(@Query("instName") String instName, @Query("course") String course);

    @POST("instance/linkinstance")
    Observable<Result<LinkInstanceResult>> getLinkInstance(@Body RequestBody body);

}
