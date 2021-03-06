package com.example.atilagapps.hellixdatamanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class GetTuitionInfo extends AppCompatActivity {


    TextInputEditText instName,instEmail,instAddress,admin1Name,admin2Name,phoneNum;
    Button submit;
    String S_instName,S_instEmail,S_instAddress,S_admin1Name,S_admin2Name,S_phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tuition_info);

        final DataBaseHelper db=new DataBaseHelper(this);

        instName=findViewById(R.id.InstitutionNameId);
        instEmail=findViewById(R.id.editTextTuitionEmailId);
        instAddress=findViewById(R.id.editTextTuitionAddressId);
        admin1Name=findViewById(R.id.editTextAdmin1Id);
        admin2Name=findViewById(R.id.editTextAdmin2Id);
        phoneNum=findViewById(R.id.editTextTuitionPhoneId);
        submit=findViewById(R.id.btn1Id);

        Toolbar toolbar=findViewById(R.id.TuitionInfoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Organization Info");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (admin1Name!=null || admin2Name!=null) {

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    S_instName = Objects.requireNonNull(instName.getText()).toString().trim();
                    S_instEmail = Objects.requireNonNull(instEmail.getText()).toString().trim();
                    S_instAddress = Objects.requireNonNull(instAddress.getText()).toString().trim();
                    S_admin1Name = Objects.requireNonNull(admin1Name.getText()).toString().trim();
                    S_admin2Name = Objects.requireNonNull(admin2Name.getText()).toString().trim();
                    S_phoneNum= Objects.requireNonNull(phoneNum.getText()).toString().trim();



                    if (!validateName() | !validatePhone()| !validateAddress() | !validateEmail() | !validateAdmin1()) {
                        return;
                    } else {
                        db.insertIntoTuitionTable(S_instName, S_instEmail, S_instAddress, S_admin1Name, S_admin2Name, S_phoneNum);

                    }

                 /*   SharedPreferences sharedPreferences=getSharedPreferences("TuitionInfo",MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Tuition Name",S_instName);
                    editor.putString("Tuition Email",S_instEmail);
                    editor.putString("Tuition Phone",S_phoneNum);
                    editor.putString("Tuition Address",S_instAddress);
                    editor.putString("Tuition Admin1",S_admin1Name);
                    editor.putString("Tuition Admin2",S_admin2Name);
                    editor.apply();
*/
                  //  db.insertIntoTuitionTable(S_instName,S_instEmail,S_instAddress,S_admin1Name,S_admin2Name,S_phoneNum);
                    startActivity(new Intent(GetTuitionInfo.this,MainActivity.class));
                    finish();
                }
            });
        }

    }


    private boolean validateName(){
        String nameInput=instName.getText().toString().trim();
        if (nameInput.isEmpty()){
            instName.setError("Field can't be empty");
            return false;
        }else {
            instName.setError(null);

            return true;
        }
    }


    private boolean validatePhone(){
        String phoneInput=phoneNum.getText().toString().trim();
        if (phoneInput.isEmpty()){
            phoneNum.setError("Field can't be empty");
            return false;
        }else {
            phoneNum.setError(null);

            return true;
        }
    }


    private boolean validateEmail(){
        String emailInput=instEmail.getText().toString().trim();
        if (emailInput.isEmpty()){
            instEmail.setError("Field can't be empty");
            return false;
        }else {
            instEmail.setError(null);

            return true;
        }
    }


    private boolean validateAddress(){
        String addressInput=instAddress.getText().toString().trim();
        if (addressInput.isEmpty()){
            instAddress.setError("Field can't be empty");
            return false;
        }else {
            instAddress.setError(null);
            //editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAdmin1(){
        String eduInput=admin1Name.getText().toString().trim();
        if (eduInput.isEmpty()){
            admin1Name.setError("Field can't be empty");
            return false;
        }else {
            admin1Name.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

}