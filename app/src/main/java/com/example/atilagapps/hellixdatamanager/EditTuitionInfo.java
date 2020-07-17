package com.example.atilagapps.hellixdatamanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.Students.ImageDialogueClass;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EditTuitionInfo extends AppCompatActivity implements TuitionImageDialogueClass.ImageDialogueListener {

    CircularImageView imgPro;
    TextInputEditText TuiName,TuiAddress,TuiPhone,TuiEmail,TuiAdmin1,TuiAdmin2;
    MaterialButton EditCnfButt;

    byte[] thumb,thumb1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tuition_info);

        Intent intent=getIntent();
        TuitionEdit tuitionEdit=intent.getParcelableExtra("TuitionInfo");

        final DataBaseHelper db=new DataBaseHelper(this);


        Toolbar toolbar=findViewById(R.id.EditTuiInfoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Details");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imgPro=findViewById(R.id.TuiprofileEditImageID);
        TuiName=findViewById(R.id.text_Tui_input_name_id);
        TuiPhone=findViewById(R.id.text_Tui_input_phone_id);
        TuiAddress=findViewById(R.id.text_input_Tui__Address_id);
        TuiEmail=findViewById(R.id.text_Tui_input_email_id);
        TuiAdmin1=findViewById(R.id.text_Tui_input_Admin1_id);
        TuiAdmin2=findViewById(R.id.text_Tui_input_Admin2_id);
        EditCnfButt=findViewById(R.id.Tui_btn1Id);


        imgPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putByteArray("IMG1",thumb1);
                //ImageDialogueClass imageDialogueClass=new ImageDialogueClass();
                TuitionImageDialogueClass tuitionImageDialogueClass=new TuitionImageDialogueClass();
                tuitionImageDialogueClass.setArguments(bundle);
                tuitionImageDialogueClass.show(getSupportFragmentManager(), null);
            }
        });




        EditCnfButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validatePhone()| !validateAddress() | !validateAdmin1() | !validateAdmin2()| !validateEmail()) {
                    return;
                } else {

                    Bitmap img=((BitmapDrawable)imgPro.getDrawable()).getBitmap();
                    //Bitmap btmp = Bitmap.create(imgPro.getBitmap());
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    thumb=byteArrayOutputStream.toByteArray();



                    String T_Name=TuiName.getText().toString().trim();
                    String T_Add=TuiAddress.getText().toString().trim();
                    String T_Phone=TuiPhone.getText().toString().trim();
                    String T_Email=TuiEmail.getText().toString().trim();
                    String T_Admin1=TuiAdmin1.getText().toString().trim();
                    String T_Admin2=TuiAdmin2.getText().toString().trim();


                    db.updateTuiInfo(T_Name,T_Add,T_Phone,T_Email,T_Admin1,T_Admin2,thumb);

                    startActivity(new Intent(EditTuitionInfo.this,TuitionProfile.class));
                    Toast.makeText(EditTuitionInfo.this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();


                }

            }
        });


        thumb1=db.getTuiThumb();
        if (thumb1==null){
            imgPro.setImageResource(R.drawable.camera);
        }

        assert tuitionEdit != null;
        String T_Name=tuitionEdit.getTuiName();
        String T_Add=tuitionEdit.getTuiAdd();
        String T_Phone=tuitionEdit.getTuiPhone();
        String T_Email=tuitionEdit.getTuiEmail();
        String T_Admin1=tuitionEdit.getTuiAdmin1();
        String T_Admin2=tuitionEdit.getTuiAdmin2();


        TuiName.setText(T_Name);
        TuiAddress.setText(T_Add);
        TuiPhone.setText(T_Phone);
        TuiEmail.setText(T_Email);
        TuiAdmin1.setText(T_Admin1);
        TuiAdmin2.setText(T_Admin2);




        Bitmap img=tuitionEdit.getImg();
        if (img!=null) {
            imgPro.setImageBitmap(img);
        }
     // imgPro.setImageResource(R.drawable.logo1);


    }

    private boolean validateName(){
        String nameInput=TuiName.getText().toString().trim();
        if (nameInput.isEmpty()){
            TuiName.setError("Field can't be empty");
            return false;
        }else {
            TuiName.setError(null);
            return true;
        }
    }


    private boolean validatePhone(){
        String phoneInput=TuiPhone.getText().toString().trim();
        if (phoneInput.isEmpty()){
            TuiPhone.setError("Field can't be empty");
            return false;
        }else if (phoneInput.length()<10){
            TuiPhone.setError("Phone number not valid!");
            return false;
        } else {
            TuiPhone.setError(null);

            return true;
        }
    }


    private boolean validateEmail(){
        String emailInput=TuiEmail.getText().toString().trim();
        if (emailInput.isEmpty()){
            TuiEmail.setError("Field can't be empty");
            return false;
        }else {
            TuiEmail.setError(null);
            //email.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateAddress(){
        String addressInput=TuiAddress.getText().toString().trim();
        if (addressInput.isEmpty()){
            TuiAddress.setError("Field can't be empty");
            return false;
        }else {
            TuiAddress.setError(null);
           // address.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAdmin1(){
        String addressInput=TuiAdmin1.getText().toString().trim();
        if (addressInput.isEmpty()){
            TuiAdmin1.setError("Field can't be empty");
            return false;
        }else {
            TuiAdmin1.setError(null);
           // subject.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAdmin2(){
        String addressInput=TuiAdmin2.getText().toString().trim();
        if (addressInput.isEmpty()){
            TuiAdmin2.setError("Field can't be empty");
            return false;
        }else {
            TuiAdmin2.setError(null);
          //  salary.setErrorEnabled(false);
            return true;
        }
    }


    @Override
    public void getImage(byte[] img) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgPro.setImageBitmap(bitmap);
        DataBaseHelper db=new DataBaseHelper(this);
        db.updatetuiProPic(img);

    }

    @Override
    public void deleteImage() {
        imgPro.setImageResource(R.drawable.camera);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditTuitionInfo.this,TuitionProfile.class));
        finish();
    }
}