package com.example.atilagapps.hellixdatamanager.StaffManager;

public class StaffRegisteredSubjectClass {

    private String mBatchName;
    private String mBatchTime;

    public StaffRegisteredSubjectClass(String mBatchName, String mBatchTime) {
        this.mBatchName = mBatchName;
        this.mBatchTime = mBatchTime;
    }

    public String getmBatchName() {
        return mBatchName;
    }

    public void setmBatchName(String mBatchName) {
        this.mBatchName = mBatchName;
    }

    public String getmBatchTime() {
        return mBatchTime;
    }

    public void setmBatchTime(String mBatchTime) {
        this.mBatchTime = mBatchTime;
    }
}
