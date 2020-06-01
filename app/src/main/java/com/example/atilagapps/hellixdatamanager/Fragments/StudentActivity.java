package com.example.atilagapps.hellixdatamanager.Fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentActivity;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {


    CardView addStudentCard,findStudentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        addStudentCard=findViewById(R.id.AddStudentCardId);
        findStudentCard=findViewById(R.id.FindStudentCardId);
        DataBaseHelper db=new DataBaseHelper(StudentActivity.this);
        ArrayList<SubjectAdapter> subjectAdapters=new ArrayList<>();
        subjectAdapters= db.getDialogueLabelsAdapter();

        Toolbar toolbar=findViewById(R.id.StudentPortalToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Portal");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;

        addStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (finalSubjectAdapters.isEmpty()){
                    AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(v.getContext());
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setMessage("First Add Batches \n Click on Batches Portal to add Batches");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog cnfDialogue = reconfirmBuilder.create();
                    cnfDialogue.show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), StudentAddActivity.class));
                }
            }


        });


        findStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FindStudentActivity.class));
            }
        });

    }
}