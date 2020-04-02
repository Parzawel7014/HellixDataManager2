package com.example.atilagapps.hellixdatamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.TuitionFess.StudentClass;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="test2.db";

    public static final String STUDENT_TABLE="student_table";
    public static final String SUBJECT_TABLE="subject_table";
    public static final String BATCHES_TABLE="batches_table";


    public static final String Mathematics_TABLE="Mathematics_table";
    public static final String Reasoning_TABLE="Reasoning_table";
    public static final String English_Grammar_TABLE="English_Grammar_table";
    public static final String Marathi_Grammar_TABLE="Marathi_Grammar_table";
    public static final String Geography_TABLE="Geography_table";
    public static final String History_TABLE="History_table";
    public static final String Political_Science_TABLE="Political_Science_table";
    public static final String Economics_TABLE="Economics_table";
    public static final String Science_TABLE="Science_table";



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
    public static final String SUB_COL_3="FEES";
    public static final String SUB_COL_4="FACULTY";
    public static final String SUB_COL_1="SUBJECT_ID";



    public static final String BATCH_COL_1="SR_NO_ID";
    public static final String BATCH_COL_4="Admission_Date";
    public static final String BATCH_COL_2="STUDENT_ID";
    public static final String BATCH_COL_3="Paid";
    public static final String BATCH_COL_5="UnPaid";

    //public static final String BATCH_COL_1="BATCH_ID";

    public static final String ALL_BATCH_COL_1="ALL_BATCH_ID";
    public static final String ALL_BATCH_COL_2="BATCH_NAME";
    public static final String ALL_BATCH_COL_3="BATCH_TIME";
    public static final String ALL_BATCH_COL_4="TeacherName";
    public static final String ALL_BATCH_COL_5="TableName";




    public static final String Sub_Table_COL_2="Student_Name";
    public static final String Sub_Table_COL_3="Student_Id";
    public static final String Sub_Table_COL_4="Amount_Paid";
    public static final String Sub_Table_COL_5="Flag";
    public static final String Sub_Table_COL_6="Batch_Timing";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ STUDENT_TABLE+"(" + STU_COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+STU_COL_2+" TEXT, "+
                STU_COL_3+" TEXT, "+STU_COL_4+" TEXT, "+STU_COL_5+" TEXT, "+STU_COL_6+" TEXT, "+STU_COL_7+" TEXT, "+
                STU_COL_8+" TEXT, "+STU_COL_9+" TEXT, "+STU_COL_10+" TEXT, "+STU_COL_11+" TEXT "+")");

        db.execSQL("CREATE TABLE "+ SUBJECT_TABLE+"("+SUB_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SUB_COL_2+" TEXT, "+
                SUB_COL_3+" TEXT, "+SUB_COL_4+" TEXT "+")");


        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+" VALUES"+"("+"\"MATHEMATICS\""+","+"\"500\""+","+"\"A\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"REASONING\""+","+"\"500\""+","+"\"B\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"ENGLISH GRAMMAR\""+","+"\"500\""+","+"\"C\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"MARATHI GRAMMAR\""+","+"\"500\""+","+"\"D\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"GEOGRAPHY\""+","+"\"500\""+","+"\"E\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"HISTORY\""+","+"\"500\""+","+"\"F\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"POLITICAL SCIENCE\""+","+"\"500\""+","+"\"G\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"ECONOMIC\""+","+"\"500\""+","+"\"H\"" +")");
        db.execSQL("INSERT INTO "+ SUBJECT_TABLE+"("+SUB_COL_2 +","+SUB_COL_3 +","+SUB_COL_4 +")"+"VALUES"+"("+"\"SCIENCE\""+","+"\"500\""+","+"\"I\"" +")");



        db.execSQL("CREATE TABLE "+BATCHES_TABLE+"("+ALL_BATCH_COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ALL_BATCH_COL_2+" TEXT, "+ALL_BATCH_COL_3+" TEXT,"+ALL_BATCH_COL_4+" TEXT, "+ALL_BATCH_COL_5+" TEXT"+")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SUBJECT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ STUDENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ BATCHES_TABLE);
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


   /*public boolean insert_PersonalData_Stu_Table(String name,String address,String contact,String batch_timing,String subject,
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
    }
*/


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

    public List<String> getDistinctDialogueLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  DISTINCT "+ALL_BATCH_COL_2 + " FROM " + BATCHES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public List<String> getDialogueLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SUBJECT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public ArrayList<SubjectAdapter> getDialogueLabelsAdapter(){
        ArrayList<SubjectAdapter> labels = new ArrayList();

        String selectQuery = "SELECT * FROM " + BATCHES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new SubjectAdapter(cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }



    public ArrayList<FindStudent> getStudentName(){
        ArrayList<FindStudent> labels=new ArrayList();

        String selectQuery = "SELECT * FROM " + STUDENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new FindStudent(cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;


    }


    public  boolean CreateBatch(String BatchName,String BatchTime,String BatchTeacher){
        SQLiteDatabase db=this.getWritableDatabase();
        String newBatchName=BatchName.replace(" ","_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime=newBatchTime.replace(" ","_");

        String TableName=newBatchName+newBatchTime;
        long result1;
        db.execSQL("CREATE TABLE " + TableName + "(" + BATCH_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + BATCH_COL_2 + " INTEGER, " + BATCH_COL_3 + " BOOLEAN, " +
                   BATCH_COL_4+" TEXT, "+BATCH_COL_5+" TEXT, " +"FOREIGN KEY" + "(" + BATCH_COL_2 + ")" + " REFERENCES " + STUDENT_TABLE + "(" + STU_COL_1 + ")" + ")" );

        ContentValues contentValues=new ContentValues();
        contentValues.put(ALL_BATCH_COL_2,BatchName);
        contentValues.put(ALL_BATCH_COL_3,BatchTime);
        contentValues.put(ALL_BATCH_COL_4,BatchTeacher);
        contentValues.put(ALL_BATCH_COL_5,TableName);
        result1=db.insert(BATCHES_TABLE,null,contentValues);
        return result1 != -1;


    }


    public boolean insertIntoTables(String TableName,String StudentName,String date){

        String StudentID = null;
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT "+ STU_COL_1 + " FROM " + STUDENT_TABLE + " where "+STU_COL_2+"="+"'"+StudentName+"'",null);

        if (cursor.moveToFirst()) {
            do {
                 StudentID=cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        ContentValues cn=new ContentValues();

        cn.put(BATCH_COL_2,StudentID);
        cn.put(BATCH_COL_4,date);
        long result1=db.insert(TableName,null,cn);

        db.close();

        return result1 != -1;

    }





    public List<String> getBatchTime(String SubjectName){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + BATCHES_TABLE+" where "+ ALL_BATCH_COL_2 + "="+"'"+SubjectName+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public boolean isTableExist(String TableName){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TableName+"'";
        try (Cursor cursor=db.rawQuery(query,null)){
            if (cursor!=null){
                if (cursor.getCount()>0){
                    return true;
                }
            }
            return false;
        }
    }


    public String getAmount(String SubjectName){
        String labels="";
        SQLiteDatabase db=this.getReadableDatabase();
        String query="select * from " + SUBJECT_TABLE +" where "+SUB_COL_2+"="+"'"+SubjectName+"'";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels=(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }



    public ArrayList<StudentClass> getAllStudents(String TableName){
        ArrayList<StudentClass> labels=new ArrayList();

        String selectQuery = "SELECT "+ STUDENT_TABLE+"."+STU_COL_2  +" FROM " + TableName +
                " INNER JOIN "+STUDENT_TABLE +" ON "+ TableName+"."+BATCH_COL_2+"="+STUDENT_TABLE+"."+STU_COL_1 ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new StudentClass(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;


    }



}
