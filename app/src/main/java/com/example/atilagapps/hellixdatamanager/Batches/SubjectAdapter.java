package com.example.atilagapps.hellixdatamanager.Batches;

public class SubjectAdapter {

    private String msubject;
    private String mTime;
    private String mTeacher;

    public SubjectAdapter(String subject,String time,String teacher){
        msubject=subject;
        mTime=time;
        mTeacher=teacher;
    }

    public String getMsubject() {
        return msubject;
    }

    public void setMsubject(String msubject) {
        this.msubject = msubject;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmTeacher() {
        return mTeacher;
    }

    public void setmTeacher(String mTeacher) {
        this.mTeacher = mTeacher;
    }
}
