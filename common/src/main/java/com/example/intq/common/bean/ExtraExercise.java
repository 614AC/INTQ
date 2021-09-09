package com.example.intq.common.bean;

import java.util.List;

public class ExtraExercise {
    private int answer;
    private String body;
    private List<ExtraOption> options;

    public ExtraExercise(int answer, String body, List<ExtraOption> options) {
        this.answer = answer;
        this.body = body;
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<ExtraOption> getOptions() {
        return options;
    }

    public void setOptions(List<ExtraOption> options) {
        this.options = options;
    }

    public int getChosen(){
        for(ExtraOption option: options)
            if(option.getType() == 1)
                return option.getIndex();
        return -1;
    }
}
