package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter.OnItemClickListener;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class AddFeesActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    StudentClass studentClass;
    SubjectAdapter subjectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fees);


        final DataBaseHelper db=new DataBaseHelper(this);

        ArrayList<SubjectAdapter> subjectAdapters=new ArrayList<>();

        subjectAdapters= db.getDialogueLabelsAdapter();

        //String SubName=subjectAdapters.get(0).toString().trim();
        //String SubTime=subjectAdapters.get(1).toString().trim();




        final ArrayList<SubjectAdapter> finalArray=subjectAdapters;

        mRecyclerView =findViewById(R.id.FeesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new RecyclerAdapter(subjectAdapters);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        Toolbar toolbar=findViewById(R.id.FeesActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Batches");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(int position) {

                String name= finalSubjectAdapters.get(position).getMsubject();
                String time=finalSubjectAdapters.get(position).getmTime();




                String newBatchName = name.replace(" ", "_");
                String newBatchTime = time.replace(":", "_");
                newBatchTime = newBatchTime.replace(" ", "_");

                final String TableName = newBatchName + newBatchTime;

                int res=db.checkStudentsPresent(TableName);

                if (res==1){
                    AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(AddFeesActivity.this);
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setMessage("No Students In Batch");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog cnfDialogue = reconfirmBuilder.create();
                    cnfDialogue.show();

                }else {

                /*    LocalDate date = LocalDate.now();

                    int month = db.getLastMonth(TableName);
                    int year = db.getLastYear(TableName);

                    int curr_mont = date.getMonthValue();
                    int curr_year = date.getYear();
                    int curr_day = date.getDayOfMonth();


                    int occ = db.getOccurrence(TableName, curr_mont);
                    if (occ != 1) {
                        if (curr_year == year) {
                            if (curr_mont == (month + 1)) {
                                boolean res1 = db.insertIntoFeesTable(TableName, curr_mont, curr_year);
                                if (res1) {
                                    Toast.makeText(AddFeesActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddFeesActivity.this, "No Student", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        if (curr_year > (year)) {
                            boolean res2 = db.insertIntoFeesTable(TableName, curr_mont, curr_year);
                            if (res2) {
                                Toast.makeText(AddFeesActivity.this, "Ok", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
*/

                    Intent intent = new Intent(getApplicationContext(), Students_In_Batch.class);
                    intent.putExtra("Name", finalArray.get(position));
                    startActivity(intent);
                }

                //startActivity(new Intent(getApplicationContext(),Students_In_Batch.class));
            }
        });




    }
}