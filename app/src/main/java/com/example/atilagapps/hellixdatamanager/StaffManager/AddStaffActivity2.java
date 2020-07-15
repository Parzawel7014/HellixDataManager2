package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.atilagapps.hellixdatamanager.Batches.NewBatchAddActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class AddStaffActivity2 extends AppCompatActivity {

    TextInputLayout name,phone,address,email,salary,subject;
    Button submitButton;
    CircularImageView proImg;
    String[] listItems;

    byte[] thumb;
    private Uri imageUri;
    private Bitmap compressor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);


        name=findViewById(R.id.text_input_staff_name_id);
        phone=findViewById(R.id.text_input_staff_phone_id);
        address=findViewById(R.id.text_input_staff_Address_id);
        salary=findViewById(R.id.text_input_staff_salary_id);
        email=findViewById(R.id.text_input_staff_email_id);
        subject=findViewById(R.id.text_input_staff_subject_id);
        submitButton=findViewById(R.id.staffbtn1Id);
        proImg=findViewById(R.id.staffProfileImageID);
        final DataBaseHelper db=new DataBaseHelper(this);

        listItems = db.getDialogueLabels().toArray(new String[0]);
        TextInputEditText edtTxt=findViewById(R.id.subjectChooseId);

        Bundle bundle=getIntent().getExtras();
        assert bundle != null;
        String subName=bundle.getString("BatchName");

        edtTxt.setText(subName);
        edtTxt.setFocusable(false);
        edtTxt.setClickable(false);



        Toolbar toolbar=findViewById(R.id.staffAddActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Staff");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        proImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(AddStaffActivity2.this,new String[]
                                    {Manifest.permission.READ_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                            1);
                }
                else {
                    chooseImage();
                }
            }
        });

      /*  edtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddStaffActivity2.this);
                mBuilder.setTitle("Subjects");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subject.getEditText().setText(listItems[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialogue = mBuilder.create();
                mDialogue.show();
            }
        });
*/
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validateName() | !validatePhone() | !validateAddress() | !validateSubject() | !validateSalary()) {
                    return;
                } else {

                    String name_T=name.getEditText().getText().toString().trim();
                    String phone_T=phone.getEditText().getText().toString().trim();
                    String address_T=address.getEditText().getText().toString().trim();
                    String subject_T=subject.getEditText().getText().toString().trim();
                    String salary_T=salary.getEditText().getText().toString().trim();
                    String email_T=email.getEditText().getText().toString().trim();

                    boolean res=db.insertIntoTeacherTable(name_T,address_T,phone_T,subject_T,salary_T,email_T,thumb);
                    if (res){
                        startActivity(new Intent(AddStaffActivity2.this, NewBatchAddActivity.class));
                        finish();
                        Toast.makeText(AddStaffActivity2.this, "Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddStaffActivity2.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }

    private void chooseImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(AddStaffActivity2.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                assert result != null;
                imageUri=result.getUri();
                proImg.setImageURI(imageUri);
                getfile(imageUri);
            }else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
            }
        }
    }

    private void getfile(Uri imageUri) {

        File newFile=new File(imageUri.getPath());
        try{
            compressor =new Compressor(AddStaffActivity2.this)
                    .setMaxWidth(100)
                    .setMaxHeight(100)
                    .setQuality(50)
                    .compressToBitmap(newFile);
        }catch (IOException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        thumb=byteArrayOutputStream.toByteArray();

    }

    private boolean validateName(){
        String nameInput=name.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()){
            name.setError("Field can't be empty");
            return false;
        }else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePhone(){
        String phoneInput=phone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()){
            phone.setError("Field can't be empty");
            return false;
        }else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail(){
        String emailInput=email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateAddress(){
        String addressInput=address.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()){
            address.setError("Field can't be empty");
            return false;
        }else {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSubject(){
        String addressInput=subject.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()){
            subject.setError("Field can't be empty");
            return false;
        }else {
            subject.setError(null);
            subject.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSalary(){
        String addressInput=salary.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()){
            salary.setError("Field can't be empty");
            return false;
        }else {
            salary.setError(null);
            salary.setErrorEnabled(false);
            return true;
        }
    }


}