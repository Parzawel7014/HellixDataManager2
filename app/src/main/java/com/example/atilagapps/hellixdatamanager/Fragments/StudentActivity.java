package com.example.atilagapps.hellixdatamanager.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentActivity;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;

public class StudentActivity extends AppCompatActivity {


    CardView addStudentCard,findStudentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        addStudentCard=findViewById(R.id.AddStudentCardId);
        findStudentCard=findViewById(R.id.FindStudentCardId);


        Toolbar toolbar=findViewById(R.id.StudentPortalToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Portal");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentAddActivity.class));
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