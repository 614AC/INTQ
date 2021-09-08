package com.example.intq.main.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.bean.instance.Link;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityLinkBinding;
import com.example.intq.main.vm.LinkViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.List;

@Route(path = Constant.ACTIVITY_URL_LINK)
public class LinkActivity extends WDActivity<LinkViewModel, ActivityLinkBinding> {

    private static final String[] m={"语文","数学","英语","物理","化学", "生物", "政治", "历史", "地理"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private TextView textView;
    private EditText editText;
    private Button chatMessageSend;

    BottomSheetDialog mAvatarDialog;
    Bitmap head;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_link;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = findViewById(R.id.link_text);
        editText = findViewById(R.id.link_contexte);
        chatMessageSend = findViewById(R.id.btn_chat_message_send);

        spinner = (Spinner) findViewById(R.id.course_spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        viewModel.enabled.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                findViewById(R.id.link_text).setEnabled(aBoolean);
            }
        });

        viewModel.links.observe(this, new Observer<List<Link>>() {
            @Override
            public void onChanged(List<Link> links) {
                textView.setText("");
                textView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);

                String context = viewModel.context.get();
                if(context == null)
                    return;
//                if(links.size() == 0)
//                    return;

                // last end is not included in the last substring
                int lastEnd = 0;
                for(Link l:links){
                    if(l.getStart_index() > lastEnd)
                        textView.append(context.substring(lastEnd, l.getStart_index()));
                    String sub = context.substring(l.getStart_index(), l.getEnd_index() + 1);

                    SpannableString spannableString = new SpannableString(sub);
                    spannableString.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            viewModel.turnToInstance(l.getEntity(), l.getEntity_url());
                        }
                    }, 0, sub.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    textView.append(spannableString);

                    lastEnd = l.getEnd_index() + 1;
                }
                if(lastEnd < context.length())
                    textView.append(context.substring(lastEnd));
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                viewModel.enabled.setValue(true);
            }
        });

        viewModel.unchangeable.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    chatMessageSend.setText("重新输入文本");
                }
                else {
                    editText.setText("");
                    editText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    chatMessageSend.setText("点击生成链接文本");
                }
            }
        });

        findViewById(R.id.btn_choose_hand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.recognizeType.setValue(0);
                mAvatarDialog.show();
            }
        });
        findViewById(R.id.btn_choose_general).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.recognizeType.setValue(1);
                mAvatarDialog.show();
            }
        });
        mAvatarDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_choose_from, null, false);
        view.findViewById(R.id.link_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                editText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                chatMessageSend.setText("点击生成链接文本");
                File head = new File(getFilesDir(), "link_cache.jpg");
                if(!head.getParentFile().exists())
                    head.getParentFile().mkdirs();
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(LinkActivity.this, "com.example.intq.fileprovider", head));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                mAvatarDialog.hide();
            }
        });
        view.findViewById(R.id.link_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                editText.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                chatMessageSend.setText("点击生成链接文本");
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                mAvatarDialog.dismiss();
            }
        });
        mAvatarDialog.setContentView(view);
    }

    @Override
    protected void onDestroy() {
        mAvatarDialog.dismiss();
        super.onDestroy();
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
                    cropPhoto(FileProvider.getUriForFile(this, "com.example.intq.fileprovider", new File(getFilesDir(), "link_cache.jpg")));// 裁剪图片
                }
                break;
            case 3:
                File tmp = new File(getFilesDir(), "link_tmp_crop.jpg");
                if(tmp.exists()){
                    head = BitmapFactory.decodeFile(tmp.getPath());
                    if (head != null) {
                        viewModel.recognizeFrom(head);
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

        File tmp = new File(getFilesDir(), "link_tmp_crop.jpg");
        Uri out = FileProvider.getUriForFile(LinkActivity.this, "com.example.intq.fileprovider", tmp);

        grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        grantUriPermission(getPackageName(), out, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        grantUriPermission(getPackageName(), out, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        //将存储图片的uri读写权限授权给剪裁工具应用
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, out, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        startActivityForResult(intent, 3);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
