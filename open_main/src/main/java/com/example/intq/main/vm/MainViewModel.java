package com.example.intq.main.vm;

import android.widget.RadioGroup;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.util.Constant;
import com.example.intq.main.BuildConfig;
import com.example.intq.main.request.IMainRequest;

public class MainViewModel extends WDViewModel<IMainRequest> {
    public MutableLiveData<Integer> cId = new MutableLiveData<>();
    public ObservableField<Boolean> debug = new ObservableField<>();

    @Override
    protected void create() {
        super.create();
        debug.set(BuildConfig.DEBUG);
    }

    public void onCheckedChanged(RadioGroup group, int checkId) {
        cId.setValue(checkId);
    }

    public void debug() {
        intentByRouter(Constant.ACTIVITY_URL_DEBUG);
    }
}
