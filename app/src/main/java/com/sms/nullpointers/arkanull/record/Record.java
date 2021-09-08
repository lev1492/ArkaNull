package com.sms.nullpointers.arkanull.record;

import android.os.Parcel;
import android.os.Parcelable;


public class Record implements Parcelable {
    private int score;
    private String mail;
    private String displayName;

    public final static String SCORE = "score";
    public final static String MAIL = "mail";
    public final static String DISPLAY_NAME = "displayName";


    public Record(String mail , String displayName , int score ){
        this.mail = mail;
        this.displayName = displayName;
        this.score = score;
    }

    public Record(){

    }

    protected Record(Parcel in) {
        score = in.readInt();
        mail = in.readString();
        displayName = in.readString();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(score);
        parcel.writeString(mail);
        parcel.writeString(displayName);
    }
}
