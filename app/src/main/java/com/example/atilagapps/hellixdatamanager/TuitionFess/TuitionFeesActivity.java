package com.example.atilagapps.hellixdatamanager.TuitionFess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.atilagapps.hellixdatamanager.R;

public class TuitionFeesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_fees);

        CardView addfeesCard=findViewById(R.id.AddFeesCardId);

        addfeesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddFeesActivity.class));
            }
        });
    }
}