package com.example.intq.main.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.List;

@Route(path = Constant.ACTIVITY_URL_LINK)
public class LinkActivity extends WDActivity<LinkViewModel, ActivityLinkBinding> {

    private static final String[] m={"语文","数学","英语","物理","化学", "生物", "政治", "历史", "地理"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_link;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = findViewById(R.id.link_text);

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
                String context = viewModel.context.get();
                if(context == null)
                    return;
                if(links.size() == 0)
                    return;

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
                            viewModel.turnToInstance(l.getEntity());
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
    }
}
