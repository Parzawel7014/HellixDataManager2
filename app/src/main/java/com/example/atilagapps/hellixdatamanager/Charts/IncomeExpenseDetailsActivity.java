package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.atilagapps.hellixdatamanager.R;

public class IncomeExpenseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense_details);


        Intent intent=getIntent();
        ExtraInExClass extraInExClass=intent.getParcelableExtra("PaymentDetails");

        assert extraInExClass != null;
        String id=extraInExClass.getId();
    }
}