package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.atilagapps.hellixdatamanager.R;

public class ChartActivity extends AppCompatActivity {

    CardView expCard,profitLossCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        expCard=findViewById(R.id.ExpenditureCardId);
        profitLossCard=findViewById(R.id.ProfitLossCardId);


        expCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ExtraIncomeExpense.class));
            }
        });


        profitLossCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),IncomeExpenseActivity.class));
            }
        });
    }
}