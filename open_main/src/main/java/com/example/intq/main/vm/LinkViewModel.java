package com.example.intq.main.vm;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.intq.common.bean.instance.Link;
import com.example.intq.common.bean.instance.LinkInstanceResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.core.http.NetworkManager;
import com.example.intq.common.util.Constant;
import com.example.intq.common.util.ImageUtils;
import com.example.intq.common.util.MD5Utils;
import com.example.intq.common.util.StringUtils;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import okhttp3.RequestBody;

public class LinkViewModel extends WDViewModel<IMainRequest> {
    private final String[] courseMapping = {"chinese", "math", "english", "physics", "chemistry", "biology", "politics", "history", "geo"};
    public ObservableField<Integer> courseId = new ObservableField<>();
    public ObservableField<String> context = new ObservableField<>();
    public MutableLiveData<List<Link>> links = new MutableLiveData<>();
    public MutableLiveData<Boolean> enabled = new MutableLiveData<>(true);
    // o for handwriting and 1 for general
    public MutableLiveData<Integer> recognizeType = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> unchangeable = new MutableLiveData<>(false);

    public void link() {
        if (!unchangeable.getValue()) {
            if (enabled.getValue()) {
                enabled.setValue(false);
                RequestBody body = NetworkManager.convertJsonBody(new String[]{"course", "context"}, new String[]{courseMapping[courseId.get()], context.get()});
                request(iRequest.getLinkInstance(body), new DataCall<LinkInstanceResult>() {
                    @Override
                    public void success(LinkInstanceResult data) {
                        unchangeable.setValue(!unchangeable.getValue());
                        links.setValue(data.getLinkInstance());
                    }
                    @Override
                    public void fail(ApiException data) {
                        UIUtils.showToastSafe("??????????????????????????????????????????");
                        enabled.setValue(true);
                    }
                });
            }
        }
        else {
            unchangeable.setValue(false);
        }
    }

    public void turnToInstance(String instName, String uri) {
        Bundle bundle = new Bundle();
        bundle.putString("inst_name", instName);
        bundle.putString("course", courseMapping[courseId.get()]);
        bundle.putString("uri", uri);
        intentByRouter(Constant.ACTIVITY_URL_INSTANCE, bundle);
    }

    public void recognizeFrom(Bitmap bitmap) {
        final String APPID = "5f266178";
        final String APIKey = "b6c9c09d5b7a9566928f6c2062b749b8";
        final String PARAM = "eyJsYW5ndWFnZSI6ImNufGVuIiwibG9jYXRpb24iOiJmYWxzZSJ9";
        final String[] xunfei = {"https://webapi.xfyun.cn/v1/service/v1/ocr/handwriting", "https://webapi.xfyun.cn/v1/service/v1/ocr/general"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                StringBuffer output = new StringBuffer();
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(xunfei[recognizeType.getValue()]).openConnection();
                    //??????????????????,??????????????????
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    //??????????????????,??????:
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    //Post??????????????????,??????????????????false
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    //?????????????????????:
                    conn.setRequestProperty("X-Appid", APPID);
                    int cur = (int) (System.currentTimeMillis() / 1000);
                    conn.setRequestProperty("X-CurTime", Integer.toString(cur));
                    conn.setRequestProperty("X-Param", PARAM);
                    conn.setRequestProperty("X-CheckSum", MD5Utils.md5Simple(APIKey + Integer.toString(cur) + PARAM));
                    System.out.println("X-Appid -> " + APPID);
                    System.out.println("X-CurTime -> " + cur);
                    System.out.println("X-CheckSum -> " + MD5Utils.md5Simple(APIKey + Integer.toString(cur) + PARAM));
                    //???????????????????????????????????????...
                    String body = "image=" + ImageUtils.bitmapToBase64(bitmap).replace("\n", "");
                    //???????????????
                    conn.connect();
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.write(body.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    if (conn.getResponseCode() == 200) {
                        // ??????????????????????????????
                        InputStream is = conn.getInputStream();
                        // ???????????????????????????
                        ByteArrayOutputStream message = new ByteArrayOutputStream();
                        // ?????????????????????
                        int len = 0;
                        // ???????????????
                        byte buffer[] = new byte[1024];
                        // ???????????????????????????????????????
                        while ((len = is.read(buffer)) != -1) {
                            // ??????????????????????????????os?????????
                            message.write(buffer, 0, len);
                        }
                        // ????????????
                        is.close();
                        message.close();
                        // ???????????????
                        msg = message.toString();
                    }
                    System.out.println(msg);
                    JSONObject object = JSONObject.parseObject(msg);
                    if (object.getString("code").equals("0")) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray blocks = (JSONArray) data.get("block");
                        for (Object block : blocks) {
                            JSONObject b = (JSONObject) block;
                            if (b.get("type").equals("text")) {
                                JSONArray lines = (JSONArray) b.get("line");
                                for (Object line : lines) {
                                    JSONObject l = (JSONObject) line;
                                    if (l.getFloat("confidence") > 0.8f) {
                                        JSONArray words = (JSONArray) l.get("word");
                                        for (Object word : words) {
                                            JSONObject w = (JSONObject) word;
                                            output.append(w.get("content"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    context.set(output.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}