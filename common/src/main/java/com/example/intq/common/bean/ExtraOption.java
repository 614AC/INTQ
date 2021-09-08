package com.example.intq.common.bean;

public class ExtraOption {
    private int index;
    private String choice;
    // 0 for common, 1 for chosen, 2 for correct, 3 for wrong
    private int type;

    public ExtraOption(int index, String choice, int type) {
        this.index = index;
        this.choice = choice;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
