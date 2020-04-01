package com.example.atilagapps.hellixdatamanager.Students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        FindStudent findStudent=intent.getParcelableExtra("StudentName");

        assert findStudent != null;
        String name=findStudent.getmStudentName();

        TextView nameText=findViewById(R.id.DetailNameTextID);

        nameText.setText(name);
    }
}