package com.example.intq.user.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.user.R;
import com.example.intq.user.databinding.ActivityMeInfoBinding;
import com.example.intq.user.vm.MeInfoViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Route(path = Constant.ACTIVITY_URL_ME_INFO)
public class MeInfoActivity extends WDActivity<MeInfoViewModel, ActivityMeInfoBinding> {

    BottomSheetDialog mAvatarDialog;
    Bitmap head;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_info;
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
                            head = getBitmap(s);
                            if(head != null)
                                setPicToView(head);// ?????????SD??????
                        }
                    }).start();

                }catch (Exception e){
                    File head = new File(getFilesDir(), "head" + viewModel.getUserId() + ".jpg");
                    if(head.exists()){
                        binding.meAvatar.setImageURI(FileProvider.getUriForFile(getApplicationContext(), "com.java.jiangjianxiao.fileprovider", head));
                    }
                }
            }
        });
        findViewById(R.id.avatar_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mAvatarDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_edit_avatar, null, false);
        view.findViewById(R.id.avatar_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File head = new File(getFilesDir(), "head_cache.jpg");
                if(!head.getParentFile().exists())
                    head.getParentFile().mkdirs();
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(MeInfoActivity.this, "com.java.jiangjianxiao.fileprovider", head));
                startActivityForResult(intent2, 2);// ??????ForResult??????
                mAvatarDialog.hide();
            }
        });
        view.findViewById(R.id.avatar_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                mAvatarDialog.dismiss();
            }
        });
        mAvatarDialog.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.updateInfo();
        viewModel.updateAvatar();
    }

    @Override
    protected void onDestroy() {
        mAvatarDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration){
        mAvatarDialog.hide();
    }

    public void showDialog() {

        mAvatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// ????????????
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    cropPhoto(FileProvider.getUriForFile(this, "com.java.jiangjianxiao.fileprovider", new File(getFilesDir(), "head_cache.jpg")));// ????????????
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        viewModel.editAvatar(head);
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ???????????????????????????
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
// aspectX aspectY ??????????????????
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX outputY ?????????????????????
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String path = getFilesDir().getPath();
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// ???????????????
        String fileName = path + "/head" + viewModel.getUserId() + ".jpg";// ????????????
        System.out.println(fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ?????????????????????
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
// ?????????
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // ????????????????????????
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// ?????????
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
}
