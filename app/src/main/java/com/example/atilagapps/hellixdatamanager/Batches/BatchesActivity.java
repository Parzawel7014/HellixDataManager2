package com.example.atilagapps.hellixdatamanager.Batches;

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
import android.view.View;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FloatingActionButton subFloatButt;
    String selectedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        //RecyclerView recyclerView=findViewById(R.id.RecyclerVieId);


        final DataBaseHelper db=new DataBaseHelper(this);

        subFloatButt=findViewById(R.id.SubjectFloatingButtonId);

        ArrayList<SubjectAdapter> subjectAdapters=new ArrayList<>();

        subjectAdapters= db.getDialogueLabelsAdapter();

        mRecyclerView =findViewById(R.id.RecyclerVieId);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new RecyclerAdapter(subjectAdapters);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar=findViewById(R.id.BatchesActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BATCHES");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        subFloatButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewBatchAddActivity.class));
                finish();
            }
        });


        final String[] value = new String[]{
                "Edit Batch",
                "Add Existing Student",
                "Remove Student",
                "Delete Batch"
        };

        final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                final String batchName= finalSubjectAdapters.get(position).getMsubject();
                final String batchTime=finalSubjectAdapters.get(position).getmTime();
                final String batchTeacher=finalSubjectAdapters.get(position).getmTeacher();


                assert batchName != null;
                String newBatchName = batchName.replace(" ", "_");
                String newBatchTime = batchTime.replace(":", "_");
                newBatchTime = newBatchTime.replace(" ", "_");

                final String TableName = newBatchName + newBatchTime;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BatchesActivity.this);

                mBuilder.setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedText = Arrays.asList(value).get(which);
                        Toast.makeText(BatchesActivity.this, selectedText, Toast.LENGTH_SHORT).show();


                        if (selectedText.equals("Edit Batch")){
                            Intent intent= new Intent(getApplicationContext(), UpdateBatchActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("BatchName",batchName);
                            bundle.putString("BatchTime",batchTime);
                            bundle.putString("BatchTeacher",batchTeacher);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }


                        if (selectedText.equals("Add Existing Student")) {
                                Intent intent = new Intent(getApplicationContext(), AddExistingStudentToBatch.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("BatchName", batchName);
                                bundle.putString("BatchTime", batchTime);
                                intent.putExtras(bundle);
                                startActivity(intent);

                        }
                            if (selectedText.equals("Remove Student")) {

                                int res1 = db.checkStudentsPresent(TableName);
                                if (res1 == 1) {
                                    AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
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

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), RemoveStudentActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }


                            if (selectedText.equals("Delete Batch")) {

                                AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getApplicationContext());
                                reconfirmBuilder.setTitle("Confirm");
                                reconfirmBuilder.setMessage("Press Ok to Delete Batch");
                                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        db.deleteBatch(TableName);

                                        Toast.makeText(BatchesActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog cnfDialogue = reconfirmBuilder.create();
                                cnfDialogue.show();


                            }



                    }
                });

                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();
            }

        });





    }
}