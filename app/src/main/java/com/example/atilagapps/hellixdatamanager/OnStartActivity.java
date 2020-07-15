package com.example.atilagapps.hellixdatamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class OnStartActivity extends AppCompatActivity {

    DataBaseHelper dbRef;
    private static int SPLASH_TIMEOUT=1500;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);
        dbRef=new DataBaseHelper(this);
        textView=findViewById(R.id.TitleId);

        final int count =dbRef.getTuitionInfoCount();

        boolean isFirstRun=getSharedPreferences("PREFERENCES",MODE_PRIVATE)
                .getBoolean("isFirstRun",true);


        animation();

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

            boolean FingerPrint=getSharedPreferences("FINGERPRINT",MODE_PRIVATE)
                    .getBoolean("F_Flag",true);

            if (FingerPrint) {
              //  startActivity(new Intent(OnStartActivity.this,FingerPrintActivity.class));
                //finish();
               // if (count == 0) {
                 //   startActivity(new Intent(getApplicationContext(), GetTuitionInfo.class));
                   // finish();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Executor executor= ContextCompat.getMainExecutor(getApplicationContext());


                        final BiometricPrompt biometricPrompt=new BiometricPrompt(OnStartActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                            }

                            @Override
                            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                if (count == 0) {
                                    startActivity(new Intent(getApplicationContext(), GetTuitionInfo.class));
                                    finish();
                                }else {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                            }
                        });

                        BiometricPrompt.PromptInfo promptInfo= new BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Login")
                                .setDescription("Use Fingerprint to login!")
                                .setDeviceCredentialAllowed(true)
                                .build();
                        biometricPrompt.authenticate(promptInfo);
                    }
                },SPLASH_TIMEOUT);



                } else {
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

    private void animation() {

        Animation animations= AnimationUtils.loadAnimation(this,R.anim.anim);
        textView.startAnimation(animations);

    }
}