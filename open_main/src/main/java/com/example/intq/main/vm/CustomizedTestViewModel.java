package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.exercise.ExerciseList;
import com.example.intq.common.bean.exercise.ExerciseOption;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.main.request.IMainRequest;

import java.util.List;

public class CustomizedTestViewModel extends WDViewModel<IMainRequest> {
    public MutableLiveData<Integer> qIndex = new MutableLiveData<>(0);
    public MutableLiveData<String> qBody = new MutableLiveData<>("");
    public MutableLiveData<List<String>> qOptions = new MutableLiveData<>();
    public MutableLiveData<String> qAnswer = new MutableLiveData<>();
    public MutableLiveData<Integer> limit = new MutableLiveData<>(5);

    @Override
    protected void create() {
        super.create();

    }

    private void getCustomizedExercise(){
        request(iRequest.getCustomized(LOGIN_USER.getToken(), limit.getValue()), new DataCall<ExerciseList>() {
            @Override
            public void success(ExerciseList data) {

            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }
}
