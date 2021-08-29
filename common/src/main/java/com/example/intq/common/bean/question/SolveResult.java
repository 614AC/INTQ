package com.example.intq.common.bean.question;

public class SolveResult {
    private String instLabel, answer;

    public SolveResult(String instLabel, String answer) {
        this.instLabel = instLabel;
        this.answer = answer;
    }

    public String getInstLabel() {
        return instLabel;
    }

    public void setInstLabel(String instLabel) {
        this.instLabel = instLabel;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
