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

import java.util.ArrayList;

public class AllStuInBatchActivity extends AppCompatActivity {
    private FindStudentRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stu_in_batch);



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

        RecyclerView mRecyclerView = findViewById(R.id.AllStudentRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = findViewById(R.id.AllStudentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final ArrayList<FindStudent> finalFindStudents = findStudents;


        mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("StudentName", finalFindStudents.get(position));
                startActivity(intent);
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