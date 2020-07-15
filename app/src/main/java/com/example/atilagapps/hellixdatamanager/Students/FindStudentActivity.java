package com.example.atilagapps.hellixdatamanager.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.Fragments.CoursesClass;
import com.example.atilagapps.hellixdatamanager.Fragments.StudentActivity;
import com.example.atilagapps.hellixdatamanager.MainActivity;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.StaffManager.All_Staff;
import com.example.atilagapps.hellixdatamanager.StaffManager.StaffPaymentActivity;
import com.example.atilagapps.hellixdatamanager.StaffManager.Staff_Recycler_Adapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class FindStudentActivity extends AppCompatActivity {


    private FindStudentRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_student);
        ArrayList<FindStudent> findStudents=new ArrayList<>();
        DataBaseHelper db=new DataBaseHelper(this);
        findStudents=db.getStudentInfo();
        RecyclerView mRecyclerView = findViewById(R.id.FindStudentRecyclerViewId);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);




        if (findStudents.isEmpty()){
            MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(FindStudentActivity.this);
            mBuilder.setTitle("Alert")
                    .setIcon(R.drawable.alert)
                    .setMessage("No Student Data To Fetch")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(FindStudentActivity.this, StudentActivity.class));
                            finish();
                        }
                    }).show();

        }




        final ArrayList<FindStudent> finalFindStudents = findStudents;
        mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                intent.putExtra("StudentName", finalFindStudents.get(position));
                startActivity(intent);
            }
        });

       // getSupportActionBar().setTitle("Find Student");


        Toolbar toolbar=findViewById(R.id.FindStuToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.find_student_menu,menu);
      //  inflater.inflate(R.menu.home_menu,menu);
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}