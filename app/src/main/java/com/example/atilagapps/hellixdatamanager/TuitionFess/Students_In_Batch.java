package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;

import java.util.ArrayList;

public class Students_In_Batch extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GetStudentRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__in__batch);

        DataBaseHelper db=new DataBaseHelper(this);


        Intent intent=getIntent();
        SubjectAdapter subjectAdapter=intent.getParcelableExtra("Name");
        assert subjectAdapter != null;
        String name=subjectAdapter.getMsubject();
        String time=subjectAdapter.getmTime();


        String newBatchName=name.replace(" ","_");
        String newBatchTime = time.replace(":", "_");
        newBatchTime=newBatchTime.replace(" ","_");

        String TableName=newBatchName+newBatchTime;


        ArrayList<StudentClass> studentClasses=new ArrayList<>();

        studentClasses=db.getAllStudents(TableName);



        mRecyclerView =findViewById(R.id.GetStudentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new GetStudentRecyclerAdapter(studentClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
}