package com.example.atilagapps.hellixdatamanager.Batches;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.DetailsActivity;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AmountDialogueClass;
import com.example.atilagapps.hellixdatamanager.TuitionFess.StudentClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddExistingStudentToBatch extends AppCompatActivity {


    private FindStudentRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_existing_student_to_batch);

        ArrayList<FindStudent> findStudents=new ArrayList<>();
        final DataBaseHelper db=new DataBaseHelper(this);
        findStudents=db.getStudentInfo();
        RecyclerView mRecyclerView = findViewById(R.id.FindExistingStudentRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        assert bundle != null;
        String BatchName=bundle.getString("BatchName");
        String BatchTime=bundle.getString("BatchTime");

        assert BatchName != null;
        String newBatchName = BatchName.replace(" ", "_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

        final String TableName = newBatchName + newBatchTime;



        Toolbar toolbar=findViewById(R.id.FindExitingToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<FindStudent> finalFindStudents = findStudents;



            mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onItemClick(int position) {

                    final String name = finalFindStudents.get(position).getmStudentName();
                    final String id = finalFindStudents.get(position).getmStudentID();

                    boolean res = db.checkAlreadyPresent(TableName, id);
                    if (!res) {

                        LocalDate today = LocalDate.now();
                        final String formattedDate = today.format(DateTimeFormatter.ofPattern("d/M/yyyy"));

                        AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(AddExistingStudentToBatch.this);
                        reconfirmBuilder.setTitle("Confirm");
                        reconfirmBuilder.setMessage("Press Ok to add student to batch");
                        reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.insertIntoTablesExisting(TableName, id, name, formattedDate, "Pending");
                                startActivity(new Intent(AddExistingStudentToBatch.this,BatchesActivity.class));
                                finish();

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

                    } else {
                        Toast.makeText(AddExistingStudentToBatch.this, "Student Already Present", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.find_student_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((FindStudentRecyclerAdapter) mAdapter).getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}