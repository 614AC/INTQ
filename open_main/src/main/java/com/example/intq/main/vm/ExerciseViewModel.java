package com.example.intq.main.vm;

import androidx.lifecycle.MutableLiveData;

import com.example.intq.common.bean.exercise.ExerciseList;
import com.example.intq.common.bean.exercise.ExerciseNode;
import com.example.intq.common.bean.exercise.ExerciseOption;
import com.example.intq.common.bean.exercise.SingleExerciseResult;
import com.example.intq.common.bean.instance.InstInfoResult;
import com.example.intq.common.bean.instance.Instance;
import com.example.intq.common.core.DataCall;
import com.example.intq.common.core.WDFragViewModel;
import com.example.intq.common.core.exception.ApiException;
import com.example.intq.common.util.UIUtils;
import com.example.intq.main.request.IMainRequest;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewModel extends WDFragViewModel<IMainRequest> {
    public MutableLiveData<List<ExerciseNode>> exerciseNodes = new MutableLiveData<>();
    public MutableLiveData<String> instName = new MutableLiveData<>();
    public static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static final String[] capitalAlphabet = {"A", "B", "C", "D", "E", "F", "G", "H"};
    public static final String alphabetString = "ABCDEFGH";
    public void getData() {
        request(iRequest.getExercise(this.instName.getValue()), new DataCall<ExerciseList>() {
            @Override
            public void success(ExerciseList data) {
                List<ExerciseNode> ens = new ArrayList<>();
                for (int i = 0; i < data.getQuestionList().size(); i++) {
                    ExerciseNode en = new ExerciseNode();
                    SingleExerciseResult ser = data.getQuestionList().get(i);
                    en.setGroupIcon("exercise");
                    en.setGroupName(ser.getqBody());
                    String ans = ser.getqAnswer();
                    if (alphabetString.contains(ans.substring(0, 1))) {
                        en.setAnswer(alphabetString.indexOf(ans.substring(0, 1)));
                    }
                    else {
                        for (int t = 0; t < 8; t++) {
                            if (ans.contains(capitalAlphabet[t])) {
                                ans = capitalAlphabet[t];
                                break;
                            }
                        }
                        en.setAnswer(alphabetString.indexOf(ans));
                    }
                    List<ExerciseOption> eops = new ArrayList<>();
//                    System.out.println(ans);
//                    System.out.println(en.getAnswer());
                    for (int j = 0; j < ser.getOptions().size(); j++) {
                        ExerciseOption eop = new ExerciseOption();
                        eop.setChildName(ser.getOptions().get(j).split(capitalAlphabet[j] + "\\.")[1]);
                        eop.setChildIcon("icon_" + alphabet[j]);
                        eop.setNumber(j);
                        eops.add(eop);
                    }
                    en.setChildData(eops);
                    ens.add(en);
                }
                exerciseNodes.setValue(ens);
            }

            @Override
            public void fail(ApiException data) {
                UIUtils.showToastSafe("网络连接失败，请检查网络连接");
            }
        });
    }
}
