package com.example.atilagapps.hellixdatamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class OnStartActivity extends AppCompatActivity {

    DataBaseHelper dbRef;
    private static int SPLASH_TIMEOUT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);
        dbRef=new DataBaseHelper(this);

        boolean isFirstRun=getSharedPreferences("PREFERENCES",MODE_PRIVATE)
                .getBoolean("isFirstRun",true);

        if (isFirstRun){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), GetTuitionInfo.class));
                    finish();
                }
            }, SPLASH_TIMEOUT);
            getSharedPreferences("PREFERENCES",MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun",false).apply();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, SPLASH_TIMEOUT);


        }

    }
}