package com.example.atilagapps.hellixdatamanager.Batches;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectAdapter implements Parcelable {

    private String msubject;
    private String mTime;
    private String mTeacher;

    public SubjectAdapter(String subject,String time,String teacher){
        msubject=subject;
        mTime=time;
        mTeacher=teacher;
    }

    protected SubjectAdapter(Parcel in) {
        msubject = in.readString();
        mTime = in.readString();
        mTeacher = in.readString();
    }

    public static final Creator<SubjectAdapter> CREATOR = new Creator<SubjectAdapter>() {
        @Override
        public SubjectAdapter createFromParcel(Parcel in) {
            return new SubjectAdapter(in);
        }

        @Override
        public SubjectAdapter[] newArray(int size) {
            return new SubjectAdapter[size];
        }
    };



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msubject);
        dest.writeString(mTime);
        dest.writeString(mTeacher);
    }
}
