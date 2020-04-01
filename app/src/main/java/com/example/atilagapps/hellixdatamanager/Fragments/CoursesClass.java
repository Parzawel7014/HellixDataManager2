package com.example.atilagapps.hellixdatamanager.Fragments;

public class CoursesClass {

    private String mSubject;
    private String mTime;
    private String mAmount;


    public CoursesClass(String mSubject, String mTime, String mAmount) {
        this.mSubject = mSubject;
        this.mTime = mTime;
        this.mAmount=mAmount;
    }

    public String getmAmount() {
        return mAmount;
    }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
