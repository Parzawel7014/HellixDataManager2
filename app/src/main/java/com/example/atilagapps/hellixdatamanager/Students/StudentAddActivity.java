package com.example.atilagapps.hellixdatamanager.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;
import com.example.atilagapps.hellixdatamanager.R;

public class StudentAddActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);


        Toolbar toolbar=findViewById(R.id.StudentAddToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.container2, new PersonalInfo_Fragment());
        fragmentTransaction.commit();




    }


}