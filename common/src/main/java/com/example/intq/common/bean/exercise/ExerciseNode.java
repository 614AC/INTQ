package com.example.intq.common.bean.exercise;
import java.util.List;

public class ExerciseNode {
    /**
     * GroupName : 水果
     * GroupIcon :
     * ChildData : [{"ChildName":"香蕉","ChildIcon":"","Number":5},{"ChildName":"苹果","ChildIcon":"","Number":3},{"ChildName":"西瓜","ChildIcon":"","Number":9}]
     */

    private String GroupName;
    private String GroupIcon;
    private int answer;
    private List<ExerciseOption> ChildData;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public String getGroupIcon() {
        return GroupIcon;
    }

    public void setGroupIcon(String GroupIcon) {
        this.GroupIcon = GroupIcon;
    }

    public List<ExerciseOption> getChildData() {
        return ChildData;
    }

    public void setChildData(List<ExerciseOption> ChildData) {
        this.ChildData = ChildData;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
