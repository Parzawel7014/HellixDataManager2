package com.example.atilagapps.hellixdatamanager.Batches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateBatchActivity extends AppCompatActivity {

    TextInputEditText batchName,batchTime,teacherName,regAmt,monthlyAmt;
    Button updateButton;
    String[] teacherListItem, teacherIdListItem;
    ArrayList<TeacherClass> teacherClasses;
    String idval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_batch);

        monthlyAmt=findViewById(R.id.MonthlyAmtId);
        regAmt=findViewById(R.id.RegistrationAmtId);
        batchName=findViewById(R.id.BatchNameId);
        batchTime=findViewById(R.id.BatchTimeId);
        teacherName=findViewById(R.id.BatchTeacherId);
        updateButton=findViewById(R.id.UpdateBatchButtonId);

        teacherClasses = new ArrayList<>();



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        assert bundle != null;
        String BatchName=bundle.getString("BatchName");
        String BatchTime=bundle.getString("BatchTime");
        final String BatchTeacher=bundle.getString("BatchTeacher");


        assert BatchName != null;
        String newBatchName = BatchName.replace(" ", "_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

        final String TableName = newBatchName + newBatchTime;

        final DataBaseHelper db=new DataBaseHelper(this);


        Toolbar toolbar=findViewById(R.id.UpdateBatchToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Batch");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String RegFee=db.getRegFee(TableName);
        String monthlyFee=db.getAmount(TableName);
        batchName.setText(BatchName);
        regAmt.setText(RegFee);
        monthlyAmt.setText(monthlyFee);
        teacherName.setText(BatchTeacher);
        batchTime.setText(BatchTime);


        teacherClasses = db.getTeachers(BatchName);

        teacherIdListItem = new String[teacherClasses.size()];
        teacherListItem = new String[teacherClasses.size()];
        for (int i = 0; i < teacherClasses.size(); i++) {
            teacherListItem[i] = (teacherClasses.get(i).getTeacherName());
            teacherIdListItem[i] = (teacherClasses.get(i).getTeacherID());

        }



        batchTime.setFocusable(false);
        batchTime.setEnabled(false);
        batchTime.setCursorVisible(false);
        batchTime.setKeyListener(null);
        batchTime.setBackgroundColor(Color.rgb(255,194,179));


        batchName.setFocusable(false);
        batchName.setEnabled(false);
        batchName.setCursorVisible(false);
        batchName.setKeyListener(null);

        batchName.setBackgroundColor(Color.rgb(255,194,179));



        teacherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateBatchActivity.this);
                mBuilder.setTitle("Teachers");
                mBuilder.setSingleChoiceItems(teacherListItem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teacherName.setText(teacherClasses.get(which).getTeacherName());
                        idval = teacherClasses.get(which).getTeacherID();
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String batchTeacher = teacherName.getText().toString().trim();
                String regAmount = regAmt.getText().toString().trim();
                String monthAmount = monthlyAmt.getText().toString().trim();


                if ( !validateBatchTeacher() | !validateRegFee() | !validateMonthlyFee()) {
                    return;
                } else {


                    boolean result = db.updateBatchInfo(TableName, idval, regAmount, monthAmount, batchTeacher);

                    if (result) {
                        Toast.makeText(UpdateBatchActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    private boolean validateBatchTeacher(){
        String phoneInput=teacherName.getText().toString().trim();
        if (phoneInput.isEmpty()){
            teacherName.setError("Field can't be empty");
            return false;
        }else {
            teacherName.setError(null);

            return true;
        }
    }

    private boolean validateRegFee(){
        String addressInput=regAmt.getText().toString().trim();
        if (addressInput.isEmpty()){
            regAmt.setError("Field can't be empty");
            return false;
        }else {
            regAmt.setError(null);
            //editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateMonthlyFee(){
        String eduInput=monthlyAmt.getText().toString().trim();
        if (eduInput.isEmpty()){
            monthlyAmt.setError("Field can't be empty");
            return false;
        }else {
            monthlyAmt.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }
    }




}