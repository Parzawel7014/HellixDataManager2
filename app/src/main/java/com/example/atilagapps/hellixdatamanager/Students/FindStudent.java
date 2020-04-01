package com.example.atilagapps.hellixdatamanager.Students;

import android.os.Parcel;
import android.os.Parcelable;

public class FindStudent implements Parcelable {

    private String mStudentName;


    public FindStudent(String mStudentName) {
        this.mStudentName = mStudentName;
    }


    protected FindStudent(Parcel in) {
        mStudentName = in.readString();
    }

    public static final Creator<FindStudent> CREATOR = new Creator<FindStudent>() {
        @Override
        public FindStudent createFromParcel(Parcel in) {
            return new FindStudent(in);
        }

        @Override
        public FindStudent[] newArray(int size) {
            return new FindStudent[size];
        }
    };

    public String getmStudentName() {
        return mStudentName;
    }

    public void setmStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStudentName);
    }
}
