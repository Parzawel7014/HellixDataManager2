package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentClass implements Parcelable {

private String StudentName;
private String RegFeePaymentStatus;

private String RemainingPayment;
private String StudentId;




    public StudentClass(String studentName,String studentId,String remainingPayment,String RegFeeStatus) {
        StudentName = studentName;

        RemainingPayment=remainingPayment;
        StudentId=studentId;
        RegFeePaymentStatus=RegFeeStatus;
    }

    public StudentClass(Parcel in) {
        StudentName = in.readString();
        StudentId=in.readString();
        RemainingPayment=in.readString();
        RegFeePaymentStatus=in.readString();
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

    public String getRegFeePaymentStatus() {
        return RegFeePaymentStatus;
    }

    public void setRegFeePaymentStatus(String regFeePaymentStatus) {
        RegFeePaymentStatus = regFeePaymentStatus;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }


    public String getRemainingPayment() {
        return RemainingPayment;
    }

    public void setRemainingPayment(String remainingPayment) {
        RemainingPayment = remainingPayment;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StudentName);
        dest.writeString(RegFeePaymentStatus);
      //dest.writeString(MonthlyAmountStatus);
        dest.writeString(StudentId);
        dest.writeString(RemainingPayment);
    }
}
