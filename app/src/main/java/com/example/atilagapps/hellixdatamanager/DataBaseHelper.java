package com.example.atilagapps.hellixdatamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="HelixDB.db";

    public static final String STUDENT_TABLE="student_table";
    public static final String SUBJECT_TABLE="subject_table";

    public static final String STU_COL_1="ID";
    public static final String STU_COL_2="NAME";
    public static final String STU_COL_3="ADDRESS";
    public static final String STU_COL_4="CONTACT_INFO";
    public static final String STU_COL_5="BATCH_TIMING";
    public static final String STU_COL_6="SUBJECTS";
    public static final String STU_COL_7="ADMISSION_DATE";
    public static final String STU_COL_8="GENDER";
    public static final String STU_COL_9="EDUCATION";
    public static final String STU_COL_10="PURPOSE";
    public static final String STU_COL_11="CASTE";

    public static final String SUB_COL_2="SUBJECTS";
    public static final String SUB_COL_3="DURATION(months)";
    public static final String SUB_COL_4="FACULTY";
    public static final String SUB_COL_1="SUBJECT_ID";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+ STUDENT_TABLE+"(" + STU_COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+STU_COL_2+" TEXT, "+
                STU_COL_3+" TEXT, "+STU_COL_4+" TEXT, "+STU_COL_5+" TEXT, "+STU_COL_6+" TEXT, "+STU_COL_7+" TEXT, "+
                STU_COL_8+" TEXT, "+STU_COL_9+" TEXT, "+STU_COL_10+" TEXT, "+STU_COL_11+" TEXT "+")");

        sqLiteDatabase.execSQL("create table SUBJECT_TABLE(SUB_COL_1 INTEGER PRIMARY KEY AUTOINCREMENT,SUB_COL_2 TEXT,SUB_COL_3 TEXT,SUB_COL_4 TEXT)");


        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Mathematics\",\"6\",\"A\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Reasoning\",\"4\",\"B\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"English Grammar\",\"4\",\"C\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Marathi Grammar\",\"3\",\"D\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Geography\",\"3\",\"E\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"History\",\"2\",\"F\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Political Science\",\"2\",\"G\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Economics\",\"2\",\"H\");");
        sqLiteDatabase.execSQL("INSERT INTO SUBJECT_TABLE(SUB_COL_2,SUB_COL_3,SUB_COL_4) VALUES(\"Science\",\"4\",\"I\");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SUBJECT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ STUDENT_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insert_PersonalData_Stu_Table(String name,String address,String contact,String gender,String caste){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(STU_COL_2,name);
        contentValues.put(STU_COL_3,address);
        contentValues.put(STU_COL_4,contact);
        contentValues.put(STU_COL_8,gender);
        contentValues.put(STU_COL_11,caste);


        long result=db.insert(STUDENT_TABLE,null,contentValues);
        return result != -1;
    }


   /* public boolean insert_PersonalData_Stu_Table(String name,String address,String contact,String batch_timing,String subject,
                                                 String admission_date,String gender,String education,String purpose,String caste){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(STU_COL_2,name);
        contentValues.put(STU_COL_3,address);
        contentValues.put(STU_COL_4,contact);
        //contentValues.put(STU_COL_5,batch_timing);
        //contentValues.put(STU_COL_6,subject);
        //contentValues.put(STU_COL_7,admission_date);
        contentValues.put(STU_COL_8,gender);
        //contentValues.put(STU_COL_9,education);
        //contentValues.put(STU_COL_10,purpose);
        contentValues.put(STU_COL_11,caste);


        long result=db.insert(STUDENT_TABLE,null,contentValues);
        return result != -1;
    }*/



    /*public boolean insertDataInSubjectTable(String subject,String duration,String faculty){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues1=new ContentValues();
        contentValues1.put(SUB_COL_2,subject);
        contentValues1.put(SUB_COL_3,duration);
        contentValues1.put(SUB_COL_4,faculty);

        long result1=db.insert(SUBJECT_TABLE,null,contentValues1);
        return result1 != -1;

    }
*/
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+SUBJECT_TABLE,null);
        return res;
    }



}
