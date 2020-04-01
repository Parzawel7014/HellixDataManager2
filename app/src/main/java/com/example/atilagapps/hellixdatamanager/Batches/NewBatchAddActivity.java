package com.example.atilagapps.hellixdatamanager.Batches;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

import java.util.ArrayList;
import java.util.Calendar;

public class NewBatchAddActivity extends AppCompatActivity {

    EditText batchName,batchTeacher,batchTime;
    Button addBatch;
    String[] listItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    String[] finalArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch_add);



        batchName=findViewById(R.id.BatchNameId);
        batchTeacher=findViewById(R.id.BatchTeacherId);
        batchTime=findViewById(R.id.BatchTimeId);
        addBatch=findViewById(R.id.addBatchButton);

      //  addBatch.setEnabled(false);

        Calendar c = Calendar.getInstance();
        final int hr = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);

        DataBaseHelper db=new DataBaseHelper(this);

        listItems = db.getDialogueLabels().toArray(new String[0]);


        batchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewBatchAddActivity.this);
                mBuilder.setTitle("Subjects");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        batchName.setText(listItems[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();
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
                            if (hourOfDay==12){
                                hour_of_12_hour_format = 12;
                            }else {

                                hour_of_12_hour_format = hourOfDay - 12;}
                            status = "PM";
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                        batchTime.setText(hour_of_12_hour_format + ":"+String.format("%02d",minute) +" "+ status );}

                }, hr, min, DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();

            }
        });


        addBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String BatchName=batchName.getText().toString().trim();
               String newBatchName=BatchName.replace(" ","_");
                String BatchTeacher=batchTeacher.getText().toString().trim();
                String BatchTime=batchTime.getText().toString().trim();
                String newBatchTime=BatchTime.replace(":","_");
                String newBatchTime1=newBatchTime.replace(" ","_");
                DataBaseHelper db=new DataBaseHelper(getApplicationContext());

                String TableName=newBatchName+newBatchTime1;

                boolean isExist=db.isTableExist(TableName);

                if (isExist!=true){

                boolean isInserted =db.CreateBatch(BatchName,BatchTime,BatchTeacher);
                if (isInserted == true) {
                    Toast.makeText(getApplicationContext(), "Table Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Table creation Unsuccessful", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(NewBatchAddActivity.this,BatchesActivity.class));
                finish();
            }else{
                    Toast.makeText(NewBatchAddActivity.this, "Batch Already exist", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }

}