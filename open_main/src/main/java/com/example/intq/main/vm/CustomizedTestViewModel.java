package com.example.intq.main.vm;

import android.annotation.SuppressLint;
import android.os.Message;

import androidx.databinding.ObservableField;
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
import android.os.Handler;
import java.util.logging.LogRecord;

public class CustomizedTestViewModel extends WDViewModel<IMainRequest> {
    public MutableLiveData<Integer> qIndex = new MutableLiveData<>();
    public MutableLiveData<Boolean> qMode = new MutableLiveData<>();
    public MutableLiveData<Integer> limit = new MutableLiveData<>(10);
    public MutableLiveData<String> course = new MutableLiveData<>();
    public MutableLiveData<ExtraExercise> presentQuestion = new MutableLiveData<>();
    private List<ExtraExercise> tests;
    public MutableLiveData<Boolean> fail = new MutableLiveData<>(false);
    public MutableLiveData<Integer> corrects = new MutableLiveData<>();
    public MutableLiveData<Integer> progress = new MutableLiveData<>();

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case 200:
                    qIndex.setValue(0);
                    presentQuestion.setValue(tests.get(qIndex.getValue()));
                    progress.setValue(-1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void create() {
        super.create();
        qMode.setValue(true);
        tests = new ArrayList<>();
    }

    public void getCustomizedExercise(){
        request(iRequest.getCustomized(LOGIN_USER.getToken(), limit.getValue(), course.getValue()), new DataCall<ExerciseList>() {
            @Override
            public void success(ExerciseList data) {
                new Thread(() -> {
                    for(int i = 0; i < data.getQuestionList().size(); i++) {
                        SingleExerciseResult result = data.getQuestionList().get(i);
                        List<ExtraOption> options = new ArrayList<>();
                        for(int j = 0; j < result.getOptions().size(); j++){
                            options.add(new ExtraOption(j, result.getOptions().get(j), 0));
                        }
                        tests.add(new ExtraExercise(stringToInt(result.getqAnswer()), result.getqBody(), options));
                    }
                    Message message = new Message();
                    message.what = 200;
                    handler.sendMessage(message);
                }).start();

            }

            @Override
            public void fail(ApiException data) {
                fail.setValue(true);
            }
        });
    }

    private int stringToInt(String s) {
        return s.charAt(0) - 'A';
    }

    public void choose(int position){
        if(!qMode.getValue())
            return;
        ExtraExercise extraExercise = presentQuestion.getValue();
        boolean flag = false;
        if(extraExercise.getChosen() < 0 && qMode.getValue() && qIndex.getValue() > progress.getValue()){
            flag = true;
        }
        if(extraExercise != null && extraExercise.getChosen() >= 0 && position != extraExercise.getChosen()){
            extraExercise.getOptions().get(extraExercise.getChosen()).setType(0);
        }
        extraExercise.getOptions().get(position).setType(1);
        presentQuestion.setValue(extraExercise);
        if(flag){
            int p = progress.getValue();
            while (p + 1 < limit.getValue() && tests.get(p + 1).getChosen() >= 0)
                p++;
            progress.setValue(p);
        }
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
        int correctNum = 0;
        for(int i = 0; i < tests.size(); i++){
            ExtraExercise extraExercise = tests.get(i);
            for(ExtraOption option: extraExercise.getOptions()){
                if(option.getType() == 1)
                    if(option.getIndex() != extraExercise.getAnswer())
                        option.setType(3);
                    else
                        correctNum++;
                if(option.getIndex() == extraExercise.getAnswer())
                    option.setType(2);
            }
        }
        corrects.setValue(correctNum);
        presentQuestion.setValue(tests.get(qIndex.getValue()));
    }

    public void retry(){
        fail.setValue(false);
        getCustomizedExercise();
    }
}
