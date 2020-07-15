package com.example.atilagapps.hellixdatamanager.Fragments;

public class RemainingStuBatchClass {

    String batchName,batchTime;
    int Count;

    public RemainingStuBatchClass(String batchName, String batchTime, int count) {
        this.batchName = batchName;
        this.batchTime = batchTime;
        Count = count;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
