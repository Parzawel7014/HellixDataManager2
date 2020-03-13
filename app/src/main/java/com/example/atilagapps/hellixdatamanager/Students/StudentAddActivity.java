package com.example.atilagapps.hellixdatamanager.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.atilagapps.hellixdatamanager.Fragments.HomeFragment;
import com.example.atilagapps.hellixdatamanager.Fragments.PersonalInfo_Fragment;
import com.example.atilagapps.hellixdatamanager.R;

public class StudentAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container2, new PersonalInfo_Fragment());
        fragmentTransaction.commit();
    }
}