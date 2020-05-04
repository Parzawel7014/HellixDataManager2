package com.example.atilagapps.hellixdatamanager.Students;

public class RegSubClass {

    private String mBatchName;
    private String mBatchTime;
    private String mRemainingAmt;
    private String mTotalPaidAmt;


    public RegSubClass(String mBatchName, String mBatchTime, String mTotalPaidAmt, String mRemainingAmt) {
        this.mBatchName = mBatchName;
        this.mBatchTime = mBatchTime;
        this.mRemainingAmt = mRemainingAmt;
        this.mTotalPaidAmt = mTotalPaidAmt;
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

    public String getmRemainingAmt() {
        return mRemainingAmt;
    }

    public void setmRemainingAmt(String mRemainingAmt) {
        this.mRemainingAmt = mRemainingAmt;
    }

    public String getmTotalPaidAmt() {
        return mTotalPaidAmt;
    }

    public void setmTotalPaidAmt(String mTotalPaidAmt) {
        this.mTotalPaidAmt = mTotalPaidAmt;
    }
}
