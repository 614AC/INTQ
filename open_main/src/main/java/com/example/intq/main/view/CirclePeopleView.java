package com.example.intq.main.view;

import static com.example.intq.main.fragment.InstanceGraphFragment.mContext;
import static com.example.intq.main.fragment.InstanceGraphFragment.childSub;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.intq.common.bean.instance.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CirclePeopleView extends View {

    private String[] colors = {"#BE9300", "#EC9D0C", "#B36301", "#B3B001", "#B3016E", "#862BB5", "#09B301", "#65BF7A", "#01ADB3", "#E8C91A"};
    private Paint paint;
    private float XPoint;
    private float YPoint;
//    private int[] radius = null;
    private List<Instance> lists = new ArrayList<>();
    private static final int DISTANCE_CIRCLE = 350;

    public CirclePeopleView(Context context) {
        super(context);
    }

    public CirclePeopleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePeopleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetFieldInfo(List<Instance> lists) {
        this.lists = lists;
//        radius = new int[lists.size()];
//        for (int i = 0; i < lists.size(); i++) {
////            Random r = new Random();
////            radius[i] = r.nextInt(160) + DISTANCE_CIRCLE;
//            radius[i] = DISTANCE_CIRCLE;
//        }
    }

    private void init() {
        this.paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.parseColor("#BE9300"));
        this.paint.setStrokeWidth(dip2px(mContext, 2));
        XPoint = getWidth() / 2;
        YPoint = getHeight() / 2;
        canvas.drawCircle(XPoint, YPoint, dip2px(mContext, 22), paint);

        this.paint.setColor(Color.parseColor("#333333"));
        this.paint.setTextSize(dip2px(mContext, 10));
        canvas.drawText(childSub, XPoint - 40, YPoint + dip2px(mContext, 32), paint);
        int count = lists.size();
        if (lists != null && count > 0) {
            double pere = Math.PI * 2 / count;
            for (int i = 0; i < count; i++) {
                paint.setColor(Color.parseColor(colors[i % colors.length]));
                paint.setStrokeWidth(dip2px(mContext, 2));
                int r = lists.get(i).getSymbolSize();
                if (r < 8) {
                    r = 8;
                } else if (r > 22) {
                    r = 22;
                }
                canvas.drawCircle(XPoint + (float) Math.cos(pere * i + 5) * DISTANCE_CIRCLE, YPoint + (float) Math.sin(pere * i + 5) * DISTANCE_CIRCLE, dip2px(mContext, r), paint);
                this.paint.setColor(Color.parseColor("#666666"));
                this.paint.setStrokeWidth(0.5f);
                canvas.drawLine(XPoint + (float) Math.cos(pere * i + 5) * dip2px(mContext, 22), YPoint + (float) Math.sin(pere * i + 5) * dip2px(mContext, 22), XPoint + (float) Math.cos(pere * i + 5) * (DISTANCE_CIRCLE - dip2px(mContext, r)), YPoint + (float) Math.sin(pere * i + 5) * (DISTANCE_CIRCLE - dip2px(mContext, r)), paint);
            }
            for (int i = 0; i < count; i++) {
                int r = lists.get(i).getSymbolSize();
                if (r < 8) {
                    r = 8;
                } else if (r > 22) {
                    r = 22;
                }
                this.paint.setColor(Color.parseColor("#333333"));
                this.paint.setTextSize(dip2px(mContext, 8));
                this.paint.setTextAlign(Paint.Align.CENTER);
//                canvas.drawText(lists.get(i).getName(), XPoint + (float) Math.cos(pere * i + 5) * DISTANCE_CIRCLE - 30, YPoint + (float) Math.sin(pere * i + 5) * DISTANCE_CIRCLE + dip2px(mContext, r + 10), paint);
                canvas.drawText(lists.get(i).getName(), XPoint + (float) Math.cos(pere * i + 5) * DISTANCE_CIRCLE, YPoint + (float) Math.sin(pere * i + 5) * DISTANCE_CIRCLE + 60, paint);
                this.paint.setColor(Color.parseColor("#856d72"));
                this.paint.setTextSize(dip2px(mContext, 6));
                this.paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(lists.get(i).getPredicate_label(), ((XPoint + (float) Math.cos(pere * i + 5) * dip2px(mContext, 22)) + XPoint + (float) Math.cos(pere * i + 5) * (DISTANCE_CIRCLE - dip2px(mContext, r))) / 2, (YPoint + (float) Math.sin(pere * i + 5) * dip2px(mContext, 22) + YPoint + (float) Math.sin(pere * i + 5) * (DISTANCE_CIRCLE - dip2px(mContext, r))) / 2, paint);
            }
        }

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }


}
