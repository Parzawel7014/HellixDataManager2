package com.example.atilagapps.hellixdatamanager.StaffManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentRecyclerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class All_Staff extends AppCompatActivity {


    private Staff_Recycler_Adapter mAdapter;
    FloatingActionButton addStaff;

    MaterialButton paymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__staff);
        addStaff=findViewById(R.id.staffAddFloatingButtId);

        DataBaseHelper db=new DataBaseHelper(this);
        ArrayList<Staff_Class> staff_classes=new ArrayList<>();
        staff_classes=db.getStaffInfo();

        RecyclerView mRecyclerView = findViewById(R.id.staff_recycler_view_id);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new Staff_Recycler_Adapter(staff_classes);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        final ArrayList<Staff_Class> finalStaff_classes = staff_classes;

        mAdapter.setOnItemClickListener(new Staff_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(All_Staff.this, StaffDetailsActivity.class);
                intent.putExtra("StaffName", finalStaff_classes.get(position));
                startActivity(intent);

            }

            @Override
            public void onPaymentButtonClick(int position) {

                Intent intent=new Intent(getApplicationContext(),StaffPaymentActivity.class);
                intent.putExtra("StaffNameId", finalStaff_classes.get(position));
                startActivity(intent);


            }
        });
        Toolbar toolbar=findViewById(R.id.staffActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Staff");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddStaffActivity.class));
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.find_student_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);


        SearchView searchView=(SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((Staff_Recycler_Adapter) mAdapter).getFilter().filter(newText);
                return false;
            }
        });



        return true;
    }

}