package com.example.intq.main.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.QAChat;
import com.example.intq.common.bean.question.SolveResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.util.Observable;

public class QAViewModel extends WDViewModel<IMainRequest> {
    private final String[] courseMapping = {"chinese", "math", "english", "physics", "chemistry", "biology", "politics", "history", "geometry"};
    public ObservableField<Integer> courseId = new ObservableField<>();
    public ObservableField<String> inputQuestion = new ObservableField<>();
    public MutableLiveData<QAChat> qaChat = new MutableLiveData<>();

    public void ask(){
        qaChat.setValue(new QAChat(true, inputQuestion.get()));
        request(iRequest.solve(qaChat.getValue().getContent(), courseMapping[courseId.get()]), new DataCall<SolveResult>() {
            @Override
            public void success(SolveResult data) {
                qaChat.setValue(new QAChat(false, data.getAnswer()));
            }

            @Override
            public void fail(ApiException data) {
                UIUtils.showToastSafe("网络连接失败，请检查网络连接");
            }
        });
    }
}
