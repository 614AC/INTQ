package com.example.intq.user.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                }catch (Exception e){
                    File head = new File(getFilesDir(), "head.jpg");
                    if(head.exists()){
                        binding.meAvatar.setImageURI(FileProvider.getUriForFile(getApplicationContext(), "com.example.intq.fileprovider", head));
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
                File head = new File(getFilesDir(), "head.jpg");
                if(!head.getParentFile().exists())
                    head.getParentFile().mkdirs();
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(MeInfoActivity.this, "com.example.intq.fileprovider", head));
                startActivityForResult(intent2, 2);// 采用ForResult打开
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
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    cropPhoto(FileProvider.getUriForFile(this, "com.example.intq.fileprovider", new File(getFilesDir(), "head.jpg")));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
/**
 * 上传服务器代码
 */
                        setPicToView(head);// 保存在SD卡中
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
// aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String path = getFilesDir().getPath();
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/head.jpg";// 图片名字
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
