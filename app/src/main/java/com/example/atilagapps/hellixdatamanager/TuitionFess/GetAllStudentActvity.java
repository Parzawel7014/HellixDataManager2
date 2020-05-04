package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Reciept.CreatePDF;
import com.example.atilagapps.hellixdatamanager.Students.DetailsActivity;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;

import java.util.ArrayList;

public class GetAllStudentActvity extends AppCompatActivity {

   private FindStudentRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_student_actvity);

        ArrayList<FindStudent> findStudents=new ArrayList<>();
        final DataBaseHelper db=new DataBaseHelper(this);
        findStudents=db.getStudentInfo();
        RecyclerView mRecyclerView = findViewById(R.id.GenerateReceiptRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        Toolbar toolbar=findViewById(R.id.GenerateReceiptToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Generate Receipt");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArrayList<FindStudent> finalFindStudents = findStudents;
        mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(), CreatePDF.class);
                intent.putExtra("STUDENTIDPDF", finalFindStudents.get(position));
                startActivity(intent);

            }
        });


    }
}