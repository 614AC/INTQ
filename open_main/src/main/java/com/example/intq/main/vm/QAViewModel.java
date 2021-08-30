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
    private boolean enabled = true;

    public void ask(){
        if(enabled){
            qaChat.setValue(new QAChat(true, inputQuestion.get()));
            enabled = false;
            inputQuestion.set("");
            request(iRequest.solve(qaChat.getValue().getContent(), courseMapping[courseId.get()]), new DataCall<SolveResult>() {
                @Override
                public void success(SolveResult data) {
                    if(data.getAnswer() == null || data.getAnswer().length() == 0 || data.getAnswer().equals(" ") || data.getAnswer().equals("“") || data.getAnswer().equals(")"))
                        qaChat.setValue(new QAChat(false, "Interesting question."));
                    else
                        qaChat.setValue(new QAChat(false, data.getAnswer()));
                    enabled = true;
                }

                @Override
                public void fail(ApiException data) {
                    UIUtils.showToastSafe("网络连接失败，请检查网络连接");
                    enabled = true;
                }
            });
        }

    }
}
