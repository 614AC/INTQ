package com.example.intq.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.core.content.ContextCompat;

import java.util.List;

import com.example.intq.main.R;
import com.example.intq.common.bean.exercise.ExerciseNode;
import com.example.intq.common.bean.exercise.ExerciseOption;
import com.example.intq.main.viewholder.ViewHolder;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    /** 数据集合 */
    private List<ExerciseNode> mExpandableModeList;
    /** 上下文 */
    private Context mContext;
    /** ViewHolder */
    private ViewHolder mViewHolder;

    public MyExpandableAdapter(List<ExerciseNode> expandableModeList, Context context) {
        mExpandableModeList = expandableModeList;
        mContext = context;
        mViewHolder = mViewHolder.getInstance();
    }

    /**
     * @return 返回组item数量
     */
    @Override
    public int getGroupCount() {
        return mExpandableModeList == null ? 0 : mExpandableModeList.size();
    }

    /**
     * 根据出入的组groupPosition返回子item的数量。
     *
     * @param groupPosition groupPosition
     * @return 返回子item的数量。
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mExpandableModeList == null) return 0;
        List<ExerciseOption> childDataBeans = mExpandableModeList.get(groupPosition).getChildData();
        return childDataBeans == null ? 0 : childDataBeans.size();
    }

    /**
     * 获取组实体对象
     *
     * @param groupPosition groupPosition
     * @return 返回组实体对象
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mExpandableModeList == null ? null : mExpandableModeList.get(groupPosition);
    }

    /**
     * 获取子实体对象
     *
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @return 返回子实体对象
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mExpandableModeList == null) return null;
        List<ExerciseOption> childDataBeans = mExpandableModeList.get(groupPosition).getChildData();
        return childDataBeans == null ? null : childDataBeans.get(childPosition);
    }

    /**
     * 获取组id,我理解为获取组的唯一ID，一般自己返回groupPosition即可
     *
     * @param groupPosition groupPosition
     * @return Position
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     * 获取子ID
     *
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @return 返回子id
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }


    /**
     * 用来判断ExpandableListView内容id是否有效的,我也不太明白，它具体有什么作用，设置返回true和false的效果是一样的。
     *
     * @return true or false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取给定分组的视图，主要重写的方法。
     *
     * @param groupPosition groupPosition
     * @param isExpanded    该组是展开状态还是收起状态
     * @param convertView   convertView
     * @param parent        parent
     * @return View
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
        }
        // 获取组的实体对象
        ExerciseNode expandableMode = mExpandableModeList.get(groupPosition);
        // 获取组名
        String groupName = expandableMode.getGroupName();
        // 获取组icon名字
        String iconName = expandableMode.getGroupIcon();
        // 调用getImageMipmapRes方法获取对应的icon资源
        int iconRes = getImageMipmapRes(mContext, iconName);
        // 获取Drawable对象
        Drawable drawableLeft = ContextCompat.getDrawable(mContext, iconRes);
        mViewHolder.setImage(convertView, R.id.item_group_icon_iv, drawableLeft);
        // 设置TextView的文字
        mViewHolder.setText(convertView, R.id.item_group_name_tv, groupName);

        // 设置组item展开或者关闭的右图片
        Drawable drawableRight = ContextCompat.getDrawable(mContext, R.mipmap.icon_right);
        if (isExpanded) {
            // 展开
            drawableRight = ContextCompat.getDrawable(mContext, R.mipmap.icon_down);
        }
        mViewHolder.setImageTextRight(convertView, R.id.item_group_name_tv, drawableRight);

        return convertView;
    }

    /**
     * 获取给定分组给定子位置的数据用的视图，主要重写的方法。
     *
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @param isLastChild   是否为改组最后一个子视图
     * @param convertView   convertView
     * @param parent        parent
     * @return View
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child, parent, false);
        }
        ExerciseNode expandableMode = mExpandableModeList.get(groupPosition);
        List<ExerciseOption> childDataBeans = expandableMode.getChildData();
        ExerciseOption childDataBean = childDataBeans.get(childPosition);
        // 获取子名字
        String childName = childDataBean.getChildName();
        // 获取对应的子icon
        String childIcon = childDataBean.getChildIcon();
        // 获取子icon名字对应的图片资源
        int childIconRes = getImageMipmapRes(mContext, childIcon);
        // 获取Drawable对象
        Drawable drawable = ContextCompat.getDrawable(mContext, childIconRes);
        // 设置icon
        mViewHolder.setImage(convertView, R.id.item_child_icon_iv, drawable);
        // 设置文字
        mViewHolder.setText(convertView, R.id.item_child_name_tv, childName);
        return convertView;
    }

    /**
     * 子item是否可点击
     * 当isChildSelectable方法返回false的时候，子item不可点击，且不会绘制分割线
     *
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @return true可点击 false不可点击
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 通过文件名，获取对应的图片资源
     *
     * @param context  context
     * @param filename filename
     * @return 对应的图片资源
     */
    public int getImageMipmapRes(Context context, String filename) {
        return context.getResources().getIdentifier(filename, "mipmap", context.getPackageName());
    }

    public void setExpandableModeList(List<ExerciseNode> expandableModeList) {
        mExpandableModeList = expandableModeList;
    }
}