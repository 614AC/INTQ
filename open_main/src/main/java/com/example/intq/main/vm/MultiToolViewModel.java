package com.example.intq.main.vm;

import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.util.Constant;
import com.example.intq.main.request.IMainRequest;

public class MultiToolViewModel extends WDFragViewModel<IMainRequest> {

    public void gotoQA(){
        intentByRouter(Constant.ACTIVITY_URL_QA);
    }

    public void gotoLink(){
        intentByRouter(Constant.ACTIVITY_URL_QA);
    }
}
