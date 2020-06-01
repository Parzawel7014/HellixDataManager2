package com.example.atilagapps.hellixdatamanager.SendSMS;

public class SMSInfoClass {

    private String date;
    private String month;
    private String year;
    private String contact;
    private String name;

    public SMSInfoClass(String contact,String date, String month, String year,String name) {
        this.date = date;
        this.month = month;
        this.year = year;
        this.contact = contact;
        this.name=name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
