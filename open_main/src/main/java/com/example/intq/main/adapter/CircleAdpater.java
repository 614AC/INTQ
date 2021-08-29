package com.example.intq.main.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.intq.main.BR;
import com.example.intq.main.R;
import com.example.intq.common.bean.Circle;
import com.example.intq.common.core.WDRecyclerAdapter;
import com.example.intq.main.databinding.CircleItemBinding;
import com.example.intq.common.util.DateUtils;
import com.example.intq.common.util.StringUtils;
import com.example.intq.common.util.recycleview.SpacingItemDecoration;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class CircleAdpater extends WDRecyclerAdapter<Circle> {

    @Override
    protected int getLayoutId() {
        return R.layout.circle_item;
    }

    @Override
    protected void initBindingField(ViewGroup parent,ViewDataBinding binding) {
        super.initBindingField(parent,binding);
        CircleItemBinding b = (CircleItemBinding) binding;
        ImageAdapter imageAdapter = new ImageAdapter();
        int space = parent.getContext().getResources()
                .getDimensionPixelSize(R.dimen.dip_10);;//图片间距
        GridLayoutManager gridLayoutManager = new GridLayoutManager(parent.getContext(),
                3);
        b.gridView.addItemDecoration(new SpacingItemDecoration(space));
        b.gridView.setLayoutManager(gridLayoutManager);
        b.gridView.setAdapter(imageAdapter);

        //由于没有自定义holder，需要灵活使用复杂对象binding寄存
        b.setVariable(BR.imageAdapter,imageAdapter);
        b.setVariable(BR.layoutManager,gridLayoutManager);
    }

    @Override
    protected void bindView(ViewDataBinding binding,Circle circle, int position) {
        CircleItemBinding b = (CircleItemBinding) binding;
        //不建议调用binding.setVariable()，
        // 由于xml暂时不包含时间计算，不能进行列表嵌套处理等复杂操作，
        // 所以尽量不要给自己增加难度，像下面这样写代码会让你略去xml中填充值碰到不必要的麻烦
        b.image.setImageURI(Uri.parse(circle.getHeadPic()));
        b.nickname.setText(circle.getNickName());
        try {
            b.time.setText(DateUtils.dateFormat(new Date(circle.getCreateTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        b.text.setText(circle.getContent());

        if (StringUtils.isEmpty(circle.getImage())){
            b.gridView.setVisibility(View.GONE);
        }else{
            b.gridView.setVisibility(View.VISIBLE);
            String[] images = circle.getImage().split(",");

            int imageCount = images.length;

            int colNum;//列数
            if (imageCount == 1){
                colNum = 1;
            }else if (imageCount == 2||imageCount == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            b.getImageAdapter().clear();//清空
            b.getImageAdapter().addStringListAll(Arrays.asList(images));
            b.getLayoutManager().setSpanCount(colNum);//设置列数


            b.getImageAdapter().notifyDataSetChanged();
        }

        if (circle.getWhetherGreat() == 1){
            b.priseImage.setImageResource(R.drawable.common_btn_prise_s);
        }else{
            b.priseImage.setImageResource(R.drawable.common_btn_prise_n);
        }

        b.priseCount.setText(circle.getGreatNum()+"");
        b.priseLayout.setTag(position);
        b.priseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greatListener!=null){
                    int p = (int) v.getTag();
                    greatListener.great(p,getItem(p));
                }
            }
        });
    }

    private GreatListener greatListener;

    public void setGreatListener(GreatListener greatListener) {
        this.greatListener = greatListener;
    }

    public interface GreatListener{
        void great(int position, Circle circle);
    }
}
