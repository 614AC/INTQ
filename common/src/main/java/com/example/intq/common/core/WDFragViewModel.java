package com.example.intq.common.core;

import android.os.Bundle;
import android.os.Message;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.bean.InstInfo;
import com.example.intq.common.bean.Result;
import com.example.intq.common.bean.UserInfo;
import com.example.intq.common.bean.UserInfo_;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.logger.Logger;

import java.lang.reflect.ParameterizedType;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * desc 每个Activity对应一个viewmodel，又或者多个Fragment同Activity共享一个viewmodel，
 * 负责拿到model(请求网络或者封装)，通过Livedata完成页面数据更新
 * author VcStrong
 * github VcStrong
 * date 2020/5/28 1:42 PM
 */
public abstract class WDFragViewModel<R> implements LifecycleObserver {
    private final Logger logger = Logger.createLogger(getClass());

    public final static int REQUEST_TYPE_DEFAULT = 0;//默认IRquest
    public final static int REQUEST_TYPE_APP_ORDER = 1;//例：这个为订单请求接口，由于接口类中方法太多，所以写了另外一个业务接口
    public final static int REQUEST_TYPE_APP_AD = 2;//例：这个为广告请求接口，由于接口类中方法太多，所以写了另外一个业务接口
    public final static int REQUEST_TYPE_SDK_BD = 100;//例：这个为请求百度的接口，请求结构为另外一种

    public final static int RESPONSE_TYPE_DEFAULT = 0;
    public final static int RESPONSE_TYPE_SDK_BD = 100;////例：这个为请求百度的接口，接口结构为另外一种

    //负责每个页面中的dialog弹出框隐藏和展示
    public MutableLiveData<Boolean> dialog;
    //关闭页面
    public MutableLiveData<Void> finish;
    //如果上个页面使用了startActivityForResult，则本页面回传值需要使用此参数进行回传值的操作
    public MutableLiveData<Void> forResult;

    //Fragment数据共享，参考https://developer.android.google.cn/topic/libraries/architecture/viewmodel#sharing
    public MutableLiveData<Message> fragDataShare;

    protected Box<UserInfo> userInfoBox;
    protected UserInfo LOGIN_USER;

    protected Box<InstInfo> instInfoBox;

    protected R iRequest;

    public WDFragViewModel() {
        Class<R> tClass = getTClass();
        if (!tClass.equals(Void.class)) {
            iRequest = NetworkManager.instance().create(getRequestType(), tClass);
        }
    }

    public void init(MutableLiveData<Boolean> dialog, MutableLiveData<Void> finish, MutableLiveData<Void> forResult, MutableLiveData<Message> fragDataShare) {
        logger.i("init");
        this.dialog = dialog;
        this.finish = finish;
        this.forResult = forResult;
        this.fragDataShare = fragDataShare;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void create() {
        logger.i("Frag-VM create");
        userInfoBox = WDApplication.getBoxStore().boxFor(UserInfo.class);
        LOGIN_USER = userInfoBox.query()
                .equal(UserInfo_.status, 1)
                .build().findUnique();
        instInfoBox = WDApplication.getBoxStore().boxFor(InstInfo.class);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void start() {
        logger.i("Frag-VM start");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void resume() {
        logger.i("Frag-VM resume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void pause() {
        logger.i("Frag-VM pause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void stop() {
        logger.i("Frag-VM stop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void destroy() {
        logger.i("Frag-VM destroy");
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation();
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation();
    }

    public void finish() {
        finish.setValue(null);
    }

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class<R> getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class) type.getActualTypeArguments()[0];//<T>
    }

    /**
     * 请求数据，所有网络操作请使用本方法
     *
     * @param observable
     * @param dataCall
     * @return
     */
    public Disposable request(Observable observable, final DataCall dataCall) {
        return observable.subscribeOn(Schedulers.io())//将请求调度到子线程上
                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .onErrorReturn(new Function<Throwable, Throwable>() {//处理所有异常
                    @Override
                    public Throwable apply(Throwable throwable) throws Exception {
                        return throwable;
                    }
                })
                .subscribe(getConsumer(dataCall), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        dataCall.fail(ApiException.handleException(e));
                    }
                });
    }

    /**
     * 此方法用来确定采用的Rtrofit中baseUrl
     * 由于Retrofit特性，baseUrl不能随意改动，当大项目拥有
     * 多个域名控制不同业务的时候，则需要不同的Retrofit
     *
     * @TODO 可以根据需要在子类中重写
     */
    protected int getRequestType() {
        return REQUEST_TYPE_DEFAULT;
    }

    /**
     * 返回值类型，方便不同接口返回数据结构不同的情况，参见{@link #getConsumer(DataCall)}
     * 应对大项目多数据结构
     *
     * @TODO 可以根据需要在子类中重写
     */
    protected int getResponseType() {
        return RESPONSE_TYPE_DEFAULT;
    }

    /**
     * @param
     * @return
     * @author: yintao
     * @date: 2020/4/20 11:56 PM
     * @method
     * @description 根据返回值{@link #getResponseType()}灵活改变Consumer或者自己直接重写都可以
     */
    protected Consumer getConsumer(final DataCall dataCall) {

        return new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                if (result.getStatus().equals("200")) {
                    dataCall.success(result.getData());
                } else {
                    dataCall.fail(new ApiException(result.getStatus(), result.getData().toString()));
                }
            }
        };

    }

    public MutableLiveData<Boolean> getDialog() {
        return dialog;
    }
}

