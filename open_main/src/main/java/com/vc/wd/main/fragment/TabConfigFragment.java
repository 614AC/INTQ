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

package com.vc.wd.main.fragment;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.vc.wd.common.core.WDFragment;
import com.vc.wd.main.R;
import com.vc.wd.main.activity.TabConfigActivity;
import com.vc.wd.main.adapter.TabConfigAdapter;
import com.vc.wd.main.databinding.FragmentTabConfigBinding;
import com.vc.wd.main.utils.TabConfigDataProvider;
import com.vc.wd.main.vm.EmptyFragmentViewModel;

public class TabConfigFragment extends WDFragment<EmptyFragmentViewModel, FragmentTabConfigBinding> {
    private RecyclerView mView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TabConfigAdapter mTabsAdapter;
    private RecyclerView.Adapter mWrapperAdapter;
    private RecyclerViewDragDropManager mTabsManager;

    public TabConfigFragment() {
        super();
    }

    @Override
    protected EmptyFragmentViewModel initFragViewModel() {
        return new EmptyFragmentViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_config;
    }

    @Override
    public void initView(Bundle bundle) {
        mView = binding.tabsEnabled;
        int spanCount = 4;
        final GridLayoutManager gridLayoutManager =
                new GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false);
        mLayoutManager = gridLayoutManager;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                long id = mTabsAdapter.getItemId(position);
                return id < 0 ? spanCount : 1;
            }
        });

        // drag & drop manager
        mTabsManager = new RecyclerViewDragDropManager();
        mTabsManager.setDraggingItemShadowDrawable(
                (NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z3));


        // Start dragging after long press
        mTabsManager.setInitiateOnLongPress(true);
        mTabsManager.setInitiateOnMove(false);
        mTabsManager.setLongPressTimeout(500);

        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mTabsManager.setDragStartItemAnimationDuration(250);
        mTabsManager.setDraggingItemAlpha(0.8f);
        mTabsManager.setDraggingItemScale(1.3f);
        mTabsManager.setDraggingItemRotation(5.0f);

        //adapter
        final TabConfigAdapter enabledAdapter = new TabConfigAdapter(getDataProvider());
        mTabsAdapter = enabledAdapter;
        mWrapperAdapter = mTabsManager.createWrappedAdapter(enabledAdapter);      // wrap for dragging

        GeneralItemAnimator animator = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.

        mView.setLayoutManager(mLayoutManager);
        mView.setAdapter(mWrapperAdapter);  // requires *wrapped* adapter
        mView.setItemAnimator(animator);

        // additional decorations
        //noinspection StatementWithEmptyBody
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
            mView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z1)));
        }

        mTabsManager.attachRecyclerView(mView);

        binding.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int mode = (isChecked)
                        ? RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP
                        : RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT;

                mTabsManager.setItemMoveMode(mode);
                mTabsAdapter.setItemMoveMode(mode);

                Snackbar.make(getView(),
                        "标签移动模式" + (isChecked ? "替换" : "默认"), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        mTabsManager.cancelDrag();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mTabsManager != null) {
            mTabsManager.release();
            mTabsManager = null;
        }

        if (mView != null) {
            mView.setItemAnimator(null);
            mView.setAdapter(null);
            mView = null;
        }
        mTabsAdapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    public TabConfigDataProvider getDataProvider() {
        return ((TabConfigActivity) getActivity()).getDataProvider();
    }
}