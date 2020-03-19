package com.example.atilagapps.hellixdatamanager.Batches;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

import java.util.Calendar;

public class NewBatchAddActivity extends AppCompatActivity {

    EditText batchName,batchTeacher,batchTime;
    Button addBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch_add);


        batchName=findViewById(R.id.BatchNameId);
        batchTeacher=findViewById(R.id.BatchTeacherId);
        batchTime=findViewById(R.id.BatchTimeId);
        addBatch=findViewById(R.id.addBatchButton);


        Calendar c = Calendar.getInstance();
        final int hr = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);


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
                String BatchTeacher=batchTeacher.getText().toString().trim();
                String BatchTime=batchTime.getText().toString().trim();
                DataBaseHelper db=new DataBaseHelper(getApplicationContext());
                boolean isInserted =db.CreateBatch(BatchName,BatchTime,BatchTeacher);
                if (isInserted == true) {
                    Toast.makeText(getApplicationContext(), "Table Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Table creation Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}