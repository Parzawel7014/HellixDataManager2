package com.example.atilagapps.hellixdatamanager.Students;

public class StudentInfo {

    private String mStudentCast;
    private String mStudentContact;
    private String mStudentAddress;
    private String mStudentEducation;
    private String mStudentEmail;
    private String mStudentGender;

    public StudentInfo( String mStudentContact,String mStudentEmail,String mStudentGender, String mStudentAddress, String mStudentEducation, String mStudentCast) {
        this.mStudentCast = mStudentCast;
        this.mStudentContact = mStudentContact;
        this.mStudentAddress = mStudentAddress;
        this.mStudentEducation = mStudentEducation;
        this.mStudentEmail = mStudentEmail;
        this.mStudentGender=mStudentGender;
    }

    public String getmStudentCast() {
        return mStudentCast;
    }

    public void setmStudentCast(String mStudentCast) {
        this.mStudentCast = mStudentCast;
    }

    public String getmStudentContact() {
        return mStudentContact;
    }

    public void setmStudentContact(String mStudentContact) {
        this.mStudentContact = mStudentContact;
    }

    public String getmStudentAddress() {
        return mStudentAddress;
    }

    public void setmStudentAddress(String mStudentAddress) {
        this.mStudentAddress = mStudentAddress;
    }

    public String getmStudentEducation() {
        return mStudentEducation;
    }

    public void setmStudentEducation(String mStudentEducation) {
        this.mStudentEducation = mStudentEducation;
    }

    public String getmStudentEmail() {
        return mStudentEmail;
    }

    public void setmStudentEmail(String mStudentEmail) {
        this.mStudentEmail = mStudentEmail;
    }

    public String getmStudentGender() {
        return mStudentGender;
    }

    public void setmStudentGender(String mStudentGender) {
        this.mStudentGender = mStudentGender;
    }
}
