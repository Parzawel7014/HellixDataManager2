package com.example.atilagapps.hellixdatamanager.SendSMS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class SendSMSActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String selectedText;
    String TuitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_s_m_s);
        ActivityCompat.requestPermissions(SendSMSActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        /*String msg="Hello";
        String num="8485004625";
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(num,null,msg,null,null);*/


        final DataBaseHelper db = new DataBaseHelper(this);

        ArrayList<SubjectAdapter> subjectAdapters = new ArrayList<>();

        subjectAdapters = db.getDialogueLabelsAdapter();


        mRecyclerView = findViewById(R.id.SMSRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(subjectAdapters);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        LinearLayout nothingLL=findViewById(R.id.nothingToShowSMSId);

        if (subjectAdapters.isEmpty()){
            nothingLL.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = findViewById(R.id.SMSActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send SMS");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TuitionName = db.getTuitionName();
        String Email ;




        final String[] msgBody = new String[]{
                "Fee Reminder",
                "Custom Message"
        };


        final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                String name = finalSubjectAdapters.get(position).getMsubject();
                String time = finalSubjectAdapters.get(position).getmTime();


                String newBatchName = name.replace(" ", "_");
                String newBatchTime = time.replace(":", "_");
                newBatchTime = newBatchTime.replace(" ", "_");

                final String TableName = newBatchName + newBatchTime;
                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(SendSMSActivity.this,R.style.AlertDialogTheme);


             //   AlertDialog.Builder mBuilder = new AlertDialog.Builder(SendSMSActivity.this);

                mBuilder.setItems(msgBody, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Arrays.asList(msgBody).get(which);

                        if (selectedText.equals("Fee Reminder")) {
                            sendFeeReminder(TableName);
                        }

                        if (selectedText.equals("Custom Message")) {
                            generateCustomMessage(TableName);
                        }


                    }
                });
               // AlertDialog mDialogue = mBuilder.create();
                mBuilder.show();

            }
        });


    }

    private void generateCustomMessage(final String tableName) {

        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(SendSMSActivity.this,R.style.AlertDialogTheme);

       // AlertDialog.Builder mBuilder = new AlertDialog.Builder(SendSMSActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.sms_body, null);
        final EditText smsBody = v.findViewById(R.id.smsBodyId);

        smsBody.setHint(" Type Your Custom Message ");


        mBuilder.setView(v)
                .setTitle("Custom SMS")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String body = smsBody.getText().toString();
                        SmsManager smsManager = SmsManager.getDefault();
                        DataBaseHelper db = new DataBaseHelper(SendSMSActivity.this);
                        ArrayList<String> studentsNum = new ArrayList<>();
                        studentsNum = db.getStudentsPhoneNumber(tableName);

                        for (int i = 0; i < studentsNum.size(); i++) {
                            smsManager.sendTextMessage(studentsNum.get(i), null, body+"\n\n"+
                                    " -"+TuitionName, null, null);
                        }

                    }
                });

       // AlertDialog mDialogue = mBuilder.create();
        mBuilder.show();




    }

    private void sendFeeReminder(final String tableName) {
        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(SendSMSActivity.this,R.style.AlertDialogTheme);

       // AlertDialog.Builder mBuilder = new AlertDialog.Builder(SendSMSActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.sms_body, null);
        final EditText smsBody = v.findViewById(R.id.smsBodyId);

        String SMS = " Dear Learner,"+"\n" +"You are requested to pay the Tuition fees for this month." +"<Date>"+"\n"+
                    " Ignore if already paid." +"\n"+
                    "-" + TuitionName;


        smsBody.setText(SMS);



        mBuilder.setView(v)
                .setTitle("Sample SMS!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper db = new DataBaseHelper(SendSMSActivity.this);
                        ArrayList<SMSInfoClass> studentsNum = new ArrayList<>();
                        studentsNum = db.getUnpaidStudentsPhoneNumber(tableName);
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < studentsNum.size(); i++) {

                            String finalSMS = "Dear "+studentsNum.get(i).getName()+","+"\n" +"You are requested to pay the Tuition fees for the date- " +"\n"+
                                    studentsNum.get(i).getDate()+"/"+studentsNum.get(i).getMonth()+"/"+studentsNum.get(i).getYear()+"\n"+
                                    " Ignore if already paid." +"\n"+
                                    "-" + TuitionName;

                            String contact=studentsNum.get(i).getContact();
                            smsManager.sendTextMessage(contact, null, finalSMS, null, null);
                        }

                    }
                });

        //AlertDialog mDialogue = mBuilder.create();
        mBuilder.show();


    }
}