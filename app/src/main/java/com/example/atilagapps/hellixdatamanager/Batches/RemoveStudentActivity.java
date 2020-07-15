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

import java.util.ArrayList;

public class RemoveStudentActivity extends AppCompatActivity {


    private FindStudentRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_student);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        String BatchName = bundle.getString("BatchName");
        String BatchTime = bundle.getString("BatchTime");

        assert BatchName != null;
        String newBatchName = BatchName.replace(" ", "_");
        String newBatchTime = BatchTime.replace(":", "_");
        newBatchTime = newBatchTime.replace(" ", "_");

        final String TableName = newBatchName + newBatchTime;


        ArrayList<FindStudent> findStudents = new ArrayList<>();
        final DataBaseHelper db = new DataBaseHelper(this);

        findStudents = db.getStudentInBatch(TableName);

        RecyclerView mRecyclerView = findViewById(R.id.RemoveStudentRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = findViewById(R.id.RemoveStudentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Remove Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ArrayList<FindStudent> finalFindStudents = findStudents;


        mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                final String id = finalFindStudents.get(position).getmStudentID();
                AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(RemoveStudentActivity.this);
                reconfirmBuilder.setTitle("Confirm");
                reconfirmBuilder.setMessage("Confirm to delete record");
                reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.removeStudent(TableName, id);
                        Toast.makeText(RemoveStudentActivity.this, "Student Removed From Batch", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(intent);
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