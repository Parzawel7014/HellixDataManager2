package com.example.atilagapps.hellixdatamanager.Reciept;

public class PaymentInfoClass {

    private String AmountToPay;
    private String PaidAmount;
    private String RemainingAmount;
    private String PaidWith;
    private String ReceivedBy;
    private String PaymentDate;


    public PaymentInfoClass(String amountToPay, String paidAmount, String remainingAmount, String paidWith, String receivedBy,String paymentDate) {
        AmountToPay = amountToPay;
        PaidAmount = paidAmount;
        RemainingAmount = remainingAmount;
        PaidWith = paidWith;
        ReceivedBy = receivedBy;
        PaymentDate=paymentDate;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getAmountToPay() {
        return AmountToPay;
    }

    public void setAmountToPay(String amountToPay) {
        AmountToPay = amountToPay;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getRemainingAmount() {
        return RemainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        RemainingAmount = remainingAmount;
    }

    public String getPaidWith() {
        return PaidWith;
    }

    public void setPaidWith(String paidWith) {
        PaidWith = paidWith;
    }

    public String getReceivedBy() {
        return ReceivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        ReceivedBy = receivedBy;
    }
}
