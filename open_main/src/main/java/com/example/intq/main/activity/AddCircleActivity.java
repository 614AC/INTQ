package com.example.intq.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.example.intq.main.R;
import com.example.intq.main.adapter.ImageAdapter;
import com.example.intq.common.core.WDActivity;
import com.example.intq.main.databinding.ActivityAddCircleBinding;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.StringUtils;
import com.example.intq.main.vm.AddCircleViewModel;

@Route(path = Constant.ACTIVITY_URL_ADD_CIRCLE)
public class AddCircleActivity extends WDActivity<AddCircleViewModel, ActivityAddCircleBinding> {

    ImageAdapter mImageAdapter;
    final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_circle;
    }

    @Override
    protected void initView(Bundle bundle) {
        mImageAdapter = new ImageAdapter();
        mImageAdapter.setSign(1);
        mImageAdapter.add(R.drawable.mask_01);
        binding.boImageList.setLayoutManager(new GridLayoutManager(this,3));
        binding.boImageList.setAdapter(mImageAdapter);
        binding.send.setOnClickListener(view->{
            viewModel.publish(mImageAdapter.getList());
        });
        viewModel.forResult.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                setResult(RESULT_OK);
            }
        });

        permessionPhoto();
    }

    public void permessionPhoto(){
        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                        permessionPhoto();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath);
                mImageAdapter.notifyDataSetChanged();
            }
        }

    }
}
