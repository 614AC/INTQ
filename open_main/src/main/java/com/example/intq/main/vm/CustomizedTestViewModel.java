package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.ExtraExercise;
import com.example.intq.common.bean.ExtraOption;
import com.example.intq.common.bean.exercise.ExerciseList;
import com.example.intq.common.bean.exercise.ExerciseOption;
import com.example.intq.common.bean.exercise.SingleExerciseResult;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;

public class CustomizedTestViewModel extends WDViewModel<IMainRequest> {
    public MutableLiveData<Integer> qIndex = new MutableLiveData<>();
    public MutableLiveData<Boolean> qMode = new MutableLiveData<>();
    public MutableLiveData<Integer> limit = new MutableLiveData<>(5);
    public MutableLiveData<String> course = new MutableLiveData<>();
    public MutableLiveData<ExtraExercise> presentQuestion = new MutableLiveData<>();
    private List<ExtraExercise> tests;


    @Override
    protected void create() {
        super.create();
        qMode.setValue(true);
    }

    public void getCustomizedExercise(){
        request(iRequest.getCustomized(LOGIN_USER.getToken(), limit.getValue(), course.getValue()), new DataCall<ExerciseList>() {
            @Override
            public void success(ExerciseList data) {
                for(int i = 0; i < data.getQuestionList().size(); i++) {
                    SingleExerciseResult result = data.getQuestionList().get(i);
                    List<ExtraOption> options = new ArrayList<>();
                    for(int j = 0; j < result.getOptions().size(); j++){
                        options.add(new ExtraOption(j, result.getOptions().get(j), 0));
                    }
                    tests.add(new ExtraExercise(stringToInt(result.getqAnswer()), result.getqBody(), options));
                }
                qIndex.setValue(0);
                presentQuestion.setValue(tests.get(qIndex.getValue()));
            }

            @Override
            public void fail(ApiException data) {

            }
        });
    }

    private int stringToInt(String s) {
        return s.charAt(0) - 'A';
    }

    public void nextOne(){
        tests.set(qIndex.getValue(), presentQuestion.getValue());
        qIndex.setValue(qIndex.getValue() + 1);
        presentQuestion.setValue(tests.get(qIndex.getValue()));
    }

    public void previousOne(){
        tests.set(qIndex.getValue(), presentQuestion.getValue());
        qIndex.setValue(qIndex.getValue() - 1);
        presentQuestion.setValue(tests.get(qIndex.getValue()));
    }

    public void submit(){
        qMode.setValue(false);
    }
}
