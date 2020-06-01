package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.os.Parcel;
import android.os.Parcelable;

public class StaffPaymentClass implements Parcelable {

    private String StaffName;
    private String ForMonth;
    private String PendingAmount;
    private String TableName;


    public StaffPaymentClass(String staffName, String pendingAmount, String forMonth,String tableName) {
        StaffName = staffName;
        ForMonth = forMonth;
        PendingAmount = pendingAmount;
        TableName=tableName;
    }


    protected StaffPaymentClass(Parcel in) {
        StaffName = in.readString();
        ForMonth = in.readString();
        PendingAmount = in.readString();
        TableName=in.readString();
    }

    public static final Creator<StaffPaymentClass> CREATOR = new Creator<StaffPaymentClass>() {
        @Override
        public StaffPaymentClass createFromParcel(Parcel in) {
            return new StaffPaymentClass(in);
        }

        @Override
        public StaffPaymentClass[] newArray(int size) {
            return new StaffPaymentClass[size];
        }
    };

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getForMonth() {
        return ForMonth;
    }

    public void setForMonth(String forMonth) {
        ForMonth = forMonth;
    }

    public String getPendingAmount() {
        return PendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        PendingAmount = pendingAmount;
    }


    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StaffName);
        dest.writeString(ForMonth);
        dest.writeString(PendingAmount);
        dest.writeString(TableName);
    }
}
