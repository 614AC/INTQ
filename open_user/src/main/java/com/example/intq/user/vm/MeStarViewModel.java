package com.example.intq.user.vm;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.WDViewModel;
import com.example.intq.user.request.IUserRequest;

public class MeStarViewModel extends WDViewModel<IUserRequest> {
    public MutableLiveData<Integer> cId = new MutableLiveData<>();

    public void click(View view) {cId.setValue(view.getId());}
}
