package com.example.intq.user.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.intq.common.core.WDFragment;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.FragMeBinding;
import com.example.intq.user.vm.UserViewModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Route(path = Constant.FRAGMENT_URL_ME)
public class MeFragment extends WDFragment<UserViewModel,FragMeBinding> {

    @Override
    protected UserViewModel initFragViewModel() {
        return new UserViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_me;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewModel.avatar.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    binding.meAvatar.setImageURI(Uri.parse(s));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveAvatar(s);
                        }
                    }).start();
                }catch (Exception e){
                    File head = new File(getActivity().getFilesDir(), "head" + viewModel.getUserId() + ".jpg");
                    if(head.exists()){
                        binding.meAvatar.setImageURI(FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.java.jiangjianxiao.fileprovider",head ));
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.updateInfo();
        viewModel.updateAvatar();
    }

    private void saveAvatar(String s){
        Bitmap head = getBitmap(s);
        setPicToView(head);
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    private void setPicToView(Bitmap mBitmap) {
        String path = getActivity().getApplicationContext().getFilesDir().getPath();
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/head" + viewModel.getUserId() + ".jpg";// 图片名字
        System.out.println(fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
// 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
