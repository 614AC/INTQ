package com.vc.wd.main.utils;

import androidx.annotation.NonNull;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.vc.wd.common.bean.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TabConfigDataProvider extends AbstractDataProvider {
    private List<TabData> mData;
    private TabData mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public TabConfigDataProvider(int[] courseIndices) {
        mData = new LinkedList<>();

        HashSet<Integer> set = new HashSet<>();
        for (int i : courseIndices) {
            set.add(i);
            final Course course = new Course(i);
            final int viewType = 0;
            final String text = course.getNameChi();
            mData.add(new TabData(course.getIndex(), viewType, text));
        }
        mData.add(new TabData(-1, 0, "----- ⬇已删除标签⬇ -----"));
        for (int i = 0; i < Course.getCourseNumber(); ++i)
            if (!set.contains(i)) {
                final Course course = new Course(i);
                final int viewType = 0;
                final String text = course.getNameChi();
                mData.add(new TabData(course.getIndex(), viewType, text));
            }
    }

    public int[] dump() {
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < mData.size(); ++i) {
            int id = mData.get(i).getId();
            if (id < 0) break;
            data.add(id);
        }
        return data.stream().mapToInt(Integer::valueOf).toArray();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Data getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final TabData item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        Collections.swap(mData, toPosition, fromPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public void removeItem(int position) {
        //noinspection UnnecessaryLocalVariable
        final TabData removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    public static final class TabData extends Data {

        private final int mId;
        @NonNull
        private final String mText;
        private final int mViewType;
        private boolean mPinned;

        TabData(int id, int viewType, @NonNull String text) {
            mId = id;
            mViewType = viewType;
            mText = text;
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }

        @Override
        public int getId() {
            return mId;
        }

        @NonNull
        @Override
        public String toString() {
            return mText;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }
    }
}