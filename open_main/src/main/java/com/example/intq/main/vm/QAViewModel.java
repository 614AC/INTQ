package com.example.intq.main.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.QAChat;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.main.request.IMainRequest;

import java.util.Observable;

public class QAViewModel extends WDViewModel<IMainRequest> {
    public ObservableField<Integer> courseId = new ObservableField<>();
    public ObservableField<String> inputQuestion = new ObservableField<>();
    public MutableLiveData<QAChat> qaChat = new MutableLiveData<>();

    public void ask(){
        System.out.println("ask!");
        qaChat.setValue(new QAChat(true, inputQuestion.get()));
    }
}
