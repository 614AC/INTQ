package com.example.intq.common.core;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.intq.common.BR;
import com.example.intq.common.bean.UserInfo;
import com.example.intq.common.bean.UserInfo_;
import com.example.intq.common.util.logger.Logger;

import java.lang.reflect.ParameterizedType;

import io.objectbox.Box;

/**
 * desc
 * author VcStrong
 * github VcStrong
 * date 2020/5/28 1:42 PM
 */
public abstract class WDFragment<VM extends WDFragViewModel, VDB extends ViewDataBinding> extends Fragment {
    private final Logger logger = Logger.createLogger(getClass());
    protected VM viewModel = initFragViewModel();
    protected VDB binding;

    protected Box<UserInfo> userInfoBox;
    protected UserInfo LOGIN_USER;

    @Override
    public void onAttach(@NonNull Context context) {
        logger.i("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        logger.i("onHiddenChanged hidden=%s", hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        logger.i("onCreate");
        super.onCreate(savedInstanceState);
        userInfoBox = WDApplication.getBoxStore().boxFor(UserInfo.class);
        LOGIN_USER = userInfoBox.query()
                .equal(UserInfo_.status, 1)
                .build().findUnique();
        ARouter.getInstance().inject(this);
        getActivity().getLifecycle().addObserver(viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logger.i("onCreateView container=" + container);
        if (binding == null) {
            // ??????ViewPager????????????????????????????????????????????????????????????View
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            binding.setVariable(BR.vm, viewModel);
            initView(savedInstanceState);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logger.i("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        logger.i("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        logger.i("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        logger.i("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        logger.i("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        logger.i("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        logger.i("onDestroy");
        super.onDestroy();
        getActivity().getLifecycle().removeObserver(viewModel);
    }

    @Override
    public void onDetach() {
        logger.i("onDetach");
        super.onDetach();
    }

    protected abstract VM initFragViewModel();

    public VM getFragViewModel() {
        return viewModel;
    }

    /**
     * ????????????????????????Class??????
     *
     * @return
     */
    private Class<VM> getTClass() {
        //??????????????? Class ??????????????????????????????????????????????????? void????????????????????? Type???
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //?????????????????????????????????????????? Type ???????????????()?????????????????????????????????Class??????????????????1
        return (Class) type.getActualTypeArguments()[0];//<T>
    }

    /**
     * ??????layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * ???????????????
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * @param path ??????Activity???
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(getContext());
    }

    /**
     * @param path   ??????Activity???
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getContext());
    }

    /**
     * @param path   ??????Activity???
     * @param bundle
     */
    public void intentForResultByRouter(String path, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getActivity(), requestCode);
    }

    /**
     * @param path ??????Activity???
     */
    public void intentForResultByRouter(String path, int requestCode) {
        ARouter.getInstance().build(path)
                .navigation(getActivity(), requestCode);
    }
}
