package com.example.atilagapps.hellixdatamanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class TuitionProfile extends AppCompatActivity {

    CircularImageView imgPro;
    TextView TuiName,TuiAddress,TuiPhone,TuiEmail,TuiAdmin1,TuiAdmin2;
    MaterialButton EditButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_profile);

        imgPro=findViewById(R.id.TuitionProfId);
        TuiName=findViewById(R.id.InstitutionNameTextId);
        TuiAddress=findViewById(R.id.InstitutionAddressTxtId);
        TuiPhone=findViewById(R.id.InstitutionPhoneTxtId);
        TuiEmail=findViewById(R.id.InstitutionEmailTxtId);
        TuiAdmin1=findViewById(R.id.Admin1TextId);
        TuiAdmin2=findViewById(R.id.Admin2TextId);
        EditButt=findViewById(R.id.EditTuitionProfileButtId);


        Toolbar toolbar=findViewById(R.id.ProfileTuiInfoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Financial");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DataBaseHelper db=new DataBaseHelper(this);

        ArrayList<TuitionEdit> tuitionDetails=new ArrayList<>();

        tuitionDetails=db.getTuitionDetails();

        String T_Name=tuitionDetails.get(0).getTuiName();
        String T_Add=tuitionDetails.get(0).getTuiAdd();
        String T_Phone=tuitionDetails.get(0).getTuiPhone();
        String T_Email=tuitionDetails.get(0).getTuiEmail();
        String T_Admin1=tuitionDetails.get(0).getTuiAdmin1();
        String T_Admin2=tuitionDetails.get(0).getTuiAdmin2();

        TuiName.setText(T_Name);
        TuiAddress.setText(T_Add);
        TuiPhone.setText(T_Phone);
        TuiEmail.setText(T_Email);
        TuiAdmin1.setText(T_Admin1);
        TuiAdmin2.setText(T_Admin2);



        Bitmap img=db.getTuiProImage();
        if (img!=null) {
            imgPro.setImageBitmap(img);
        }
        //imgPro.setImageResource(R.drawable.logo1);

        final ArrayList<TuitionEdit> finalTuitionDetails = tuitionDetails;
        EditButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(TuitionProfile.this,EditTuitionInfo.class);
                intent.putExtra("TuitionInfo", finalTuitionDetails.get(0));
                startActivity(intent);
                finish();

            }
        });


    }
}