package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter.OnItemClickListener;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

import java.util.ArrayList;

public class AddFeesActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fees);


        DataBaseHelper db=new DataBaseHelper(this);

        ArrayList<SubjectAdapter> subjectAdapters=new ArrayList<>();

        subjectAdapters= db.getDialogueLabelsAdapter();

        //String SubName=subjectAdapters.get(0).toString().trim();
        //String SubTime=subjectAdapters.get(1).toString().trim();




        final ArrayList<SubjectAdapter> finalArray=subjectAdapters;
        String value=finalArray.get(1).toString();
        Log.i("Val",value);

        mRecyclerView =findViewById(R.id.FeesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new RecyclerAdapter(subjectAdapters);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(getApplicationContext(),Students_In_Batch.class);
                intent.putExtra("Name",finalArray.get(position));
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),Students_In_Batch.class));
            }
        });




    }
}