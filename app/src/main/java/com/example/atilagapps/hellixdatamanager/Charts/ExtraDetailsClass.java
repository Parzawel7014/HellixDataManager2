package com.example.atilagapps.hellixdatamanager.Charts;

public class ExtraDetailsClass {

    String date,paymentType,paymentPerson,paymentAmount,paymentDesc,paymentTypeCode;


    public ExtraDetailsClass(String date, String paymentType, String paymentPerson, String paymentAmount, String paymentDesc,String paymentTypeCode) {
        this.date = date;
        this.paymentType = paymentType;
        this.paymentPerson = paymentPerson;
        this.paymentAmount = paymentAmount;
        this.paymentDesc = paymentDesc;
        this.paymentTypeCode=paymentTypeCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentPerson() {
        return paymentPerson;
    }

    public void setPaymentPerson(String paymentPerson) {
        this.paymentPerson = paymentPerson;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }


    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }
}
