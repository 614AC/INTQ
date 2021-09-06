package com.example.intq.main.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.intq.common.core.WDActivity;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.R;
import com.example.intq.main.databinding.ActivityInstanceBinding;
import com.example.intq.main.fragment.ExerciseFragment;
import com.example.intq.main.fragment.InstanceGraphFragment;
import com.example.intq.main.fragment.InstanceListFragment;
import com.example.intq.main.view.CirclePeopleView;
import com.example.intq.main.vm.InstanceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Route(path = Constant.ACTIVITY_URL_INSTANCE)
public class InstanceActivity extends WDActivity<InstanceViewModel, ActivityInstanceBinding> implements WbShareCallback, CompoundButton.OnCheckedChangeListener {

    private final String[] titles = {"属性", "知识图谱", "相关试题"};
    private InstanceGraphFragment GraphFragment;
    private InstanceListFragment ListFragment;
    private ExerciseFragment ExerciseFragment;

    @Autowired
    public String inst_name;
    @Autowired
    public String course;
    @Autowired
    public String uri;

    private IWBAPI mWBAPI;

    BottomSheetDialog mDialog;
    BottomSheetDialog mExerciseDialog;
    private boolean isPropertySelected;
    private boolean isPicSelected;
    private boolean isExerciseSelected;
    private int[] checkbox;
    private View view;
    private View view1;
    private String shareExer;
    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_instance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        System.out.println("activity -> " + inst_name);
        TabLayout tabLayout = findViewById(R.id.inst_tab);
        ViewPager viewPager = findViewById(R.id.inst_pager);
        TextView tv = findViewById(R.id.inst_name);
        tv.setText(inst_name);

        viewModel.if_star.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                findViewById(R.id.star_icon).setActivated(aBoolean);
            }
        });
        viewModel.instName.setValue(inst_name);
        viewModel.course.setValue(course);
        viewModel.uri.setValue(uri);
        viewModel.checkStar();
        viewModel.addHistory();

        Bundle bundle = new Bundle();
        bundle.putString("inst_name", inst_name);
        bundle.putString("course", course);
        bundle.putString("uri", uri);
        GraphFragment = new InstanceGraphFragment();
        GraphFragment.setArguments(bundle);
        ListFragment = new InstanceListFragment();
        ListFragment.setArguments(bundle);
        ExerciseFragment = new ExerciseFragment();
        ExerciseFragment.setArguments(bundle);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return ListFragment;
                else if (position == 1)
                    return GraphFragment;
                else
                    return ExerciseFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager, false);

        initSdk();

        findViewById(R.id.share_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
            }
        });

        mDialog = new BottomSheetDialog(this);
        view = LayoutInflater.from(this).inflate(R.layout.layout_choose_share, null, false);
        checkbox = new int[]{R.id.check_property, R.id.check_pic, R.id.check_exercise};
        for(int id: checkbox){
            CheckBox b = view.findViewById(id);
            b.setOnCheckedChangeListener(this);
            b.setEnabled(false);
        }
        view.findViewById(R.id.check_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPropertySelected && !isPicSelected && !isExerciseSelected){
                    UIUtils.showToastSafe("请选择至少一项分享");
                    return;
                }
                doWeiboShare();
                mDialog.hide();
            }
        });
        mDialog.setContentView(view);

        mExerciseDialog = new BottomSheetDialog(this);
        view1 = LayoutInflater.from(this).inflate(R.layout.layout_exercise_share, null, false);
        textView = (TextView) view1.findViewById(R.id.share_text);
        view1.findViewById(R.id.check_exercise_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doExerciseShare();
                mExerciseDialog.hide();
            }
        });
        mExerciseDialog.setContentView(view1);

        ExerciseFragment.shareExercise.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shareExer = s;
                if(shareExer.length() > 20)
                    textView.setText(shareExer.substring(0, 20) + "...");
                else
                    textView.setText(shareExer);
                mExerciseDialog.show();
            }
        });

        ListFragment.getFragViewModel().isPropertyReady.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                CheckBox b = view.findViewById(checkbox[0]);
                b.setEnabled(aBoolean);
                CheckBox b1 = view.findViewById(checkbox[1]);
                b1.setEnabled(aBoolean);
            }
        });

        ExerciseFragment.getFragViewModel().isExerciseReady.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                CheckBox b = view.findViewById(checkbox[2]);
                b.setEnabled(aBoolean);
            }
        });
    }

    private void initSdk() {
        AuthInfo authInfo = new AuthInfo(this, Constant.APP_KY, Constant.REDIRECT_URL, Constant.SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
    }

    /**
     * 授权操作
     */
    private void startAuth() {
//auth
        mWBAPI.authorize(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                UIUtils.showToastSafe("微博授权成功");
            }

            @Override
            public void onError(UiError error) {
                UIUtils.showToastSafe("微博授权失败");
            }

            @Override
            public void onCancel() {
                UIUtils.showToastSafe("微博授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWBAPI != null) {
            mWBAPI.doResultIntent(data, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.hide();
    }

    @Override
    protected void onDestroy() {
        mDialog.dismiss();
        mExerciseDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onComplete() {
        UIUtils.showToastSafe("微博分享成功");
        mDialog.dismiss();
        mExerciseDialog.dismiss();
        for(int id: checkbox){
            CheckBox b = view.findViewById(id);
            b.setChecked(false);
        }
    }

    @Override
    public void onError(UiError error) {
        UIUtils.showToastSafe("微博分享失败");
        mDialog.dismiss();
        mExerciseDialog.dismiss();
    }

    @Override
    public void onCancel() {
        UIUtils.showToastSafe("微博分享取消");
        mDialog.dismiss();
        mExerciseDialog.dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getText().toString()){
            case "实体属性":
                isPropertySelected = b;
                break;
            case "知识图谱":
                isPicSelected = b;
                break;
            case "相关试题":
                isExerciseSelected = b;
                break;
            default:
                break;
        }
    }

    private void doWeiboShare(){
        WeiboMultiMessage message = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        String text = "我正在使用知清INTQ分享" + inst_name + "的相关知识~";
        textObject.text = text;
        message.textObject = textObject;

        MultiImageObject multiImageObject = new MultiImageObject();
        ArrayList<Uri> list = new ArrayList<>();
        if(isPropertySelected){
            Uri uri = getPropertyUri();
            if(uri != null)
                list.add(uri);
        }
        if(isPicSelected){
            Uri uri = getPicUri();
            if(uri != null)
                list.add(uri);
        }
        if(isExerciseSelected){
            Uri uri = getExerciseUri();
            if(uri != null)
                list.add(uri);
        }
        multiImageObject.imageList = list;
        message.multiImageObject = multiImageObject;

        mWBAPI.shareMessage(message, false);
    }

    private void doExerciseShare(){
        WeiboMultiMessage message = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        String text = "我正在使用知清INTQ分享" + inst_name + "的一道习题~\n" + shareExer;
        textObject.text = text;
        message.textObject = textObject;

        mWBAPI.shareMessage(message, false);
    }

    private Uri getPropertyUri(){
        try{
            File file = new File(getExternalFilesDir(null), "share_property.jpg");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(!file.exists())
                file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            Bitmap bitmap = transViewToBitmap(ListFragment.getView().findViewById(R.id.instance_list), getMaxSize(ListFragment.getView().findViewById(R.id.instance_list)));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return FileProvider.getUriForFile(this, "com.example.intq.fileprovider", file);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private Uri getPicUri(){
        File file = new File(getExternalFilesDir(null), "share_pic.jpg");
        try{
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(!file.exists())
                file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            Bitmap bitmap = getBitmapFromView(GraphFragment.getView());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return FileProvider.getUriForFile(this, "com.example.intq.fileprovider", file);
        }catch (IOException e){
            return null;
        }
    }
    private Uri getExerciseUri(){
        File file = new File(getExternalFilesDir(null), "share_exercise.jpg");
        try{
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if(!file.exists())
                file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            ExpandableListView listView = ExerciseFragment.getView().findViewById(R.id.act_main_expandable_list_view);
            ExerciseFragment.expandAll();
            Bitmap bitmap = transViewToBitmap(ExerciseFragment.getView().findViewById(R.id.act_main_expandable_list_view), getMaxSize(ExerciseFragment.getView().findViewById(R.id.act_main_expandable_list_view)));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            ExerciseFragment.reverseExpandAll();
            return FileProvider.getUriForFile(this, "com.example.intq.fileprovider", file);
        }catch (IOException e){
            return null;
        }
    }


    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(c);
        else
            c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    public Bitmap transViewToBitmap(View view, Pair<Integer, Integer> size) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(size.first, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(size.second, View.MeasureSpec.EXACTLY);
        view.measure(measuredWidth, measuredHeight);
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.WHITE); //如果不设置canvas画布为白色，则生成透明
        view.layout(0, 0, w, h);
        view.draw(canvas);
        return bmp;
    }

    public Pair<Integer, Integer> getMaxSize(View view) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(getScreenWidth(this), View.MeasureSpec.AT_MOST);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        view.measure(measuredWidth, measuredHeight);
        return new Pair<>(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
