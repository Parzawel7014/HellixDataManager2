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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    String batchActiveStatus;
    ArrayList<SubjectAdapter> subjectAdapters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        //RecyclerView recyclerView=findViewById(R.id.RecyclerVieId);


        final DataBaseHelper db=new DataBaseHelper(this);

        subFloatButt=findViewById(R.id.SubjectFloatingButtonId);

       subjectAdapters=new ArrayList<>();

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
                "Show All Students",
                "Add Existing Student",
                "Remove Student",
                "Suspend Batch",
                "Delete Batch"
        };


        final String[] value2 = new String[]{
                "Edit Batch",
                "Show All Students",
                "Add Existing Student",
                "Remove Student",
                "Activate Batch",
                "Delete Batch"
        };

      //  final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {



                final String batchName= subjectAdapters.get(position).getMsubject();
                final String batchTime=subjectAdapters.get(position).getmTime();
                final String batchTeacher=subjectAdapters.get(position).getmTeacher();
                batchActiveStatus=subjectAdapters.get(position).getmBatchActiveStat();


                assert batchName != null;
                String newBatchName = batchName.replace(" ", "_");
                String newBatchTime = batchTime.replace(":", "_");
                newBatchTime = newBatchTime.replace(" ", "_");

                final String TableName = newBatchName + newBatchTime;



                if (batchActiveStatus.equals("Active")) {


                    MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);

                    // AlertDialog.Builder mBuilder = new AlertDialog.Builder(BatchesActivity.this);
                    mBuilder.setItems(value, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedText = Arrays.asList(value).get(which);
                            // Toast.makeText(BatchesActivity.this, selectedText, Toast.LENGTH_SHORT).show();


                            if (selectedText.equals("Show All Students")) {

                                int res1 = db.checkStudentsPresent(TableName);
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students to Show");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), AllStuInBatchActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    bundle.putString("BatchTeacher", batchTeacher);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }

                            if (selectedText.equals("Edit Batch")) {
                                Intent intent = new Intent(getApplicationContext(), UpdateBatchActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("BatchName", batchName);
                                bundle.putString("BatchTime", batchTime);
                                bundle.putString("BatchTeacher", batchTeacher);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }


                            if (selectedText.equals("Add Existing Student")) {


                                int res1 = db.checkStudentsPresents();
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students to Add");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), AddExistingStudentToBatch.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }
                            if (selectedText.equals("Remove Student")) {

                                int res1 = db.checkStudentsPresent(TableName);
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students In Batch");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), RemoveStudentActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }


                            if (selectedText.equals("Suspend Batch")) {
                                MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getApplicationContext());
                                reconfirmBuilder.setTitle("Confirm");
                                reconfirmBuilder.setMessage("Press Ok to Suspend Batch!\nBy suspending the batch means the data will not be deleted of this batch.");
                                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.suspendBatch(TableName);

                                      //  subjectAdapters.get(position).setmBatchActiveStat("Suspended");
                                        subjectAdapters=new ArrayList<>();
                                        subjectAdapters=db.getDialogueLabelsAdapter();
                                        mLayoutManager=new LinearLayoutManager(BatchesActivity.this);
                                        mAdapter=new RecyclerAdapter(subjectAdapters);

                                        mRecyclerView.setLayoutManager(mLayoutManager);
                                        mRecyclerView.setAdapter(mAdapter);
                                        mAdapter.notifyDataSetChanged();



                                        Toast.makeText(BatchesActivity.this, "Successfully Suspended", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                                //AlertDialog cnfDialogue = reconfirmBuilder.create();
                                //cnfDialogue.show();


                            }


                            if (selectedText.equals("Delete Batch")) {
                                MaterialAlertDialogBuilder reconfirmBuilder = new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getApplicationContext());
                                reconfirmBuilder.setTitle("Warning");
                                reconfirmBuilder.setMessage("Press Ok to Delete Batch!\nThis option will clear all the data of the batch!");
                                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        db.deleteBatch(TableName);
                                        subjectAdapters.remove(position);
                                        mAdapter.notifyItemRemoved(position);
                                        Toast.makeText(BatchesActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                                //AlertDialog cnfDialogue = reconfirmBuilder.create();
                                //cnfDialogue.show();


                            }


                        }
                    }).show();
                }


                if (batchActiveStatus.equals("In-Active")){

                    MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);

                    // AlertDialog.Builder mBuilder = new AlertDialog.Builder(BatchesActivity.this);
                    mBuilder.setItems(value2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedText = Arrays.asList(value2).get(which);
                            // Toast.makeText(BatchesActivity.this, selectedText, Toast.LENGTH_SHORT).show();


                            if (selectedText.equals("Show All Students")){

                                int res1 = db.checkStudentsPresent(TableName);
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students to Show");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                }
                                else {
                                    Intent intent= new Intent(getApplicationContext(), AllStuInBatchActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    bundle.putString("BatchTeacher", batchTeacher);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }

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


                                int res1 = db.checkStudentsPresents();
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    //AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students to Add");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                }
                                else {
                                    Intent intent = new Intent(getApplicationContext(), AddExistingStudentToBatch.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }
                            if (selectedText.equals("Remove Student")) {

                                int res1 = db.checkStudentsPresent(TableName);
                                if (res1 == 1) {
                                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                    // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(BatchesActivity.this);
                                    reconfirmBuilder.setTitle("Alert");
                                    reconfirmBuilder.setMessage("No Students In Batch");
                                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();

                                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                                    //cnfDialogue.show();

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), RemoveStudentActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("BatchName", batchName);
                                    bundle.putString("BatchTime", batchTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }


                            if (selectedText.equals("Activate Batch")) {
                                MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getApplicationContext());
                                reconfirmBuilder.setTitle("Confirm");
                                reconfirmBuilder.setMessage("Press Ok to Activate Batch!");
                                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                       db.activateBatch(TableName);

                                       // finalSubjectAdapters.remove(position);

                                        subjectAdapters.get(position).setmBatchActiveStat("Active");
                                        mAdapter.notifyDataSetChanged();
                                        //mAdapter.notifyItemChanged(position);

                                        Toast.makeText(BatchesActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                                //AlertDialog cnfDialogue = reconfirmBuilder.create();
                                //cnfDialogue.show();


                            }


                            if (selectedText.equals("Delete Batch")) {
                                MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(BatchesActivity.this,R.style.AlertDialogTheme);
                                // AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getApplicationContext());
                                reconfirmBuilder.setTitle("Warning");
                                reconfirmBuilder.setMessage("Press Ok to Delete Batch!\nThis option will clear all the data of the batch!");
                                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        db.deleteBatch(TableName);
                                        subjectAdapters.remove(position);
                                        mAdapter.notifyItemRemoved(position);

                                        Toast.makeText(BatchesActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });
                                reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                                //AlertDialog cnfDialogue = reconfirmBuilder.create();
                                //cnfDialogue.show();


                            }




                        }
                    }).show();

                    // AlertDialog mDialogue = mBuilder.create();
                    // mDialogue.show();
                }

               // AlertDialog mDialogue = mBuilder.create();
               // mDialogue.show();
            }

        });





    }
}