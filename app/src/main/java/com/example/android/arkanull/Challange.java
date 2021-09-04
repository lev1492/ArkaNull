package com.example.android.arkanull;

import android.os.Parcel;
import android.os.Parcelable;

public class Challange implements Parcelable {

    String namePlayer1;
    String scorePlayer1;
    String namePlayer2;
    String scorePlaer2;

    protected Challange(Parcel in) {
        namePlayer1 = in.readString();
        scorePlayer1 = in.readString();
        namePlayer2 = in.readString();
        scorePlaer2 = in.readString();
    }

    public static final Creator<Challange> CREATOR = new Creator<Challange>() {
        @Override
        public Challange createFromParcel(Parcel in) {
            return new Challange(in);
        }

        @Override
        public Challange[] newArray(int size) {
            return new Challange[size];
        }
    };

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getScorePlayer1() {
        return scorePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public String getScorePlaer2() {
        return scorePlaer2;
    }

    public Challange( ) {

    }

    public Challange(String namePlayer1, String scorePlayer1, String namePlayer2, String scorePlaer2) {
        this.namePlayer1 = namePlayer1;
        this.scorePlayer1 = scorePlayer1;
        this.namePlayer2 = namePlayer2;
        this.scorePlaer2 = scorePlaer2;
    }

    public void setPlayer1(String namePlayer1, String scorePlayer1) {
        this.namePlayer1 = namePlayer1;
        this.scorePlayer1 = scorePlayer1;
    }

    public void setPlayer2( String namePlayer2, String scorePlaer2) {
        this.namePlayer2 = namePlayer2;
        this.scorePlaer2 = scorePlaer2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namePlayer1);
        parcel.writeString(scorePlayer1);
        parcel.writeString(namePlayer2);
        parcel.writeString(scorePlaer2);
    }
}
