package com.example.atilagapps.hellixdatamanager.Batches;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.StaffManager.AddStaffActivity2;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class NewBatchAddActivity extends AppCompatActivity {


    Button addBatch;
    String[] listItems;//,teacherListItem;
    String batchname;

    String[] teacherListItem, teacherIdListItem;

    ArrayList<Integer> mUserItems = new ArrayList<>();

    TextInputEditText batchName, batchTeacher, batchTime, regFee, monthlyFee;

    TextInputLayout teacherInputLayout;
    String idval;

    ArrayList<TeacherClass> teacherClasses;
    String[] finalArray;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch_add);


        batchName = findViewById(R.id.BatchNameId);
        batchTeacher = findViewById(R.id.BatchTeacherId);
        batchTime = findViewById(R.id.BatchTimeId);
        addBatch = findViewById(R.id.addBatchButton);
        regFee = findViewById(R.id.RegAmountEditId);
        monthlyFee = findViewById(R.id.MonthlyAmountEditId);
        teacherInputLayout = findViewById(R.id.text_input_batch_teacher_id);

        Toolbar toolbar = findViewById(R.id.AddBatchesActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NEW BATCH");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //  addBatch.setEnabled(false);

        Calendar c = Calendar.getInstance();
        final int hr = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);

        final DataBaseHelper db = new DataBaseHelper(this);

        listItems = db.getDialogueLabels().toArray(new String[0]);


        //final ArrayList<TeacherClass>[] teacherClasses = new ArrayList[]{new ArrayList<>()};

        teacherClasses = new ArrayList<>();


        batchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(NewBatchAddActivity.this);

                //AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewBatchAddActivity.this);
                mBuilder.setTitle("Subjects");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        batchName.setText(listItems[which]);
                        batchname = batchName.getText().toString();
                        // teacherListItem=db.getTeacherDialogue(batchname).toArray(new String[0]);

                        //   teacherClasses[0] =db.getTeachers(batchname);

                        teacherClasses = db.getTeachers(batchname);

                        teacherIdListItem = new String[teacherClasses.size()];
                        teacherListItem = new String[teacherClasses.size()];
                        for (int i = 0; i < teacherClasses.size(); i++) {
                            teacherListItem[i] = (teacherClasses.get(i).getTeacherName());
                            teacherIdListItem[i] = (teacherClasses.get(i).getTeacherID());

                        }
                        dialog.dismiss();
                        if (validateBatchNameIsEmpty()) {
                            teacherInputLayout.setVisibility(View.VISIBLE);
                        }
                        if (!validateBatchNameIsEmpty()) {
                            teacherInputLayout.setVisibility(View.GONE);
                        }
                    }
                });
              //  AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();
            }

        });


        batchTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                teacherClasses = db.getTeachers(batchname);

                if (teacherClasses.isEmpty()) {

                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(NewBatchAddActivity.this);

                   // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(NewBatchAddActivity.this);
                    reconfirmBuilder.setTitle("Alert!");
                    reconfirmBuilder.setMessage("No Teacher Assigned to Subject.\nWant to add new?");
                    reconfirmBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(NewBatchAddActivity.this, AddStaffActivity2.class);
                            String SubName = batchName.getText().toString().trim();
                            intent.putExtra("BatchName", SubName);
                            startActivity(intent);
                            finish();



                        }
                    });
                    reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                   // AlertDialog cnfDialogue = reconfirmBuilder.create();
                    reconfirmBuilder.show();
                } else {
                    MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(NewBatchAddActivity.this);

                   // AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewBatchAddActivity.this);
                    mBuilder.setTitle("Teachers");
                    mBuilder.setSingleChoiceItems(teacherListItem, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            batchTeacher.setText(teacherClasses.get(which).getTeacherName());
                            idval = teacherClasses.get(which).getTeacherID();
                            dialog.dismiss();
                        }
                    });
                   // AlertDialog mDialogue = mBuilder.create();
                    mBuilder.show();

                }
            }
        });



        batchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hour_of_12_hour_format;
                        String status = "AM";
                        if (hourOfDay > 11) {
                            if (hourOfDay == 12) {
                                hour_of_12_hour_format = 12;
                            } else {

                                hour_of_12_hour_format = hourOfDay - 12;
                            }
                            status = "PM";
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        batchTime.setText(hour_of_12_hour_format + ":" + String.format("%02d", minute) + " " + status);
                    }

                }, hr, min, DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();

            }
        });

        LocalDate date = LocalDate.now();
        final int curr_day = date.getDayOfMonth();
        final int curr_month = date.getMonthValue();
        final int curr_year = date.getYear();


        addBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String BatchName = batchName.getText().toString().trim();
                String newBatchName = BatchName.replace(" ", "_");
                String BatchTeacher = batchTeacher.getText().toString().trim();
                String BatchTime = batchTime.getText().toString().trim();
                String newBatchTime = BatchTime.replace(":", "_");
                String newBatchTime1 = newBatchTime.replace(" ", "_");
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());

                String TableName = newBatchName + newBatchTime1;

                String RegFee = regFee.getText().toString().trim();
                String MonthlyFee = monthlyFee.getText().toString().trim();

                boolean isExist = db.isTableExist(TableName);
                if (!validateBatchName() | !validateBachTime() | !validateBatchTeacher() | !validateRegFee() | !validateMonthlyFee()) {
                    return;
                } else {

                    if (!isExist) {

                        boolean isInserted = db.CreateBatch(BatchName, BatchTime, BatchTeacher, RegFee, MonthlyFee, idval);
                        int salary = db.getMonthlySalary(idval);
                        if (isInserted) {
                            db.insertIntoStaffFeesTable(BatchName, BatchTime, idval, curr_day, curr_month, curr_year, salary);
                            Toast.makeText(getApplicationContext(), "Table Created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Table creation Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(NewBatchAddActivity.this, BatchesActivity.class));
                        finish();
                    } else {
                        Toast.makeText(NewBatchAddActivity.this, "Batch Already exist", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewBatchAddActivity.this,BatchesActivity.class));
        finish();
    }

    private boolean validateBatchNameIsEmpty() {
        String nameInput = batchName.getText().toString().trim();
        return !nameInput.isEmpty();
    }



    private boolean validateBatchName(){
        String nameInput=batchName.getText().toString().trim();
        if (nameInput.isEmpty()){
            batchName.setError("Field can't be empty");
            return false;
        }else {
            batchName.setError(null);
            return true;
        }
    }


    private boolean validateBatchTeacher(){
        String phoneInput=batchTeacher.getText().toString().trim();
        if (phoneInput.isEmpty()){
            batchTeacher.setError("Field can't be empty");
            return false;
        }else {
            batchTeacher.setError(null);

            return true;
        }
    }


    private boolean validateBachTime(){
        String emailInput=batchTime.getText().toString().trim();
        if (emailInput.isEmpty()){
            batchTime.setError("Field can't be empty");
            return false;
        }else {
            batchTime.setError(null);

            return true;
        }
    }


    private boolean validateRegFee(){
        String addressInput=regFee.getText().toString().trim();
        if (addressInput.isEmpty()){
            regFee.setError("Field can't be empty");
            return false;
        }else {
            regFee.setError(null);
            //editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateMonthlyFee(){
        String eduInput=monthlyFee.getText().toString().trim();
        if (eduInput.isEmpty()){
            monthlyFee.setError("Field can't be empty");
            return false;
        }else {
            monthlyFee.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }
    }



}