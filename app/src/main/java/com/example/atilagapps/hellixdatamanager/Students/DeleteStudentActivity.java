package com.example.atilagapps.hellixdatamanager.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.Fragments.StudentActivity;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class DeleteStudentActivity extends AppCompatActivity {


    private FindStudentRecyclerAdapter mAdapter;
    ArrayList<FindStudent> findStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);


        Toolbar toolbar=findViewById(R.id.deleteStuToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Remove Student");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findStudents=new ArrayList<>();
        final DataBaseHelper db=new DataBaseHelper(this);
        findStudents=db.getStudentInfo();
        RecyclerView mRecyclerView = findViewById(R.id.deleteStuRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new FindStudentRecyclerAdapter(findStudents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        if (findStudents.isEmpty()){
            MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(DeleteStudentActivity.this);
            mBuilder.setTitle("Alert")
                    .setIcon(R.drawable.alert)
                    .setMessage("No Student Data To Fetch")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(DeleteStudentActivity.this, StudentActivity.class));
                            finish();
                        }
                    }).show();

        }


        MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(DeleteStudentActivity.this);
        mBuilder.setTitle("Caution!")
                .setIcon(R.drawable.alert)
                .setMessage("Data will be permanently deleted for the selected student including fee entry data.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // startActivity(new Intent(DeleteStudentActivity.this, StudentActivity.class));
                        //finish();
                    }
                }).show();

        mBuilder.setCancelable(false);


        final ArrayList<FindStudent> finalFindStudents = findStudents;
        mAdapter.setOnItemClickListener(new FindStudentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                MaterialAlertDialogBuilder mBuilder=new MaterialAlertDialogBuilder(DeleteStudentActivity.this);
                mBuilder.setTitle("Caution!")
                        .setIcon(R.drawable.alert)
                        .setMessage("Data will be permanently deleted for the selected student including fee entry data.\nProceed to delete?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String id=findStudents.get(position).getmStudentID();
                                boolean res=db.deleteStudentEntry(id);
                                if (res){
                                    findStudents.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                   // mAdapter.notifyItemRangeChanged(position,findStudents.size()-position);
                                  //  notifyItemRemoved(this.getLayoutPosition());
                                    Toast.makeText(DeleteStudentActivity.this, "Record Deleted Successfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(DeleteStudentActivity.this, "Record Deletion Failed", Toast.LENGTH_SHORT).show();
                                }

                                // startActivity(new Intent(DeleteStudentActivity.this, StudentActivity.class));
                                //finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
              //  Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
               // intent.putExtra("StudentName", finalFindStudents.get(position));
               // startActivity(intent);
            }
        });

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




}