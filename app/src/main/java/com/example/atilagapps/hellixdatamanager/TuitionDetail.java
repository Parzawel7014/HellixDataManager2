package com.example.atilagapps.hellixdatamanager;

public class TuitionDetail {

    String TuiName,TuiAdd,TuiPhone,TuiEmail,TuiAdmin1,TuiAdmin2;

    public TuitionDetail(String tuiName, String tuiAdd, String tuiPhone, String tuiEmail, String tuiAdmin1, String tuiAdmin2) {
        TuiName = tuiName;
        TuiAdd = tuiAdd;
        TuiPhone = tuiPhone;
        TuiEmail = tuiEmail;
        TuiAdmin1 = tuiAdmin1;
        TuiAdmin2 = tuiAdmin2;
    }

    public String getTuiName() {
        return TuiName;
    }

    public void setTuiName(String tuiName) {
        TuiName = tuiName;
    }

    public String getTuiAdd() {
        return TuiAdd;
    }

    public void setTuiAdd(String tuiAdd) {
        TuiAdd = tuiAdd;
    }

    public String getTuiPhone() {
        return TuiPhone;
    }

    public void setTuiPhone(String tuiPhone) {
        TuiPhone = tuiPhone;
    }

    public String getTuiEmail() {
        return TuiEmail;
    }

    public void setTuiEmail(String tuiEmail) {
        TuiEmail = tuiEmail;
    }

    public String getTuiAdmin1() {
        return TuiAdmin1;
    }

    public void setTuiAdmin1(String tuiAdmin1) {
        TuiAdmin1 = tuiAdmin1;
    }

    public String getTuiAdmin2() {
        return TuiAdmin2;
    }

    public void setTuiAdmin2(String tuiAdmin2) {
        TuiAdmin2 = tuiAdmin2;
    }
}
