package com.example.atilagapps.hellixdatamanager.StaffManager;

public class StaffInfo {

    private String StaffName;
    private String StaffContact;
    private String StaffEmail;
    private String StaffAddress;
    private String StaffSalary;

    public StaffInfo(String staffName, String staffContact, String staffEmail, String staffAddress, String staffSalary) {
        StaffName = staffName;
        StaffContact = staffContact;
        StaffEmail = staffEmail;
        StaffAddress = staffAddress;
        StaffSalary = staffSalary;
    }


    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getStaffContact() {
        return StaffContact;
    }

    public void setStaffContact(String staffContact) {
        StaffContact = staffContact;
    }

    public String getStaffEmail() {
        return StaffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        StaffEmail = staffEmail;
    }

    public String getStaffAddress() {
        return StaffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        StaffAddress = staffAddress;
    }

    public String getStaffSalary() {
        return StaffSalary;
    }

    public void setStaffSalary(String staffSalary) {
        StaffSalary = staffSalary;
    }
}
