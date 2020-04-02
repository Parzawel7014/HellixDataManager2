package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentClass implements Parcelable {

private String StudentName;

    public StudentClass(String studentName) {
        StudentName = studentName;
    }

    protected StudentClass(Parcel in) {
        StudentName = in.readString();
    }

    public static final Creator<StudentClass> CREATOR = new Creator<StudentClass>() {
        @Override
        public StudentClass createFromParcel(Parcel in) {
            return new StudentClass(in);
        }

        @Override
        public StudentClass[] newArray(int size) {
            return new StudentClass[size];
        }
    };

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StudentName);
    }
}
