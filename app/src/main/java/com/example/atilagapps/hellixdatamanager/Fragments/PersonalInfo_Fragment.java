package com.example.atilagapps.hellixdatamanager.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.SharedViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import id.zelory.compressor.Compressor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class PersonalInfo_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {


    public static final int PICK_IMAGE = 1;
    public TextInputLayout editTextName, editTextAddress, editTextPhone,editTextEmail,editTextEducation;
    private Spinner spinner;
    DataBaseHelper dbRef;
    RadioButton radioButton;
    String Cast_text, Gender_text;
    CharSequence name,address,Phone,email,edu;
    SharedViewModel viewModel;
    TextView text;
    CircularImageView profileImage;
    byte[] thumb;
   private Uri imageUri;
   private Bitmap compressor;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_info_, container, false);


        Button NextBtn = v.findViewById(R.id.btn1Id);
        spinner = v.findViewById(R.id.spinnerId);
        dbRef = new DataBaseHelper(getContext());
        editTextName = v.findViewById(R.id.text_input_name_id);
        editTextAddress = v.findViewById(R.id.text_input_Address_id);
        editTextPhone = v.findViewById(R.id.text_input_phone_id);
        editTextEmail=v.findViewById(R.id.text_input_email_id);
        editTextEducation=v.findViewById(R.id.text_input_Education_id);
        text=v.findViewById(R.id.castTextHidden);
        profileImage=v.findViewById(R.id.profileImageID);

        RadioGroup radioGroup = v.findViewById(R.id.radioGroupId);
        int selectedBtn = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(selectedBtn);
        Gender_text = radioButton.getText().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.Caste, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

                   ActivityCompat.requestPermissions(requireActivity(),new String[]
                           {Manifest.permission.READ_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                           1);
               }
               else {
                   chooseImage();
               }


            }
        });





        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validatePhone()| !validateAddress() | !validateEdu() | !validateCast()) {
                    return;
                } else {

                    name = Objects.requireNonNull(editTextName.getEditText()).getText().toString().trim();
                    address = Objects.requireNonNull(editTextAddress.getEditText()).getText().toString().trim();
                    Phone = Objects.requireNonNull(editTextPhone.getEditText()).getText().toString().trim();
                    email = Objects.requireNonNull(editTextEmail.getEditText()).getText().toString().trim();
                    edu = Objects.requireNonNull(editTextEducation.getEditText()).getText().toString().trim();

                    viewModel.setName(name);
                    viewModel.setAddress(address);
                    viewModel.setPhone(Phone);
                    viewModel.setCastText(Cast_text);
                    viewModel.setGenderText(Gender_text);
                    viewModel.setEduTxt(edu);
                    viewModel.setEmailTxt(email);
                    viewModel.setProfilePic(thumb);

                    assert getFragmentManager() != null;
                    FragmentTransaction fm = getFragmentManager().beginTransaction();
                    fm.replace(R.id.container2, new CourseDetails()).addToBackStack(null).commit();

                }
            }
        });

        return v;
    }

    @SuppressLint("IntentReset")
    private void chooseImage() {

        Intent intent =CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .getIntent(requireActivity())
                ;
        startActivityForResult(intent,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                assert result != null;
                imageUri=result.getUri();
                profileImage.setImageURI(imageUri);
                getfile(imageUri);
            }else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                Exception error=result.getError();
            }
        }


    }

    private void getfile(Uri imageUri) {

        File newFile=new File(Objects.requireNonNull(imageUri.getPath()));
        try{
        compressor =new Compressor(getActivity())
                .setMaxWidth(100)
                .setMaxHeight(100)
                .setQuality(80)
                .compressToBitmap(newFile);
        }catch (IOException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        thumb=byteArrayOutputStream.toByteArray();

    }

    private boolean validateName(){
        String nameInput=editTextName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()){
            editTextName.setError("Field can't be empty");
            return false;
        }else {
            editTextName.setError(null);
            editTextEmail.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePhone(){
        String phoneInput=editTextPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()){
            editTextPhone.setError("Field can't be empty");
            return false;
        }else if (phoneInput.length()<10){
            editTextPhone.setError("Phone number not valid!");
            return false;
        } else {
            editTextPhone.setError(null);
             editTextEmail.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail(){
        String emailInput=editTextEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            editTextEmail.setError("Field can't be empty");
            return false;
        }else {
            editTextEmail.setError(null);
          editTextEmail.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateAddress(){
        String addressInput=editTextAddress.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()){
            editTextAddress.setError("Field can't be empty");
            return false;
        }else {
            editTextAddress.setError(null);
             //editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEdu(){
        String eduInput=editTextEducation.getEditText().getText().toString().trim();
        if (eduInput.isEmpty()){
            editTextEducation.setError("Field can't be empty");
            return false;
        }else {
            editTextEducation.setError(null);
            // editTextEmail.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateCast(){
        if (Cast_text.equals("Select Cast")){
            text.setVisibility(View.VISIBLE);
            return false;
        }else {
            text.setVisibility(View.GONE);
            return true;
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Cast_text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}