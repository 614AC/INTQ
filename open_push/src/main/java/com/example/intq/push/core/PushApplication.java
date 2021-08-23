package com.example.intq.push.core;

import android.app.Application;

import com.example.intq.common.core.IWDApplication;
import com.example.intq.common.util.logger.Logger;

/**
 * desc Module的Application不要去清单文件中配置，请打开common包的IWDAppcation接口把路径(包名+类名)配置上，
 *      WDApplication会自动初始化这些子模块App
 * author VcStrong
 * github VcStrong
 * date 2020/5/28 1:42 PM
 */
public class PushApplication implements IWDApplication {
    private final Logger logger = Logger.createLogger(getClass());
    @Override
    public void onCreate(Application application) {
        //推送初始化等等工作
        logger.i("初始化");
    }
}
