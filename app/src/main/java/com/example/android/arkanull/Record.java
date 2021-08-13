package com.example.android.arkanull;

public class Record {
    private int score;
    private String displayName;


    public Record(String displayName , int score ){
        this.displayName = displayName;
        this.score = score;
    }

    public Record(){

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String mail() {
        return displayName;
    }

    public void mail(String mail) {
        this.displayName = displayName;
    }
}
