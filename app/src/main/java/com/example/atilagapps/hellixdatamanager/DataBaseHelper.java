package com.example.atilagapps.hellixdatamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.TeacherClass;
import com.example.atilagapps.hellixdatamanager.Charts.ExtraDetailsClass;
import com.example.atilagapps.hellixdatamanager.Charts.ExtraInExClass;
import com.example.atilagapps.hellixdatamanager.Fragments.RemainingStuBatchClass;
import com.example.atilagapps.hellixdatamanager.Reciept.PaymentInfoClass;
import com.example.atilagapps.hellixdatamanager.SendSMS.SMSInfoClass;
import com.example.atilagapps.hellixdatamanager.StaffManager.StaffInfo;
import com.example.atilagapps.hellixdatamanager.StaffManager.StaffPaymentClass;
import com.example.atilagapps.hellixdatamanager.StaffManager.StaffRegisteredSubjectClass;
import com.example.atilagapps.hellixdatamanager.StaffManager.Staff_Class;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.RegSubClass;
import com.example.atilagapps.hellixdatamanager.Students.StudentInfo;
import com.example.atilagapps.hellixdatamanager.TuitionFess.PaymentRecievedSMSClass;
import com.example.atilagapps.hellixdatamanager.TuitionFess.ReceiptMonthClass;
import com.example.atilagapps.hellixdatamanager.TuitionFess.StudentClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "HellixDataManage.db";

    public static final String STUDENT_TABLE = "student_table";
    public static final String SUBJECT_TABLE = "subject_table";
    public static final String BATCHES_TABLE = "batches_table";
    public static final String RECEIPT_NO_TABLE = "Receipt_num_table";
    public static final String TUITION_INFO_TABLE = "tuition_info_table";
    public static final String TEACHER_TABLE = "Teacher_table";

    public static final String INCOME_EXPENSE_TABLE = "Income_Expense_Table";
    //public static final String INCOME_EXPENSE_TABLE2 = "Income_Expense_Table";


    public static final String STU_COL_1 = "ID";
    public static final String STU_COL_2 = "NAME";
    public static final String STU_COL_3 = "ADDRESS";
    public static final String STU_COL_4 = "CONTACT_INFO";
    public static final String STU_COL_5 = "EnrolledBatchID";
    public static final String STU_COL_6 = "Email";
    //public static final String STU_COL_7="ADMISSION_DATE";
    public static final String STU_COL_8 = "GENDER";
    public static final String STU_COL_9 = "EDUCATION";
    public static final String STU_COL_10 = "PURPOSE";
    public static final String STU_COL_11 = "CASTE";
    public static final String STU_COL_12 = "RegistrationId";
    public static final String STU_COL_13 = "PROFILE_IMAGE";

    public static final String SUB_COL_2 = "SUBJECTS";
    public static final String SUB_COL_3 = "FEES";
    public static final String SUB_COL_4 = "FACULTY";
    public static final String SUB_COL_1 = "SUBJECT_ID";


    public static final String BATCH_COL_1 = "SR_NO_ID";
    public static final String BATCH_COL_4 = "Admission_Date";
    public static final String BATCH_COL_2 = "STUDENT_ID";
    public static final String BATCH_COL_3 = "REG_FEE_STATUS";
    public static final String BATCH_COL_5 = "Registration_Amount_Paid";
    public static final String BATCH_COL_6 = "Total_Paid";
    public static final String BATCH_COL_7 = "Remaining_Amount";
    public static final String BATCH_COL_8 = "Monthly_fee_status";
    public static final String BATCH_COL_9 = "RegFeeAmt_Remaining";
    public static final String BATCH_COL_10 = "ActiveStatus";
    public static final String BATCH_COL_11 = "Staff_Name";
    public static final String BATCH_COL_12 = "Staff_ID";
    public static final String BATCH_COL_13 = "Registration_Amount_Paid_Date";


    public static final String Receipt_Col_1 = "SrNo";
    public static final String Receipt_Col_2 = "Receipt_No";
    public static final String Receipt_Col_3 = "StudentId";
    public static final String Receipt_Col_4 = "Month";
    public static final String Receipt_Col_5 = "Year";


    public static final String TUITION_INFO_COL1 = "SrNo";
    public static final String TUITION_INFO_COL2 = "Tuition_Name";
    public static final String TUITION_INFO_COL3 = "Tuition_Phone";
    public static final String TUITION_INFO_COL4 = "Tuition_Email";
    public static final String TUITION_INFO_COL5 = "Tuition_Address";
    public static final String TUITION_INFO_COL6 = "Tuition_Admin_1";
    public static final String TUITION_INFO_COL7 = "Tuition_Admin_2";
    public static final String TUITION_INFO_COL8 = "Tuition_ProPic";


    public static final String Fess_COl_1 = "SR_NO";
    public static final String Fess_COl_2 = "STUDENT_ID";
    public static final String Fess_COl_3 = "Amount_Paid";
    public static final String Fess_COl_4 = "Fee_Month";
    public static final String Fess_COl_5 = "Fee_Year";
    public static final String Fess_COl_6 = "Payment_date";
    public static final String Fess_COl_7 = "Status";
    public static final String Fess_COl_8 = "Amount_Pending";
    public static final String Fess_COl_9 = "Fee_Date";
    public static final String Fess_COl_10 = "Paid_With";
    public static final String Fess_COl_11 = "Received_by";


    public static final String Staff_Fess_COl_1 = "SR_NO";
    public static final String Staff_Fess_COl_2 = "Staff_ID";
    public static final String Staff_Fess_COl_3 = "Amount_Paid";
    public static final String Staff_Fess_COl_4 = "Fee_Month";
    public static final String Staff_Fess_COl_5 = "Fee_Year";
    public static final String Staff_Fess_COl_6 = "Payment_date";
    public static final String Staff_Fess_COl_7 = "Status";
    public static final String Staff_Fess_COl_8 = "Amount_Pending";
    public static final String Staff_Fess_COl_9 = "Fee_Date";
    public static final String Staff_Fess_COl_10 = "Paid_With";
    //public static final String Staff_Fess_COl_11 = "Received_by";


    public static final String IN_EX_COL_1 = "Sr_no";
    public static final String IN_EX_COL_2 = "Payment_Type";
    public static final String IN_EX_COL_3 = "Paid_to";
    public static final String IN_EX_COL_4 = "Payment_Amount";
    public static final String IN_EX_COL_5 = "Payment_Date";
    public static final String IN_EX_COL_6 = "Payment_Desc";
    public static final String IN_EX_COL_7 = "Payment_Type_Code";


    //public static final String BATCH_COL_1="BATCH_ID";

    public static final String ALL_BATCH_COL_1 = "ALL_BATCH_ID";
    public static final String ALL_BATCH_COL_2 = "BATCH_NAME";
    public static final String ALL_BATCH_COL_3 = "BATCH_TIME";
    public static final String ALL_BATCH_COL_4 = "TeacherName";
    public static final String ALL_BATCH_COL_5 = "TableName";
    public static final String ALL_BATCH_COL_6 = "Registration_Amt";
    public static final String ALL_BATCH_COL_7 = "MonthlyFee_Amt";
    public static final String ALL_BATCH_COL_8 = "BatchActiveStatus";
    public static final String ALL_BATCH_COL_9 = "TeacherId";


    public static final String Sub_Table_COL_2 = "Student_Name";
    public static final String Sub_Table_COL_3 = "Student_Id";
    public static final String Sub_Table_COL_4 = "Amount_Paid";
    public static final String Sub_Table_COL_5 = "Flag";
    public static final String Sub_Table_COL_6 = "Batch_Timing";


    public static final String TEACHER_COL_1 = "Sr_no";
    public static final String TEACHER_COL_2 = "Teacher_Name";
    public static final String TEACHER_COL_3 = "Teacher_Contact";
    public static final String TEACHER_COL_4 = "Teacher_Address";
    public static final String TEACHER_COL_5 = "Teacher_Subject";
    public static final String TEACHER_COL_6 = "Monthly_Salary";
    public static final String TEACHER_COL_7 = "Profile_Picture";
    public static final String TEACHER_COL_8 = "Email";
    public static final String TEACHER_COL_9 = "Active_Status";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + STUDENT_TABLE + "(" + STU_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STU_COL_12 + " TEXT," + STU_COL_2 + " TEXT, " +
                STU_COL_3 + " TEXT, " + STU_COL_4 + " TEXT, " + STU_COL_5 + " TEXT, " + STU_COL_6 + " TEXT, " +
                STU_COL_8 + " TEXT, " + STU_COL_9 + " TEXT, " + STU_COL_10 + " TEXT, " + STU_COL_11 + " TEXT, " + STU_COL_13 + " BLOB " + ")");

        db.execSQL("CREATE TABLE " + SUBJECT_TABLE + "(" + SUB_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUB_COL_2 + " TEXT, " +
                SUB_COL_3 + " TEXT, " + SUB_COL_4 + " TEXT " + ")");


        db.execSQL("CREATE TABLE " + RECEIPT_NO_TABLE + "(" + Receipt_Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Receipt_Col_2 + " TEXT, " +
                Receipt_Col_3 + " TEXT," + Receipt_Col_4 + " TEXT," + Receipt_Col_5 + " TEXT"
                + ")");


        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + " VALUES" + "(" + "\"MATHEMATICS\"" + "," + "\"500\"" + "," + "\"A\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"REASONING\"" + "," + "\"500\"" + "," + "\"B\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"ENGLISH GRAMMAR\"" + "," + "\"500\"" + "," + "\"C\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"MARATHI GRAMMAR\"" + "," + "\"500\"" + "," + "\"D\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"GEOGRAPHY\"" + "," + "\"500\"" + "," + "\"E\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"HISTORY\"" + "," + "\"500\"" + "," + "\"F\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"POLITICAL SCIENCE\"" + "," + "\"500\"" + "," + "\"G\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"ECONOMIC\"" + "," + "\"500\"" + "," + "\"H\"" + ")");
        db.execSQL("INSERT INTO " + SUBJECT_TABLE + "(" + SUB_COL_2 + "," + SUB_COL_3 + "," + SUB_COL_4 + ")" + "VALUES" + "(" + "\"SCIENCE\"" + "," + "\"500\"" + "," + "\"I\"" + ")");


        db.execSQL("CREATE TABLE " + BATCHES_TABLE + "(" + ALL_BATCH_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALL_BATCH_COL_2 + " TEXT, " + ALL_BATCH_COL_3 + " TEXT," + ALL_BATCH_COL_4 + " TEXT, " + ALL_BATCH_COL_5 + " TEXT," +
                ALL_BATCH_COL_6 + " TEXT," + ALL_BATCH_COL_7 + " TEXT," + ALL_BATCH_COL_8 + " TEXT," + ALL_BATCH_COL_9 + " TEXT" + ")");


        db.execSQL(" CREATE TABLE " + TUITION_INFO_TABLE + "(" + TUITION_INFO_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TUITION_INFO_COL2 + " TEXT," + TUITION_INFO_COL3 + " TEXT," + TUITION_INFO_COL4 + " TEXT," + TUITION_INFO_COL5 + " TEXT," +
                TUITION_INFO_COL6 + " TEXT," + TUITION_INFO_COL7 + " TEXT," + TUITION_INFO_COL8 + " BLOB" + ")");


        db.execSQL("CREATE TABLE " + TEACHER_TABLE + "(" + TEACHER_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEACHER_COL_2 + " TEXT, " + TEACHER_COL_3 + " TEXT, " +
                TEACHER_COL_4 + " TEXT, " + TEACHER_COL_8 + " TEXT," + TEACHER_COL_5 + " TEXT, " + TEACHER_COL_6 + " TEXT," + TEACHER_COL_7 + " BLOB, " + TEACHER_COL_9 + " TEXT" + ")");


        db.execSQL("CREATE TABLE " + INCOME_EXPENSE_TABLE + "(" + IN_EX_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IN_EX_COL_2 + " TEXT, " + IN_EX_COL_3 + " TEXT," + IN_EX_COL_4 + " INTEGER," + IN_EX_COL_5 + " DATE," + IN_EX_COL_6 + " TEXT," + IN_EX_COL_7 + " TEXT"
                + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BATCHES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TEACHER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + INCOME_EXPENSE_TABLE);

        onCreate(sqLiteDatabase);
    }

    public String getTuitionName() {
        String name = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TUITION_INFO_COL2 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                name = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return name;
    }


    public String getTuitionEmail() {
        String email = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TUITION_INFO_COL4 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                email = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return email;
    }


    public int getTuitionInfoCount() {

        SQLiteDatabase db = this.getWritableDatabase();
        int count = 0;
        String query = "select * from " + TUITION_INFO_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                count = (cursor.getCount());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return count;

    }


    public String getTuitionAddress() {
        String val = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TUITION_INFO_COL5 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                val = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return val;
    }


    public void insertIntoTuitionTable(String S_instName, String S_instEmail, String S_instAddress, String S_admin1Name, String S_admin2Name, String S_phoneNum) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TUITION_INFO_COL2, S_instName);
        contentValues.put(TUITION_INFO_COL3, S_phoneNum);
        contentValues.put(TUITION_INFO_COL4, S_instEmail);
        contentValues.put(TUITION_INFO_COL5, S_instAddress);
        contentValues.put(TUITION_INFO_COL6, S_admin1Name);
        contentValues.put(TUITION_INFO_COL7, S_admin2Name);

        db.insert(TUITION_INFO_TABLE, null, contentValues);
        db.close();

    }


    public String[] getAdminNames() {

        String[] labels = new String[2];
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + TUITION_INFO_COL6 + "," + TUITION_INFO_COL7 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels[0] = (cursor.getString(0));
                labels[1] = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        cursor.close();

     /*   String query2 = "SELECT " + TUITION_INFO_COL7 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor2 = db.rawQuery(query2, null);

        if (cursor2.moveToFirst()) {
            do {
                labels[1] = (cursor.getString(0));

            } while (cursor2.moveToNext());
        }

        cursor2.close();
*/
        db.close();
        return labels;

    }


    public String getAdminName1(){
        String name=null;
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select "+TUITION_INFO_COL6+" from "+TUITION_INFO_TABLE;
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                name= (cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return name;

    }


    public String getAdminName2(){
        String name=null;
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select "+TUITION_INFO_COL7+" from "+TUITION_INFO_TABLE;
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                name= (cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return name;

    }


    public void deleteTuiImage(){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.putNull(TUITION_INFO_COL8);
        db.update(TUITION_INFO_TABLE,cn,TUITION_INFO_COL1+"=1",null);


    }


    public void deleteStaffImage(String ID){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.putNull(TEACHER_COL_7);
        db.update(TEACHER_TABLE,cn,TEACHER_COL_1+"="+ID,null);


    }


    public void deleteStuImage(String ID){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.putNull(STU_COL_13);
        db.update(STUDENT_TABLE,cn,STU_COL_1+"="+ID,null);


    }

    // public static String getDBName() {

    //  DataBaseHelper db = new DataBaseHelper();
    //if (BuildConfig.DEBUG) {
    //  throw new AssertionError("Assertion failed");
    // }
    // assert false;
    //return db.getDatabaseName();

    //}


    public boolean insert_PersonalData_Stu_Table(String name, String address, String contact, String gender, String caste, String EmailString, String EduString, String RegId, byte[] img) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_2, name);
        contentValues.put(STU_COL_3, address);
        contentValues.put(STU_COL_4, contact);
        contentValues.put(STU_COL_8, gender);
        contentValues.put(STU_COL_11, caste);
        contentValues.put(STU_COL_6, EmailString);
        contentValues.put(STU_COL_9, EduString);
        contentValues.put(STU_COL_12, RegId);
        contentValues.put(STU_COL_13, img);
        long result = db.insert(STUDENT_TABLE, null, contentValues);

        db.close();
        return result != -1;
    }


    public ArrayList<String> getAllRegistrationNumbers() {

        ArrayList<String> ids = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + STU_COL_12 + " from " + STUDENT_TABLE;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return ids;
    }

    public String getRegId(String id) {

        String regId = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + STU_COL_12 + " from " + STUDENT_TABLE + " Where " + STU_COL_1 + "=" + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                regId = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return regId;

    }

    public String getReceiptNumberForId(String id, String monthVal, String yearVal) {


        String recNo = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + Receipt_Col_2 + " FROM " + RECEIPT_NO_TABLE +
                " WHERE " + Receipt_Col_3 + "=" + id +
                " AND " + Receipt_Col_4 + "=" + monthVal +
                " AND " + Receipt_Col_5 + "=" + yearVal;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                recNo = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return recNo;


    }


    public ArrayList<String> getReceiptNumber() {

        ArrayList<String> recNo = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + Receipt_Col_2 + " FROM " + RECEIPT_NO_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                recNo.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return recNo;

    }


    public void addReceiptNo(String recNo, String monthVal, String yearVal, String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Receipt_Col_2, recNo);
        contentValues.put(Receipt_Col_3, id);
        contentValues.put(Receipt_Col_4, monthVal);
        contentValues.put(Receipt_Col_5, yearVal);

        db.insert(RECEIPT_NO_TABLE, null, contentValues);
        db.close();

    }


    public List<String> getDistinctDialogueLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  DISTINCT " + ALL_BATCH_COL_2 + " FROM " + BATCHES_TABLE;

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


    public List<String> getDialogueLabels() {
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


    public ArrayList<TeacherClass> getTeachers(String batchname) {
        ArrayList<TeacherClass> labels = new ArrayList<>();
        String selectQuery = "SELECT " + TEACHER_COL_2 + "," + TEACHER_COL_1 + " FROM " + TEACHER_TABLE +
                " WHERE " + TEACHER_COL_5 + "='" + batchname + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(new TeacherClass(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public List<String> getTeacherDialogue(String batchname) {
        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT " + TEACHER_COL_2 + " FROM " + TEACHER_TABLE +
                " WHERE " + TEACHER_COL_5 + "='" + batchname + "'";

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


    public void updateEnrolledBatches(String TableName, int cnt) {
        SQLiteDatabase db = this.getWritableDatabase();

        String id = null;

        String query = "SELECT " + ALL_BATCH_COL_1 + " FROM " + BATCHES_TABLE + " WHERE " + ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                id = (cursor.getString(0));
            } while (cursor.moveToNext());

        }

        cursor.close();

        //   String query2="Update "+STUDENT_TABLE+" SET "+STU_COL_5+"=CONCAT("+STU_COL_5+","+"'"+id+"')";
        db.execSQL("Update " + STUDENT_TABLE + " SET " + STU_COL_5 + "=" + STU_COL_5 + "||" + "'," + id + "'" + " WHERE " +
                STU_COL_1 + "+" + cnt);
        db.close();

    }

    public ArrayList<SubjectAdapter> getDialogueLabelsAdapter() {
        ArrayList<SubjectAdapter> labels = new ArrayList();
        String stat = "Active";
        String selectQuery = "SELECT "+ ALL_BATCH_COL_2+","+ALL_BATCH_COL_3+","+ALL_BATCH_COL_4+","+ALL_BATCH_COL_8  +" FROM " + BATCHES_TABLE ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new SubjectAdapter(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }


    public ArrayList<FindStudent> getStudentInfo() {
        ArrayList<FindStudent> labels = new ArrayList();

        String selectQuery = "SELECT " + STU_COL_2 + "," + STU_COL_1 + "," + STU_COL_12 + "," + STU_COL_13 + " FROM " + STUDENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            do {

                byte[] img = cursor.getBlob(3);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
                labels.add(new FindStudent(cursor.getString(0), cursor.getString(1), cursor.getString(2), bitmap));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;

    }

    public ArrayList<FindStudent> getStudentInBatch(String TableName) {
        ArrayList<FindStudent> labels = new ArrayList();
        String active = "Active";

        Bitmap bitmap = null;

        String selectQuery = "SELECT " + STUDENT_TABLE + "." + STU_COL_2 + "," + STUDENT_TABLE + "." + STU_COL_1 + "," + STUDENT_TABLE + "." + STU_COL_12 + "," + STUDENT_TABLE + "." + STU_COL_13 + " FROM " + STUDENT_TABLE +
                " INNER JOIN " + TableName + " ON " + TableName + "." + BATCH_COL_2 + "=" + STUDENT_TABLE + "." + STU_COL_1
                + " WHERE " + TableName + "." + BATCH_COL_10 + "=" + "'" + active + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(3);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
                labels.add(new FindStudent(cursor.getString(0), cursor.getString(1), cursor.getString(2), bitmap));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public boolean removeStudent(String TableName, String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BATCH_COL_10, "In-Active");


        long result = db.update(TableName, contentValues, BATCH_COL_2 + "=" + id, null);
        db.close();
        return result != -1;

    }


    public ArrayList<StudentInfo> getStudentDetails(String id) {

        ArrayList<StudentInfo> labels = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + STU_COL_4 + "," + STU_COL_6 + "," + STU_COL_8 + "," + STU_COL_3 + "," + STU_COL_9 + "," + STU_COL_11 +
                " FROM " + STUDENT_TABLE + " WHERE " + STU_COL_1 + "=" + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(new StudentInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;

    }


    public boolean CreateBatch(String BatchName, String BatchTime, String BatchTeacher, String RegFee, String MonthlyFee, String teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String newBatchName = BatchName.replace(" ", "_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

        String TableName = newBatchName + newBatchTime;
        long result1;
        db.execSQL("CREATE TABLE " + TableName + "(" + BATCH_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + BATCH_COL_2 + " INTEGER, " + BATCH_COL_3 + " TEXT, " +
                BATCH_COL_4 + " DATE, " + BATCH_COL_5 + " TEXT, " + BATCH_COL_9 + " TEXT, " + BATCH_COL_13 + " DATE, "
                + BATCH_COL_6 + " TEXT," + BATCH_COL_7 + " TEXT, " + BATCH_COL_8 + " TEXT, " + BATCH_COL_10 + " TEXT, " + BATCH_COL_11 + " TEXT," + BATCH_COL_12 + " TEXT," + "FOREIGN KEY" + "(" + BATCH_COL_2 + ")" + " REFERENCES " + STUDENT_TABLE + "(" + STU_COL_1 + ")" + ")");


        db.execSQL("CREATE TABLE " + "FEES_" + TableName + "(" + Fess_COl_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Fess_COl_2 + " INTEGER, " + Fess_COl_3 + " INTEGER, " + Fess_COl_8 + " INTEGER, " + Fess_COl_9 + " INTEGER, " +
                Fess_COl_4 + " INTEGER, " + Fess_COl_5 + " INTEGER, " + Fess_COl_6 + " DATE, " +
                Fess_COl_7 + " INTEGER, " + Fess_COl_10 + " TEXT, " + Fess_COl_11 + " TEXT" + ")");


        db.execSQL("CREATE TABLE " + "Staff_Fees_" + TableName + "(" + Staff_Fess_COl_1 + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Staff_Fess_COl_2 + " INTEGER," + Staff_Fess_COl_3 + " INTEGER," + Staff_Fess_COl_9 + " INTEGER," + Staff_Fess_COl_4 + " INTEGER," +
                Staff_Fess_COl_5 + " INTEGER," + Staff_Fess_COl_8 + " INTEGER," + Staff_Fess_COl_7 + " TEXT," + Staff_Fess_COl_6 + " DATE," + Staff_Fess_COl_10 + " TEXT" + ")"
        );


        ContentValues contentValues = new ContentValues();
        contentValues.put(ALL_BATCH_COL_2, BatchName);
        contentValues.put(ALL_BATCH_COL_3, BatchTime);
        contentValues.put(ALL_BATCH_COL_4, BatchTeacher);
        contentValues.put(ALL_BATCH_COL_5, TableName);
        contentValues.put(ALL_BATCH_COL_6, RegFee);
        contentValues.put(ALL_BATCH_COL_7, MonthlyFee);
        contentValues.put(ALL_BATCH_COL_8, "Active");
        contentValues.put(ALL_BATCH_COL_9, teacherId);

        result1 = db.insert(BATCHES_TABLE, null, contentValues);

        db.close();
        return result1 != -1;


    }


    public int getMonthlySalary(String idval) {

        SQLiteDatabase db = this.getWritableDatabase();
        int sal = 0;

        String query = "select " + TEACHER_COL_6 + " from " + TEACHER_TABLE + " WHERE " + TEACHER_COL_1 + "=" + idval;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                sal = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sal;
    }


    public void insertIntoStaffFeesTable(String BatchName, String BatchTime, String idval, int curr_day, int curr_month, int curr_year, int salary) {

        SQLiteDatabase db = this.getWritableDatabase();
        String newBatchName = BatchName.replace(" ", "_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

        String TableName = newBatchName + newBatchTime;

        ContentValues cn = new ContentValues();
        cn.put(Staff_Fess_COl_2, idval);
        cn.put(Staff_Fess_COl_8, salary);
        cn.put(Staff_Fess_COl_9, curr_day);
        cn.put(Staff_Fess_COl_4, curr_month);
        cn.put(Staff_Fess_COl_5, curr_year);
        cn.put(Staff_Fess_COl_7, "Pending");
        cn.put(Staff_Fess_COl_3, "0");

        db.insert("Staff_Fees_" + TableName, null, cn);
        db.close();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertIntoTables(String TableName, String StudentName, String date, String RegFeeStatus, String amt) {

        String StudentID = null;
        SQLiteDatabase db = this.getWritableDatabase();

        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        // int  month=today.getMonthValue();
        //int year=today.getYear();

        Cursor cursor = db.rawQuery("SELECT " + STU_COL_1 + " FROM " + STUDENT_TABLE + " where " + STU_COL_2 + "=" + "'" + StudentName + "'", null);

        if (cursor.moveToFirst()) {
            do {
                StudentID = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();


        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate admissionDate = LocalDate.parse(date, df);

        int day = admissionDate.getDayOfMonth();
        int month = admissionDate.getMonthValue();
        int year = admissionDate.getYear();


        String monthlyamt = null;
        String regAmt = null;
        String query1 = "SELECT " + ALL_BATCH_COL_7 + "," + ALL_BATCH_COL_6 + " From " + BATCHES_TABLE + " where " +
                ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";

        Cursor cursor1 = db.rawQuery(query1, null);

        if (cursor1.moveToFirst()) {
            do {
                monthlyamt = cursor1.getString(0);
                regAmt = cursor1.getString(1);
            } while (cursor1.moveToNext());
        }

        cursor1.close();


        //Cursor cr=db.rawQuery("SELECT "+ STU_COL_1 + " FROM " + STUDENT_TABLE + " where "+STU_COL_2+"="+"'"+StudentName+"'",null)


        ContentValues cn = new ContentValues();

        cn.put(BATCH_COL_2, StudentID);
        cn.put(BATCH_COL_3, RegFeeStatus);
        cn.put(BATCH_COL_4, date);
        cn.put(BATCH_COL_8, "Pending");
        if (RegFeeStatus.equals("Paid")) {
            cn.put(BATCH_COL_5, regAmt);
            cn.put(BATCH_COL_9, 0);
            cn.put(BATCH_COL_13, formattedDate);

        } else if (RegFeeStatus.equals("Pending")) {

            cn.put(BATCH_COL_5, "0");
            cn.put(BATCH_COL_9, regAmt);

        }

        cn.put(BATCH_COL_7, monthlyamt);

        cn.put(BATCH_COL_6, "0");
        cn.put(BATCH_COL_10, "Active");


        long result1 = db.insert(TableName, null, cn);


        ContentValues cn2 = new ContentValues();

        cn2.put(Fess_COl_2, StudentID);
        cn2.put(Fess_COl_7, "Pending");
        cn2.put(Fess_COl_4, month);
        cn2.put(Fess_COl_5, year);
        cn2.put(Fess_COl_9, day);
        cn2.put(Fess_COl_3, 0);
        cn2.put(Fess_COl_8, monthlyamt);


        db.insert("Fees_" + TableName, null, cn2);


        db.close();
        return result1 != -1;


/*
       public static final String BATCH_COL_1="SR_NO_ID";
    public static final String BATCH_COL_4="Admission_Date";
    public static final String BATCH_COL_2="STUDENT_ID";
    public static final String BATCH_COL_3="REG_FEE_STATUS";
    public static final String BATCH_COL_5="Registration_Amount";
    public static final String BATCH_COL_6="Total_Paid";
    public static final String BATCH_COL_7="Remaining_Amount";
    public static final String BATCH_COL_8="Monthly_fee_status";*/

    }

    public boolean checkAlreadyPresent(String TableName, String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String active = "Active";

        String query = "Select COUNT(" + BATCH_COL_2 + ")" + " FROM " + TableName + " WHERE " +
                BATCH_COL_2 + "=" + id + " AND " + BATCH_COL_10 + "='" + active + "'";

        String cnt = null;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                cnt = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();
        int finalCnt = Integer.parseInt(cnt);
        return finalCnt > 0;


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertIntoTablesExisting(String TableName, String id, String name, String date, String RegFeeStatus) {


        SQLiteDatabase db = this.getWritableDatabase();

        String inactive = "In-Active";

        String cnt = null;

        String query = "Select COUNT(" + BATCH_COL_2 + ")" + " FROM " + TableName + " WHERE " +
                BATCH_COL_2 + "=" + id + " AND " + BATCH_COL_10 + "='" + inactive + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                cnt = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();

        int count = Integer.parseInt(cnt);

        if (count > 0) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(BATCH_COL_10, "Active");
            contentValues.put(BATCH_COL_8,"Pending");
            db.update(TableName, contentValues, BATCH_COL_2 + "=" + id, null);

        } else {

            DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate admissionDate = LocalDate.parse(date, df);

            int day = admissionDate.getDayOfMonth();
            int month = admissionDate.getMonthValue();
            int year = admissionDate.getYear();


            String monthlyamt = null;
            String regAmt = null;
            String query1 = "SELECT " + ALL_BATCH_COL_7 + "," + ALL_BATCH_COL_6 + " From " + BATCHES_TABLE + " where " +
                    ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";

            Cursor cursor1 = db.rawQuery(query1, null);

            if (cursor1.moveToFirst()) {
                do {
                    monthlyamt = cursor1.getString(0);
                    regAmt = cursor1.getString(1);
                } while (cursor1.moveToNext());
            }

            cursor1.close();


            //Cursor cr=db.rawQuery("SELECT "+ STU_COL_1 + " FROM " + STUDENT_TABLE + " where "+STU_COL_2+"="+"'"+StudentName+"'",null)


            ContentValues cn = new ContentValues();

            cn.put(BATCH_COL_2, id);
            cn.put(BATCH_COL_3, RegFeeStatus);
            cn.put(BATCH_COL_4, date);
            cn.put(BATCH_COL_8,"Pending");
            if (RegFeeStatus.equals("Paid")) {
                cn.put(BATCH_COL_5, regAmt);
                cn.put(BATCH_COL_9, 0);

            } else if (RegFeeStatus.equals("Pending")) {

                cn.put(BATCH_COL_5, "0");
                cn.put(BATCH_COL_9, regAmt);

            }

            cn.put(BATCH_COL_7, monthlyamt);
            cn.put(BATCH_COL_10, "Active");
            cn.put(BATCH_COL_6, "0");


            long result1 = db.insert(TableName, null, cn);


            ContentValues cn2 = new ContentValues();

            cn2.put(Fess_COl_2, id);
            cn2.put(Fess_COl_7, "Pending");
            cn2.put(Fess_COl_4, month);
            cn2.put(Fess_COl_5, year);
            cn2.put(Fess_COl_9, day);
            cn2.put(Fess_COl_3, 0);
            cn2.put(Fess_COl_8, monthlyamt);


            db.insert("Fees_" + TableName, null, cn2);


            db.close();

        }


    }


    public int getcnt() {
        SQLiteDatabase db = this.getWritableDatabase();

        String cnt = null;
        String query = " SELECT COUNT(" + STU_COL_1 + ")" + " FROM " + STUDENT_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                cnt = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        int cnt1 = Integer.parseInt(cnt);
        return cnt1;

    }


    public List<String> getBatchTime(String SubjectName) {
        List<String> labels = new ArrayList<String>();
String stat="Active";
        // Select All Query
        String selectQuery = "SELECT * FROM " + BATCHES_TABLE + " where " + ALL_BATCH_COL_2 + "=" + "'" + SubjectName + "'"+
                " AND "+ALL_BATCH_COL_8+"='"+stat+"'";

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


    public boolean isTableExist(String TableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    return true;

                }
                db.close();
            }
            return false;

        }

    }


    public void suspendBatch(String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ALL_BATCH_COL_8, "In-Active");

        db.update(BATCHES_TABLE, contentValues, ALL_BATCH_COL_5 + "='" + TableName+"'", null);

       // db.execSQL("DROP TABLE IF EXISTS " + TableName);

        db.close();
    }



    public void activateBatch(String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ALL_BATCH_COL_8, "Active");

     db.update(BATCHES_TABLE, contentValues, ALL_BATCH_COL_5 + "='" + TableName+"'", null);

        // db.execSQL("DROP TABLE IF EXISTS " + TableName);

        db.close();

    }

    public void deleteBatch(String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();

      //  ContentValues contentValues = new ContentValues();
        //contentValues.put(ALL_BATCH_COL_8, "In-Active");
        //db.update(BATCHES_TABLE, contentValues, ALL_BATCH_COL_5 + "='" + TableName+"'", null);
        db.delete(BATCHES_TABLE,ALL_BATCH_COL_5+"='"+TableName+"'",null);
        db.execSQL("DROP TABLE IF EXISTS " + TableName);

        db.close();
    }

    public ArrayList<RegSubClass> getAllRegisteredBathes(String id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> batches = new ArrayList<>();

        String query = "Select " + ALL_BATCH_COL_5 + " FROM " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                batches.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        ArrayList<String> registeredBatches = new ArrayList<>();
        String active = "Active";
        for (int i = 0; i < batches.size(); i++) {

            String query1 = "Select " + BATCH_COL_2 + " FROM " + batches.get(i) + " Where " + BATCH_COL_10 + "='" + active + "'" +
                    " AND " + BATCH_COL_2 + "=" + id;
            String StuId = null;
            Cursor cursor1 = db.rawQuery(query1, null);
            if (cursor1.moveToFirst()) {
                do {
                    StuId = (cursor1.getString(0));
                } while (cursor1.moveToNext());
            }
            cursor1.close();

            if (StuId != null) {
                String query2 = " SELECT " + ALL_BATCH_COL_2 + " FROM " + BATCHES_TABLE + " WHERE " + ALL_BATCH_COL_5 +
                        "=" + "'" + batches.get(i) + "'";


                Cursor cursor2 = db.rawQuery(query2, null);
                if (cursor2.moveToFirst()) {
                    do {
                        registeredBatches.add(cursor2.getString(0));
                    } while (cursor2.moveToNext());
                }

                cursor2.close();

            }

        }

        ArrayList<RegSubClass> array = new ArrayList<>();
        for (int i = 0; i < registeredBatches.size(); i++) {
            String time = null;
            String query3 = "SELECT " + ALL_BATCH_COL_3 + " FROM " + BATCHES_TABLE + " WHERE " +
                    ALL_BATCH_COL_5 + "=" + "'" + batches.get(i) + "'";

            Cursor cursor3 = db.rawQuery(query3, null);
            if (cursor3.moveToFirst()) {
                do {
                    time = (cursor3.getString(0));
                } while (cursor3.moveToNext());
            }

            cursor3.close();


            String query4 = "SELECT SUM(" + Fess_COl_3 + ")," + "SUM(" + Fess_COl_8 + ")" + " FROM " + "Fees_" + batches.get(i) +
                    " WHERE " + Fess_COl_2 + "=" + id;

            Cursor cursor4 = db.rawQuery(query4, null);
            if (cursor4.moveToFirst()) {
                do {
                    array.add(new RegSubClass(registeredBatches.get(i), time, cursor4.getString(0), cursor4.getString(1)));
                } while (cursor4.moveToNext());
            }

            cursor4.close();

        }
        db.close();
        return array;

    }


    public ArrayList<ReceiptMonthClass> getReceiptMonths(String tableName, String id) {


        ArrayList<ReceiptMonthClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select " + Fess_COl_4 + "," + Fess_COl_5 + " from " + "Fees_" + tableName + " where " + Fess_COl_2 + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new ReceiptMonthClass(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }


    public ArrayList<PaymentInfoClass> getPaymentInfo(String tableName, String id, String month, String year) {

        ArrayList<PaymentInfoClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + "( SELECT " + ALL_BATCH_COL_7 + " FROM " + BATCHES_TABLE + " WHERE " + ALL_BATCH_COL_5 + "='" + tableName + "')" + "," + Fess_COl_3 + "," + Fess_COl_8 + "," + Fess_COl_10 + "," + Fess_COl_11 + "," + Fess_COl_6 + " FROM " +
                "Fees_" + tableName + " WHERE " + Fess_COl_2 + "=" + id + " AND " + Fess_COl_4 + "=" + month + " AND " + Fess_COl_5 + "=" + year;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new PaymentInfoClass(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return labels;

    }


    public boolean updateBatchInfo(String TableName, String idVal, String regAmount, String monthAmount,String Teacher) {


        SQLiteDatabase db = this.getWritableDatabase();

        String batchTeacher = null;

        String query = "select " + TEACHER_COL_2 + " from " + TEACHER_TABLE + " where " + TEACHER_COL_1 + "=" + idVal;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                batchTeacher = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();


        ContentValues contentValues = new ContentValues();

        //contentValues.put(ALL_BATCH_COL_4, batchTeacher);
        contentValues.put(ALL_BATCH_COL_6, regAmount);
        contentValues.put(ALL_BATCH_COL_7, monthAmount);
        contentValues.put(ALL_BATCH_COL_4,Teacher);

        long result = db.update(BATCHES_TABLE, contentValues, ALL_BATCH_COL_5 + "='" + TableName+"'", null);
        db.close();
        return result != -1;

    }


    public String getAmount(String SubjectName) {
        String labels = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + ALL_BATCH_COL_7 + " from " + BATCHES_TABLE + " where " + ALL_BATCH_COL_5 + "=" + "'" + SubjectName + "'";
        Cursor cursor = db.rawQuery(query, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public String getAdmissionDate(String TableName, String StudentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Date = "";
        String query = "select " + BATCH_COL_4 + " from " + TableName + " where " + BATCH_COL_2 + "=" + StudentId;
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Date = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        return Date;
    }



    /*public ArrayList<StudentClass> getAllStudents(String TableName){
        ArrayList<StudentClass> labels=new ArrayList();

       // String selectQuery = "SELECT "+ STUDENT_TABLE+"."+STU_COL_2+","+ TableName+"."+BATCH_COL_3 +","+ TableName+"."+BATCH_COL_8 +","+STUDENT_TABLE+"."+STU_COL_1+" FROM " + TableName +
         //       " INNER JOIN "+STUDENT_TABLE +" ON "+ TableName+"."+BATCH_COL_2+"="+STUDENT_TABLE+"."+STU_COL_1 ;


        String query="SELECT "+  STUDENT_TABLE+"."+STU_COL_2+","+STUDENT_TABLE+"."+STU_COL_1+" FROM "+ STUDENT_TABLE+ " INNER JOIN "+TableName +" ON "+ TableName+"."+BATCH_COL_2+"="+STUDENT_TABLE+"."+STU_COL_1 ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new StudentClass(cursor.getString(0),cursor.getString(1),null));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;


    }

*/


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean UpdateFeeTableIsChecked(String TableName, String StudentId, String Amount, String paymentBy, String paymentTo, String srNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));

        ContentValues cn = new ContentValues();

        String query = "SELECT " + ALL_BATCH_COL_7 + " FROM " + BATCHES_TABLE + " WHERE " +
                ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";

        String monthlyFee = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                monthlyFee = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();


        int remaining_amount = Integer.parseInt(monthlyFee) - Integer.parseInt(Amount);

        cn.put(Fess_COl_6, formattedDate);
        cn.put(Fess_COl_3, Amount);
        cn.put(Fess_COl_8, "0");
        cn.put(Fess_COl_10, paymentBy);
        cn.put(Fess_COl_11, paymentTo);
        //  if (remaining_amount == 0) {
        cn.put(Fess_COl_7, "Paid");
        ContentValues contentValues = new ContentValues();
        contentValues.put(BATCH_COL_8, "Paid");
        db.update(TableName, contentValues, BATCH_COL_2 + "=" + StudentId, null);


        long result = db.update("Fees_" + TableName, cn, Fess_COl_1 + "=" + srNo, null);
        db.close();
        return result != -1;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean UpdateFeeTable(String TableName, String StudentId, String Amount, String paymentBy, String paymentTo, String srNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));

        ContentValues cn = new ContentValues();

        String query = "SELECT " + ALL_BATCH_COL_7 + " FROM " + BATCHES_TABLE + " WHERE " +
                ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";

        String monthlyFee = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                monthlyFee = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();


        assert monthlyFee != null;
        int remaining_amount = Integer.parseInt(monthlyFee) - Integer.parseInt(Amount);

        cn.put(Fess_COl_6, formattedDate);
        cn.put(Fess_COl_3, Amount);
        cn.put(Fess_COl_8, remaining_amount);
        cn.put(Fess_COl_10, paymentBy);
        cn.put(Fess_COl_11, paymentTo);

        if (remaining_amount == 0) {
            cn.put(Fess_COl_7, "Paid");
            ContentValues contentValues = new ContentValues();
            String idS = "select " + STU_COL_1 + " from " + STUDENT_TABLE + " where " + STU_COL_12 + "=" + StudentId;
            String idValS = null;
            Cursor cursor1 = db.rawQuery(idS, null);

            if (cursor1.moveToFirst()) {
                do {
                    idValS = cursor1.getString(0);
                } while (cursor1.moveToNext());
            }

            cursor1.close();
            contentValues.put(BATCH_COL_8, "Paid");
            db.update(TableName, contentValues, BATCH_COL_2 + "=" + idValS, null);

        }

        long result = db.update("Fees_" + TableName, cn, Fess_COl_1 + "=" + srNo, null);
        db.close();
        return result != -1;


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean updateRegAmtStatus(String tableName, String StudentID, String regAmount, String Reg_amt) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();

        LocalDate date = LocalDate.now();

        String query = "SELECT " + BATCH_COL_5 + " From " + tableName + " WHERE " + BATCH_COL_2 + "=" + StudentID;
        Cursor cursor = db.rawQuery(query, null);
        String paidAmt = null;
        if (cursor.moveToFirst()) {
            do {
                paidAmt = (cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();


        int updatedRemainingAmt = Integer.parseInt(Reg_amt) - Integer.parseInt(regAmount);
        cn.put(BATCH_COL_9, updatedRemainingAmt);

        assert paidAmt != null;
        int updatedPaidAmt = Integer.parseInt(paidAmt) + Integer.parseInt(regAmount);
        cn.put(BATCH_COL_5, updatedPaidAmt);
        cn.put(BATCH_COL_13, String.valueOf(date));

        long result = db.update(tableName, cn, BATCH_COL_2 + "=" + StudentID, null);
        db.close();
        return result != -1;


    }


    public boolean deleteStudentEntry(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        long res = 0;
        db.delete(STUDENT_TABLE, STU_COL_1 + "=" + id, null);
        String stat = "Pending";
        for (int i = 0; i < tables.size(); i++) {
            res = db.delete(tables.get(i), BATCH_COL_2 + "=" + id, null);
            db.delete("Fees_" + tables.get(i), Fess_COl_2 + "=" + id + " AND " + Fess_COl_7 + "='" + stat + "'", null);
        }


        return res != -1;

    }


    public ArrayList<String> getAllStudentsId(String TableName) {
        ArrayList<String> labels = new ArrayList();

        String selectQuery = "SELECT " + STUDENT_TABLE + "." + STU_COL_1 + " FROM " + TableName +
                " INNER JOIN " + STUDENT_TABLE + " ON " + TableName + "." + BATCH_COL_2 + "=" + STUDENT_TABLE + "." + STU_COL_1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return labels;
    }


    public int checkStudentsPresent(String TableName) {


        SQLiteDatabase db = this.getWritableDatabase();
        int res = 0;
        String count = "SELECT count(*) FROM " + TableName;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount == 0) {
            res = 1;
        }
        db.close();
        return res;

    }

    public int checkStudentsPresents() {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = 0;
        String count = "SELECT count(*) FROM " + STUDENT_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount == 0) {
            res = 1;
        }
        db.close();
        return res;
    }


    public ArrayList<StudentClass> getUnpaidStudent(String TableName) {


        ArrayList<StudentClass> labels = new ArrayList();
        SQLiteDatabase db = this.getWritableDatabase();

        String stat = "Pending";
        String active = "Active";
        String query = "SELECT " + STUDENT_TABLE + "." + STU_COL_2 + "," + STUDENT_TABLE + "." + STU_COL_12 + "," +
                "Fees_" + TableName + "." + Fess_COl_8 + "," + TableName + "." + BATCH_COL_3 + "," + "Fees_" + TableName + "." + Fess_COl_1 + "," + STUDENT_TABLE + "." + STU_COL_13 +
                " FROM " + STUDENT_TABLE +
                " INNER JOIN " + "Fees_" + TableName + " ON " + STUDENT_TABLE + "." + STU_COL_1 + "=" + "Fees_" + TableName + "." + Fess_COl_2 +
                " AND " + "Fees_" + TableName + "." + Fess_COl_7 + "=" + "'" + stat + "'" +
                " INNER JOIN " + TableName + " ON " + "Fees_" + TableName + "." + Fess_COl_2 + "=" + TableName + "." + BATCH_COL_2;

        Bitmap bitmap = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(5);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
                labels.add(new StudentClass(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), bitmap));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;
    }

    public String getRegFee(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String amt = null;

        String query = "SELECT " + ALL_BATCH_COL_6 + " FROM " + BATCHES_TABLE + " WHERE " + ALL_BATCH_COL_5 + "=" + "'" + tableName + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                amt = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return amt;


    }


    public String getRegFeePaid(String tableName, String StudentID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String amt = null;

        String query = "SELECT " + BATCH_COL_9 + " FROM " + tableName + " WHERE " + BATCH_COL_2 + "=" + StudentID;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                amt = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return amt;


    }


    public int getLastMonth(String TableName) {

        String val = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + Fess_COl_4 + " FROM " + "Fees_" + TableName + " WHERE " + Fess_COl_2 + "=(" + "SELECT " + BATCH_COL_2 + " FROM " + TableName +
                " ORDER BY " + BATCH_COL_2 + " DESC LIMIT 1" + ")";
        // +" ORDER BY "+ Fess_COl_4+" DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                val = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        assert val != null;
        int newval = Integer.parseInt(val);
        return newval;

    }


    public int getLastYear(String TableName) {

        String val = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + Fess_COl_5 + " FROM " + "Fees_" + TableName + " ORDER BY " + Fess_COl_5 + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                val = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        assert val != null;
        int newval = Integer.parseInt(val);
        return newval;

    }

    public int getLastStaffMonth(String TableName) {
        String val = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + Staff_Fess_COl_4 + " FROM " + "Staff_Fees_" + TableName + " ORDER BY " + Staff_Fess_COl_4 + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                val = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        assert val != null;
        int newval = Integer.parseInt(val);
        return newval;
    }

    public int getLastStaffYear(String TableName) {
        String val = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + Staff_Fess_COl_5 + " FROM " + "Staff_Fees_" + TableName + " ORDER BY " + Staff_Fess_COl_5 + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                val = (cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        assert val != null;
        int newval = Integer.parseInt(val);
        return newval;
    }

    public int getStudentsCount(String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        int count = 0;
        String query = " select * from " + TableName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                count = (cursor.getCount());
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return count;


    }

    public int getStaffCount(String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        int count = 0;
        String query = " select * from Staff_Fees_" + TableName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                count = (cursor.getCount());
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return count;


    }

    public boolean insertInStaffFeeTable(String TableName, int curr_mont, int curr_year) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();

        String teacherId = null;
        String query = " select " + ALL_BATCH_COL_9 + " from " + BATCHES_TABLE + " where " + ALL_BATCH_COL_5 + "='" + TableName + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                teacherId = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();


        int teacherSalary = 0;
        String query1 = "select " + TEACHER_COL_6 + " from " + TEACHER_TABLE + " WHERE " + TEACHER_COL_1 + "=" + teacherId;
        Cursor cursor1 = db.rawQuery(query1, null);
        if (cursor1.moveToFirst()) {
            do {
                teacherSalary = cursor1.getInt(0);
            } while (cursor1.moveToNext());
        }
        cursor1.close();

        int teacherDate = 0;
        String query2 = "select " + Staff_Fess_COl_9 + " from Staff_Fees_" + TableName + " WHERE " + Staff_Fess_COl_2 + "=" + teacherId;
        Cursor cursor2 = db.rawQuery(query2, null);
        if (cursor2.moveToFirst()) {
            do {
                teacherDate = cursor2.getInt(0);
            } while (cursor2.moveToNext());
        }
        cursor2.close();


        cn.put(Staff_Fess_COl_2, teacherId);
        cn.put(Staff_Fess_COl_3, "0");
        cn.put(Staff_Fess_COl_9, teacherDate);
        cn.put(Staff_Fess_COl_4, curr_mont);
        cn.put(Staff_Fess_COl_5, curr_year);
        cn.put(Staff_Fess_COl_7, "Pending");
        cn.put(Staff_Fess_COl_8, teacherSalary);

        long res = db.insert("Staff_Fees_" + TableName, null, cn);
        db.close();
        return res != -1;


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertIntoFeesTable(String TableName, int curr_mont, int curr_year) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cn = new ContentValues();

        ArrayList<String> stuId = new ArrayList<>();
        LocalDate date = LocalDate.now();
        int curr_day = date.getDayOfMonth();

        String query4 = "SELECT " + ALL_BATCH_COL_7 + " FROM " + BATCHES_TABLE + " WHERE " +
                ALL_BATCH_COL_5 + "=" + "'" + TableName + "'";

        String monthlyFee = null;
        Cursor cursor4 = db.rawQuery(query4, null);

        if (cursor4.moveToFirst()) {
            do {
                monthlyFee = cursor4.getString(0);
            } while (cursor4.moveToNext());
        }

        cursor4.close();


        String active = "Active";
        String query = "SELECT " + BATCH_COL_2 + " FROM " + TableName + " Where " + BATCH_COL_10 + "='" + active + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                stuId.add(cursor.getString(0));
                //newToDate=cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();

        long result = 0;

        ArrayList<String> idArray = new ArrayList<>();

        for (int i = 0; i < stuId.size(); i++) {

            int day = 0;
            String query2 = "Select " + Fess_COl_9 + " FROM  " + "Fees_" + TableName + " WHERE " + Fess_COl_2 + "=" + stuId.get(i);

            Cursor cursor1 = db.rawQuery(query2, null);
            if (cursor1.moveToFirst()) {
                do {
                    day = (cursor1.getInt(0));

                } while (cursor1.moveToNext());
            }
            cursor1.close();


            String query3 = "SELECT " + Fess_COl_2 + " FROM " + "Fees_" + TableName + " WHERE " + Fess_COl_4 + "=" + curr_mont;
            Cursor cursor3 = db.rawQuery(query3, null);
            if (cursor3.moveToFirst()) {
                do {
                    idArray.add(cursor3.getString(0));

                } while (cursor3.moveToNext());
            }
            cursor3.close();


            if (!idArray.contains(stuId.get(i))) {
                if (curr_day >= day) {

                    cn.put(Fess_COl_2, stuId.get(i));
                    cn.put(Fess_COl_3, "0");
                    cn.put(Fess_COl_4, curr_mont);
                    cn.put(Fess_COl_5, curr_year);
                    cn.put(Fess_COl_7, "Pending");
                    cn.put(Fess_COl_8, monthlyFee);
                    cn.put(Fess_COl_9, day);
                    result = db.insert("Fees_" + TableName, null, cn);
                    // idArray.add(stuId.get(i));
                }
            }
        }

        db.close();
        return result != -1;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getOccurrence(String TableName, int month) {

        SQLiteDatabase db = this.getWritableDatabase();
        //  String val=null;

        LocalDate date = LocalDate.now();
        int curr_mont = date.getMonthValue();
        int curr_year = date.getYear();

        int result = 0;

        ArrayList<String> val = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();

        String query = "SELECT " + Fess_COl_2 + " FROM " + "Fees_" + TableName + " WHERE " + Fess_COl_4 + "=" + curr_mont;
        // +" ORDER BY "+ Fess_COl_2+" DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                val.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        String active = "Active";
        //  String id=null;
        String query2 = "SELECT " + BATCH_COL_2 + " FROM " + TableName + " WHERE " + BATCH_COL_10 + "='" + active + "'";
        // " ORDER BY "+ BATCH_COL_2+" DESC LIMIT 1";
        Cursor cursor2 = db.rawQuery(query2, null);
        if (cursor2.moveToFirst()) {
            do {
                id.add(cursor2.getString(0));
            } while (cursor2.moveToNext());
        }
        cursor2.close();

        if (val.size() == id.size()) {
            result = 1;
        }
        db.close();
        return result;

    }


    public String getRegFeePaymentStatus(String tableName, String StudentID) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + BATCH_COL_3 + " FROM " + tableName + " WHERE " + BATCH_COL_2 + "=" + StudentID;


        Cursor cursor = db.rawQuery(query, null);
        String status = null;

        if (cursor.moveToFirst()) {
            do {
                status = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return status;


    }

    public ArrayList<String> getStudentsPhoneNumber(String tableName) {

        ArrayList<String> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String active = "Active";

        String query = " SELECT " + STUDENT_TABLE + "." + STU_COL_4 + " FROM " + STUDENT_TABLE + " INNER JOIN " + tableName +
                " ON " + STUDENT_TABLE + "." + STU_COL_1 + "=" + tableName + "." + BATCH_COL_2 +
                " WHERE " + tableName + "." + BATCH_COL_10 + "='" + active + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;

    }


    public ArrayList<String> getAllTableName() {

        ArrayList<String> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String stat="Active";
        String query = "SELECT " + ALL_BATCH_COL_5 + " FROM " + BATCHES_TABLE+" where "+ALL_BATCH_COL_8+"='"+stat+"'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;

    }


    public String getContact(String StudentID) {

        String contact = null;

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + STU_COL_4 + " FROM " + STUDENT_TABLE + " WHERE " + STU_COL_12 + "=" + StudentID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                contact = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contact;


    }


    public ArrayList<PaymentRecievedSMSClass> getpaymentRecievedSMS(String tableName, String srNo) {

        ArrayList<PaymentRecievedSMSClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select " + Fess_COl_9 + "," + Fess_COl_4 + "," + Fess_COl_5 + " From " + "Fees_" + tableName +
                " WHERE " + Fess_COl_1 + "=" + srNo;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new PaymentRecievedSMSClass(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;


    }


    public ArrayList<SMSInfoClass> getUnpaidStudentsPhoneNumber(String tableName) {

        ArrayList<SMSInfoClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String status = "Pending";

        String query = " SELECT " + STUDENT_TABLE + "." + STU_COL_4 + "," + "Fees_" + tableName + "." + Fess_COl_9 + "," +
                "Fees_" + tableName + "." + Fess_COl_4 + "," + "Fees_" + tableName + "." + Fess_COl_5 + "," + STUDENT_TABLE + "." + STU_COL_2
                + " FROM " + STUDENT_TABLE + " INNER JOIN " + "Fees_" + tableName +
                " ON " + STUDENT_TABLE + "." + STU_COL_1 + "=" + "Fees_" + tableName + "." + Fess_COl_2 +
                " WHERE " + "Fees_" + tableName + "." + Fess_COl_7 + "='" + status + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new SMSInfoClass(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;

    }


    public void updatePhone(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_4, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }


    public void updateName(String id, String Sname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_2, Sname);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();

    }

    public void updateEmail(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_6, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateAddress(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_3, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateEducation(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_9, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateGender(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_8, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateCast(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_11, phoneNum);
        db.update(STUDENT_TABLE, contentValues, STU_COL_1 + "=" + id, null);
        db.close();
    }


    public Bitmap getProImage(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bitmap = null;
        String query = "SELECT " + STU_COL_13 + " FROM " + STUDENT_TABLE + " WHERE " + STU_COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(0);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bitmap;


    }


    public byte[] getTuiThumb() {

        SQLiteDatabase db = this.getWritableDatabase();
        byte[] thumb = null;
        String query = "SELECT " + TUITION_INFO_COL8 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                thumb = cursor.getBlob(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return thumb;


    }


    public byte[] getStuThumb(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        byte[] thumb = null;
        String query = "SELECT " + STU_COL_13 + " FROM " + STUDENT_TABLE + " WHERE " + STU_COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                thumb = cursor.getBlob(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return thumb;


    }

    public byte[] getStaffThumb(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        byte[] thumb = null;
        String query = "SELECT " + TEACHER_COL_7 + " FROM " + TEACHER_TABLE + " WHERE " + TEACHER_COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                thumb = cursor.getBlob(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return thumb;


    }

    public Bitmap getStaffProImage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bitmap = null;
        String query = "SELECT " + TEACHER_COL_7 + " FROM " + TEACHER_TABLE + " WHERE " + TEACHER_COL_1 + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(0);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bitmap;
    }


    public ArrayList<StaffInfo> getStaffDetails(String id) {

        ArrayList<StaffInfo> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + TEACHER_COL_2 + "," + TEACHER_COL_3 + "," +
                TEACHER_COL_8 + "," + TEACHER_COL_4 + "," + TEACHER_COL_6 + " FROM " +
                TEACHER_TABLE + " WHERE " + TEACHER_COL_1 + "=" + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new StaffInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;


    }


    public ArrayList<Staff_Class> getStaffInfo() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Staff_Class> labels = new ArrayList<>();

        String query = "select " + TEACHER_COL_1 + "," + TEACHER_COL_2 + "," + TEACHER_COL_5 + "," + TEACHER_COL_7 +
                " FROM " + TEACHER_TABLE;
        Bitmap bitmap;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(3);
                if (img == null) {

                    labels.add(new Staff_Class(cursor.getString(0), cursor.getString(1), cursor.getString(2), null));

                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    labels.add(new Staff_Class(cursor.getString(0), cursor.getString(1), cursor.getString(2), bitmap));

                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return labels;
    }


    public boolean insertIntoTeacherTable(String name_T, String address_T, String phone_T, String subject_T, String salary_T, String email_T, byte[] thumb) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put(TEACHER_COL_2, name_T);
        cn.put(TEACHER_COL_3, phone_T);
        cn.put(TEACHER_COL_4, address_T);
        cn.put(TEACHER_COL_5, subject_T);
        cn.put(TEACHER_COL_6, salary_T);
        cn.put(TEACHER_COL_8, email_T);
        cn.put(TEACHER_COL_7, thumb);

        long result = db.insert(TEACHER_TABLE, null, cn);
        db.close();
        return result != -1;

    }


    public ArrayList<StaffRegisteredSubjectClass> getAllRegisterdBatchesStaff(String id) {

        ArrayList<StaffRegisteredSubjectClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + ALL_BATCH_COL_2 + "," + ALL_BATCH_COL_3 + " FROM " + BATCHES_TABLE +
                " WHERE " + ALL_BATCH_COL_9 + "=" + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(new StaffRegisteredSubjectClass(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;
    }


    public void updateStaffPhone(String id, String phoneNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_COL_3, phoneNum);
        db.update(TEACHER_TABLE, contentValues, TEACHER_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateStaffEmail(String id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_COL_8, email);
        db.update(TEACHER_TABLE, contentValues, TEACHER_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateStaffAddress(String id, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_COL_4, address);
        db.update(TEACHER_TABLE, contentValues, TEACHER_COL_1 + "=" + id, null);
        db.close();
    }

    public void updateStaffSalary(String id, String salary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_COL_6, salary);
        db.update(TEACHER_TABLE, contentValues, TEACHER_COL_1 + "=" + id, null);
        db.close();
    }


    public void updateStaffName(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEACHER_COL_2, name);
        db.update(TEACHER_TABLE, contentValues, TEACHER_COL_1 + "=" + id, null);
        db.close();

    }


    public ArrayList<StaffPaymentClass> getStaffPaymentInfo(String StaffId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<StaffPaymentClass> labels = new ArrayList<>();

        ArrayList<String> table = new ArrayList<>();

        String name = null;


        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE + " WHERE " + ALL_BATCH_COL_9 + "=" + StaffId;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                table.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // db.close();
        String stat = "Pending";
        for (int i = 0; i < table.size(); i++) {
            int count = 0;
            String query1 = "select " + Staff_Fess_COl_2 + " from " + " Staff_Fees_" + table.get(i) +
                    " where " + Staff_Fess_COl_7 + "='" + stat + "'";
            Cursor cursor1 = db.rawQuery(query1, null);
            if (cursor1.moveToFirst()) {
                do {
                    count = (cursor1.getCount());
                } while (cursor1.moveToNext());
            }
            cursor1.close();


            String nameQuery = "select " + ALL_BATCH_COL_2 + " from " + BATCHES_TABLE + " where " + ALL_BATCH_COL_5 + "='" + table.get(i) + "'";
            Cursor namecursor = db.rawQuery(nameQuery, null);

            if (namecursor.moveToFirst()) {
                do {
                    name = namecursor.getString(0);
                } while (namecursor.moveToNext());
            }
            namecursor.close();


            if (count > 0) {

                String query2 = "select " + Staff_Fess_COl_8 + "," + Staff_Fess_COl_4 + "," + Staff_Fess_COl_5 + " FROM " +
                        "Staff_Fees_" + table.get(i) + " where " + Staff_Fess_COl_7 + "='" + stat + "'";

                Cursor cursor2 = db.rawQuery(query2, null);
                if (cursor2.moveToFirst()) {
                    do {

                        String month = cursor2.getString(1);
                        String year = cursor2.getString(2);

                        String dateVal = month + "/" + year;

                        labels.add(new StaffPaymentClass(name, cursor2.getString(0), dateVal, table.get(i)));

                    } while (cursor2.moveToNext());
                }
                cursor2.close();


            }


        }

        db.close();
        return labels;


    }


    public String getStaffAmountToPay(String StfId, String month, String TableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        String amount = null;

        String[] DateArray = month.split("/");
        String monthVal = DateArray[0];
        String yearVal = DateArray[1];

        String query = "select " + Staff_Fess_COl_8 + " from Staff_Fees_" + TableName + " where " +
                Staff_Fess_COl_2 + "=" + StfId + " AND " + Staff_Fess_COl_4 + "=" + monthVal +
                "  AND " + Staff_Fess_COl_5 + "=" + yearVal;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                amount = (cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return amount;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean UpdateStaffFeeTable(String id, String TableName, int finalAmt, String month, String paidwith) {

        SQLiteDatabase db = this.getWritableDatabase();
        String amount = null;

        String[] DateArray = month.split("/");
        String monthVal = DateArray[0];
        String yearVal = DateArray[1];
        ContentValues contentValues = new ContentValues();


        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));


        String query = "SELECT " + TEACHER_COL_6 + " FROM " + TEACHER_TABLE + " WHERE " +
                TEACHER_COL_1 + "=" + id;


        String monthlySalary = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                monthlySalary = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        cursor.close();


        assert monthlySalary != null;
        int remaining_amount = Integer.parseInt(monthlySalary) - finalAmt;


        contentValues.put(Staff_Fess_COl_3, finalAmt);
        contentValues.put(Staff_Fess_COl_8, remaining_amount);
        contentValues.put(Staff_Fess_COl_6, formattedDate);

        contentValues.put(Staff_Fess_COl_10, paidwith);


        if (remaining_amount == 0) {
            contentValues.put(Staff_Fess_COl_7, "Paid");
        }

        long result = db.update("Staff_Fees_" + TableName, contentValues, Staff_Fess_COl_2 + "=" + id + " and " + Staff_Fess_COl_4 + "=" + monthVal + " and " + Staff_Fess_COl_5 + "=" + yearVal, null);
        db.close();
        return result != -1;


    }

    public double getAllRegAmount() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + BATCH_COL_5 + ") as Total FROM " + tables.get(i), null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;

    }


    public double getAllExpenses() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + Staff_Fess_COl_3 + ") as Total FROM Staff_Fees_" + tables.get(i), null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;

    }


    public double getAllIncome() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + Fess_COl_3 + ") as Total FROM Fees_" + tables.get(i), null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;


    }


    public double getAllExtraIncome() {

        SQLiteDatabase db = this.getWritableDatabase();
        double total = 0;
        String code = "I";
        Cursor cursor1 = db.rawQuery("SELECT SUM(" + IN_EX_COL_4 + ") as Total FROM " + INCOME_EXPENSE_TABLE + " WHERE " +
                IN_EX_COL_7 + "='" + code + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
            } while (cursor1.moveToNext());
        }

        cursor1.close();

        db.close();

        return total;

    }

    public double getAllExtraExpense() {

        SQLiteDatabase db = this.getWritableDatabase();
        double total = 0;
        String code = "E";
        Cursor cursor1 = db.rawQuery("SELECT SUM(" + IN_EX_COL_4 + ") as Total FROM " + INCOME_EXPENSE_TABLE + " WHERE " +
                IN_EX_COL_7 + "='" + code + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
            } while (cursor1.moveToNext());
        }

        cursor1.close();

        db.close();

        return total;

    }


    public double getAllIncomeInPeriod(String FromDayVal, String fromMonthVal, String fromYearVal, String ToDayVal, String ToMonthVal, String ToYearVal) {

        String fromDateVal = FromDayVal + "/" + fromMonthVal + "/" + fromYearVal;
        String toDateVal = ToDayVal + "/" + ToMonthVal + "/" + ToYearVal;


        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();


        //  SELECT * FROM appointment_table WHERE barber_id = 36 AND (date BETWEEN '2017-02-08' AND '2017-02-16')


        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + Fess_COl_3 + ") as Total FROM Fees_" + tables.get(i) +
                    " where " + Fess_COl_6 + " BETWEEN " + "'" + fromDateVal + "'" + " AND " + "'" + toDateVal + "'", null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;

    }


    public double getExtraIncomeInPeriod(String FromDayVal, String fromMonthVal, String fromYearVal, String ToDayVal, String ToMonthVal, String ToYearVal) {

        SQLiteDatabase db = this.getWritableDatabase();
        double total = 0;

        String fromDateVal = FromDayVal + "/" + fromMonthVal + "/" + fromYearVal;
        String toDateVal = ToDayVal + "/" + ToMonthVal + "/" + ToYearVal;

        String code = "I";

        Cursor cursor1 = db.rawQuery("SELECT SUM(" + IN_EX_COL_4 + ") as Total FROM " + INCOME_EXPENSE_TABLE +
                " where " +
                IN_EX_COL_7 + "='" + code + "'" + " AND "
                + IN_EX_COL_5 + " BETWEEN " + "'" + fromDateVal + "'" + " AND " + "'" + toDateVal + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
            } while (cursor1.moveToNext());
        }

        cursor1.close();
        db.close();

        return total;

    }


    public double getExtraExpenseInPeriod(String FromDayVal, String fromMonthVal, String fromYearVal, String ToDayVal, String ToMonthVal, String ToYearVal) {

        SQLiteDatabase db = this.getWritableDatabase();
        double total = 0;

        String fromDateVal = FromDayVal + "/" + fromMonthVal + "/" + fromYearVal;
        String toDateVal = ToDayVal + "/" + ToMonthVal + "/" + ToYearVal;

        String code = "E";

        Cursor cursor1 = db.rawQuery("SELECT SUM(" + IN_EX_COL_4 + ") as Total FROM " + INCOME_EXPENSE_TABLE +
                " where " +
                IN_EX_COL_7 + "='" + code + "'" + " AND "
                + IN_EX_COL_5 + " BETWEEN " + "'" + fromDateVal + "'" + " AND " + "'" + toDateVal + "'", null);
        if (cursor1.moveToFirst()) {
            do {
                total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
            } while (cursor1.moveToNext());
        }

        cursor1.close();
        db.close();

        return total;

    }


    public double getAllRegAmountInPeriod(String FromDayVal, String fromMonthVal, String fromYearVal, String ToDayVal, String ToMonthVal, String ToYearVal) {

        String fromDateVal = FromDayVal + "/" + fromMonthVal + "/" + fromYearVal;
        String toDateVal = ToDayVal + "/" + ToMonthVal + "/" + ToYearVal;


        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();


        //  SELECT * FROM appointment_table WHERE barber_id = 36 AND (date BETWEEN '2017-02-08' AND '2017-02-16')


        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + BATCH_COL_5 + ") as Total FROM " + tables.get(i) +
                    " where " + BATCH_COL_13 + " BETWEEN " + "'" + fromDateVal + "'" + " AND " + "'" + toDateVal + "'", null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;

    }


    public double getAllExpensesInPeriod(String FromDayVal, String fromMonthVal, String fromYearVal, String ToDayVal, String ToMonthVal, String ToYearVal) {

        String fromDateVal = FromDayVal + "/" + fromMonthVal + "/" + fromYearVal;
        String toDateVal = ToDayVal + "/" + ToMonthVal + "/" + ToYearVal;


        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();


        //  SELECT * FROM appointment_table WHERE barber_id = 36 AND (date BETWEEN '2017-02-08' AND '2017-02-16')


        double total = 0;

        for (int i = 0; i < tables.size(); i++) {

            Cursor cursor1 = db.rawQuery("SELECT SUM(" + Staff_Fess_COl_3 + ") as Total FROM Staff_Fees_" + tables.get(i) +
                    " where " + Staff_Fess_COl_6 + " BETWEEN " + "'" + fromDateVal + "'" + " AND " + "'" + toDateVal + "'", null);
            if (cursor1.moveToFirst()) {
                do {
                    total += cursor1.getDouble(cursor1.getColumnIndex("Total"));
                } while (cursor1.moveToNext());
            }

            cursor1.close();
        }
        db.close();
        return total;

    }

    public ArrayList<ExtraInExClass> getExtraInExPayments() {

        ArrayList<ExtraInExClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + IN_EX_COL_3 + "," + IN_EX_COL_4 + "," + IN_EX_COL_5 + "," + IN_EX_COL_1 + "," + IN_EX_COL_2 + "," + IN_EX_COL_7 +
                " from " + INCOME_EXPENSE_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(new ExtraInExClass(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return labels;


    }


    public void insertIntoExtraInExTable(String paymentType, String paidTo, String receivedFrom, String amount, String date, String desc) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();

        if (paymentType.equals("Income")) {
            cn.put(IN_EX_COL_2, "Received From");
            cn.put(IN_EX_COL_3, receivedFrom);
            cn.put(IN_EX_COL_7, "I");
        } else if (paymentType.equals("Expense")) {
            cn.put(IN_EX_COL_2, "Paid To");
            cn.put(IN_EX_COL_3, paidTo);
            cn.put(IN_EX_COL_7, "E");
        }

        cn.put(IN_EX_COL_4, amount);
        cn.put(IN_EX_COL_5, date);
        cn.put(IN_EX_COL_6, desc);


        db.insert(INCOME_EXPENSE_TABLE, null, cn);
        db.close();
    }

    public ArrayList<ExtraDetailsClass> getExtraPaymentInfo(String id) {

        ArrayList<ExtraDetailsClass> labels = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + IN_EX_COL_5 + "," + IN_EX_COL_2 + "," + IN_EX_COL_3 + "," +
                IN_EX_COL_4 + "," + IN_EX_COL_6 + "," + IN_EX_COL_7 + " from " + INCOME_EXPENSE_TABLE + " where " + IN_EX_COL_1 + "=" + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                labels.add(new ExtraDetailsClass(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));


            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return labels;


    }


    public ArrayList<TuitionEdit> getTuitionDetails() {
        ArrayList<TuitionEdit> labels = new ArrayList<>();
        Bitmap bitmap = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + TUITION_INFO_COL2 + "," + TUITION_INFO_COL5 + "," + TUITION_INFO_COL3 + "," + TUITION_INFO_COL4 + "," +
                TUITION_INFO_COL6 + "," + TUITION_INFO_COL7 + "," + TUITION_INFO_COL8 + " from " + TUITION_INFO_TABLE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                byte[] img = cursor.getBlob(6);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }

                labels.add(new TuitionEdit(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), bitmap));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return labels;

    }


    public boolean updateStuProPic(String id, byte[] img) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put(STU_COL_13, img);
        long result = db.update(STUDENT_TABLE, cn, STU_COL_1 + "=" + id, null);

        return result != -1;

    }


    public void updatetuiProPic(byte[] img) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put(TUITION_INFO_COL8, img);
        db.update(TUITION_INFO_TABLE, cn, TUITION_INFO_COL1 + "=1", null);


    }


    public boolean updateStaffProPic(String id, byte[] img) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();
        cn.put(TEACHER_COL_7, img);
        long result = db.update(TEACHER_TABLE, cn, TEACHER_COL_1 + "=" + id, null);

        return result != -1;

    }


    public Bitmap getTuiProImage() {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bitmap = null;
        String query = "SELECT " + TUITION_INFO_COL8 + " FROM " + TUITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(0);
                if (img == null) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bitmap;
    }


    public void updateTuiInfo(String T_Name, String T_Add, String T_Phone, String T_Email, String T_Admin1, String T_Admin2, byte[] thumb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cn = new ContentValues();

        cn.put(TUITION_INFO_COL2, T_Name);
        cn.put(TUITION_INFO_COL3, T_Phone);
        cn.put(TUITION_INFO_COL4, T_Email);
        cn.put(TUITION_INFO_COL5, T_Add);
        cn.put(TUITION_INFO_COL6, T_Admin1);
        cn.put(TUITION_INFO_COL7, T_Admin2);
        //   cn.put(TUITION_INFO_COL8, thumb);

        db.update(TUITION_INFO_TABLE, cn, TUITION_INFO_COL1 + "=1", null);
        db.close();
    }


    public ArrayList<RemainingStuBatchClass> getBatchesChartInfo() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<RemainingStuBatchClass> labels = new ArrayList<>();

        ArrayList<String> tables = new ArrayList<>();

        String query = "select " + ALL_BATCH_COL_5 + " from " + BATCHES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tables.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        String stat = "Active";
        String stat2 = "Pending";
        for (int i = 0; i < tables.size(); i++) {

            int count = 0;
            String query1 = "select " + tables.get(i) + "." + BATCH_COL_2 + " from " + tables.get(i) +
                    " where " + BATCH_COL_10 + "='" + stat + "'" + " AND " + BATCH_COL_8 + "='" + stat2 + "'";

            Cursor cursor1 = db.rawQuery(query1, null);
            if (cursor1.moveToFirst()) {
                do {
                    count = (cursor1.getCount());
                } while (cursor1.moveToNext());
            }

            cursor1.close();


            String time = null;
            String batch = null;

            String query2 = "select " + ALL_BATCH_COL_3 + "," + ALL_BATCH_COL_2 + " from " + BATCHES_TABLE +
                    " where " + ALL_BATCH_COL_5 + "='" + tables.get(i) + "'";

            Cursor cursor2 = db.rawQuery(query2, null);
            if (cursor2.moveToFirst()) {
                do {
                    time = (cursor2.getString(0));
                    batch = cursor2.getString(1);

                } while (cursor2.moveToNext());
            }

            cursor2.close();


            labels.add(new RemainingStuBatchClass(batch, time, count));

        }


        return labels;

    }


}
