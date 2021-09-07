/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.intq.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemState;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.example.intq.main.R;
import com.example.intq.main.utils.AbstractDataProvider;
import com.example.intq.main.utils.TabConfigDataProvider;

public class TabConfigAdapter
        extends RecyclerView.Adapter<TabConfigAdapter.TabConfigViewHolder>
        implements DraggableItemAdapter<TabConfigAdapter.TabConfigViewHolder> {
    private static final String TAG = "TabDraggableItemAdapter";
    private int mItemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT;
    private String[] itemColors = {
            "#0B89CF",
            "#A7647A",
            "#AACFDD",
            "#B4A9BC",
            "#F4EEEE",
            "#227BDA",
            "#00979f",
            "#0f7e79",
            "#cbd1e6",
            "#aacb92",
    };
    private AbstractDataProvider mProvider;

    public static class TabConfigViewHolder extends AbstractDraggableItemViewHolder {
        public FrameLayout mContainer;
        public View mDragHandle;
        public TextView mTextView;
        public ImageView mImageView;

        public TabConfigViewHolder(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mDragHandle = v.findViewById(R.id.drag_handle);
            mTextView = v.findViewById(android.R.id.text1);
            mImageView = v.findViewById(R.id.item_image);
        }
    }

    public TabConfigAdapter(AbstractDataProvider dataProvider) {
        mProvider = dataProvider;

        // DraggableItemAdapter requires stable ID, and also
        // have to implement the getItemId() method appropriately.
        setHasStableIds(true);
    }

    public void setItemMoveMode(int itemMoveMode) {
        mItemMoveMode = itemMoveMode;
    }

    @Override
    public long getItemId(int position) {
        return mProvider.getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return mProvider.getItem(position).getViewType();
    }

    @NonNull
    @Override
    public TabConfigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_grid_item, parent, false);
        return new TabConfigViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TabConfigViewHolder holder, int position) {
        final AbstractDataProvider.Data item = mProvider.getItem(position);

        // set text
        if (item.getId() >= 0)
            holder.mTextView.setText(item.getText());
        else
            holder.mImageView.setBackgroundResource(R.drawable.trash_bin);

        // set item view width
        Context context = holder.itemView.getContext();

        // set background resource (target view ID: container)
        final DraggableItemState dragState = holder.getDragState();

        if (dragState.isUpdated()) {
            if (dragState.isActive()) {
                if (item.getId() >= 0)
                    holder.mTextView.setBackgroundColor(Color.parseColor(
                            itemColors[item.getId()]));
                // need to clear drawable state here to get correct appearance of the dragging item.
                holder.mContainer.getForeground().setState(new int[]{});
            } else if (dragState.isDragging()) {
                if (item.getId() >= 0)
                    holder.mTextView.setBackgroundColor(Color.parseColor(
                            itemColors[item.getId()]));
            } else {
                if (item.getId() >= 0)
                    holder.mTextView.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mProvider.getCount();
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        Log.d(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");

        if (mItemMoveMode == RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT) {
            mProvider.moveItem(fromPosition, toPosition);
        } else {
            mProvider.swapItem(fromPosition, toPosition);
        }
    }

    @Override
    public boolean onCheckCanStartDrag(@NonNull TabConfigViewHolder holder, int position, int x, int y) {
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(@NonNull TabConfigViewHolder holder, int position) {
        // no drag-sortable range specified
        return null;
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        if (draggingPosition == 0 && mProvider.getItem(dropPosition).getId() < 0
                || (mItemMoveMode == RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
                && draggingPosition == 0 && mProvider.getItem(1).getId() < 0)
                || mProvider.getItem(draggingPosition).getId() < 0 && dropPosition == 0)
            return false;
        return true;
    }

    @Override
    public void onItemDragStarted(int position) {
        notifyDataSetChanged();
    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
        notifyDataSetChanged();
    }
}