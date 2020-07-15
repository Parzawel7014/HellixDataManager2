package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.atilagapps.hellixdatamanager.Batches.RecyclerAdapter;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExtraIncomeExpense extends AppCompatActivity {

    ExtraInExRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton addExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_income_expense);


        DataBaseHelper db=new DataBaseHelper(this);
        ArrayList<ExtraInExClass> extraInExClasses=new ArrayList<>();
        extraInExClasses=db.getExtraInExPayments();

        addExpense=findViewById(R.id.IncomeExpenseFloatingButtonId);


        RecyclerView recyclerView=findViewById(R.id.ExtraInExId);
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new ExtraInExRecyclerAdapter(extraInExClasses);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        Toolbar toolbar=findViewById(R.id.ExtraIncomeExpenseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Other Transactions");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<ExtraInExClass> finalExtraInExClasses = extraInExClasses;
        mAdapter.setOnItemClickListener(new ExtraInExRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent=new Intent(getApplicationContext(),IncomeExpenseDetailsActivity.class);
                intent.putExtra("PaymentDetails", finalExtraInExClasses.get(position));
                startActivity(intent);



            }
        });


        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddIncomeExenseActivity.class));
                finish();
            }
        });

    }
}