package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;

import java.util.ArrayList;

public class Students_In_Batch extends AppCompatActivity implements AmountDialogueClass.AmountDialogueListener {

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

        mAdapter.setOnItemClickListener(new GetStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDialogue();
            }
        });
    }


    private void openDialogue() {
        AmountDialogueClass amountDialogueClass=new AmountDialogueClass();
        amountDialogueClass.show(getSupportFragmentManager(),null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getAmount(String amount) {
        TextView statusTxt=findViewById(R.id.AmountPaidStatusId);
        statusTxt.setTextColor(Color.GREEN);
        statusTxt.setText("Paid");
    }
}