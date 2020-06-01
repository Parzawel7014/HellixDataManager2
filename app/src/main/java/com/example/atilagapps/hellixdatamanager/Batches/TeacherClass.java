package com.example.atilagapps.hellixdatamanager.Batches;

public class TeacherClass {
    String TeacherName;
    String TeacherID;


    public TeacherClass(String teacherName, String teacherID) {
        TeacherName = teacherName;
        TeacherID = teacherID;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }
}
