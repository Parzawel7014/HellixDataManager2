package com.example.atilagapps.hellixdatamanager.StaffManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudent;
import com.example.atilagapps.hellixdatamanager.Students.RegBatchesAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class StaffDetailsActivity extends AppCompatActivity implements StaffImageDialogueClass.StaffImageDialogueListener {


    TextView nameText,  phoneTxt, emailTxt, salaryTxt,addressTxt;
    CircularImageView proPic;
    StaffRegSubRecyclerAdapter mAdapter;
    byte[] thumb;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);

        Intent intent = getIntent();
        Staff_Class staff_class = intent.getParcelableExtra("StaffName");

        assert staff_class != null;
        String name = staff_class.getStaff_name();
         id = staff_class.getId();
        nameText = findViewById(R.id.StaffDetailNameTextID);
        phoneTxt = findViewById(R.id.StaffPhoneId);
        emailTxt = findViewById(R.id.StaffEmailId);
        salaryTxt = findViewById(R.id.SalaryId);
        addressTxt = findViewById(R.id.StaffAddressId);
        proPic=findViewById(R.id.StaffProfileImageID);

        nameText.setText(name);


        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putByteArray("Staff_IMG",thumb);
                bundle.putString("Staff_Id_img",id);
                StaffImageDialogueClass staffImageDialogueClass=new StaffImageDialogueClass();
                staffImageDialogueClass.setArguments(bundle);
                staffImageDialogueClass.show(getSupportFragmentManager(), null);

            }
        });


       final DataBaseHelper db=new DataBaseHelper(this);

        ArrayList<StaffRegisteredSubjectClass> staffRegisteredSubjectClasses=new ArrayList<>();
        staffRegisteredSubjectClasses=db.getAllRegisterdBatchesStaff(id);


        Toolbar toolbar = findViewById(R.id.StaffDetailsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Staff Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bitmap img=db.getStaffProImage(id);
        thumb=db.getStaffThumb(id);

        if (img!=null) {
            proPic.setImageBitmap(img);
        }


        ArrayList<StaffInfo> data=new ArrayList<>();
        data=db.getStaffDetails(id);

        String phone = data.get(0).getStaffContact();
        String email = data.get(0).getStaffEmail();
        String salary = data.get(0).getStaffSalary();
        String address = data.get(0).getStaffAddress();


        if (email == null) {
            emailTxt.setText("-");
        } else {
            emailTxt.setText(email);
        }

        phoneTxt.setText(phone);
        salaryTxt.setText(salary);
        addressTxt.setText(address);


        final RelativeLayout relativeLayout1=findViewById(R.id.rellay1);

        final EditText NameEdit=findViewById(R.id.StaffNameEditTextEditId);
        ImageView nameDone=findViewById(R.id.StaffNameDoneImageId);
        final LinearLayout nameLinearLayout=findViewById(R.id.StaffNameEditButtonId);
        final LinearLayout hiddenNameLinearLayout=findViewById(R.id.StaffHiddenNameLinearLayoutId);
        nameLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString().trim();
                NameEdit.setText(name);
                nameText.setVisibility(View.GONE);
                hiddenNameLinearLayout.setVisibility(View.VISIBLE);
                nameLinearLayout.setVisibility(View.GONE);
              //  relativeLayout1.setVisibility(View.GONE);
            }
        });
        nameDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sname=NameEdit.getText().toString().trim();
                nameText.setText(Sname);
                hiddenNameLinearLayout.setVisibility(View.GONE);
                nameText.setVisibility(View.VISIBLE);
                nameLinearLayout.setVisibility(View.VISIBLE);
               // relativeLayout1.setVisibility(View.VISIBLE);
                db.updateStaffName(id,Sname);
            }
        });








        final EditText phoneEdit=findViewById(R.id.StaffPhoneEditTextEditId);
        ImageView phoneDone=findViewById(R.id.StaffPhoneDoneImageId);
        LinearLayout phoneLinearLayout=findViewById(R.id.StaffPhoneEditButtonId);
        final LinearLayout hiddenPhoneLinearLayout=findViewById(R.id.StaffHiddenPhoneLinearLayoutId);
        phoneLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=phoneTxt.getText().toString().trim();
                phoneEdit.setText(phone);
                phoneTxt.setVisibility(View.GONE);
                hiddenPhoneLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });
        phoneDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum=phoneEdit.getText().toString().trim();
                phoneTxt.setText(phoneNum);
                hiddenPhoneLinearLayout.setVisibility(View.GONE);
                phoneTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
               db.updateStaffPhone(id,phoneNum);
            }
        });


        final EditText emailEdit=findViewById(R.id.StaffEmailEditTextEditId);
        ImageView emailDone=findViewById(R.id.StaffEmailDoneImageId);
        LinearLayout emailLinearLayout=findViewById(R.id.StaffEmailEditButtonId);
        final LinearLayout hiddenEmailLinearLayout=findViewById(R.id.StaffHiddenEmailLinearLayoutId);
        emailLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailTxt.setVisibility(View.GONE);
                String email=emailTxt.getText().toString().trim();
                emailEdit.setText(email);
                hiddenEmailLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });

        emailDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEdit.getText().toString().trim();
                emailTxt.setText(email);
                hiddenEmailLinearLayout.setVisibility(View.GONE);
                emailTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
                db.updateStaffEmail(id,email);
            }
        });


        final EditText AddressEdit=findViewById(R.id.StaffAddressEditTextEditId);
        ImageView AddressDone=findViewById(R.id.StaffAddressDoneImageId);
        LinearLayout AddressLinearLayout=findViewById(R.id.StaffAddressEditButtonId);
        final LinearLayout hiddenAddressLinearLayout=findViewById(R.id.StaffHiddenAddressLinearLayoutId);
        AddressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=addressTxt.getText().toString().trim();
                AddressEdit.setText(address);
                addressTxt.setVisibility(View.GONE);
                hiddenAddressLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });

        AddressDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=AddressEdit.getText().toString().trim();
                addressTxt.setText(address);
                hiddenAddressLinearLayout.setVisibility(View.GONE);
                addressTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
              db.updateStaffAddress(id,address);
            }
        });


        final EditText SalaryEdit=findViewById(R.id.StaffSalaryEditTextEditId);
        ImageView SalaryDone=findViewById(R.id.StaffSalaryDoneImageId);
        LinearLayout SalaryLinearLayout=findViewById(R.id.StaffSalaryEditButtonId);
        final LinearLayout hiddenEducationLinearLayout=findViewById(R.id.HiddenEducationLinearLayoutId);
        SalaryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String education=salaryTxt.getText().toString().trim();
                salaryTxt.setVisibility(View.GONE);
                SalaryEdit.setText(education);
                hiddenEducationLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });
        SalaryDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salry=SalaryEdit.getText().toString().trim();
                salaryTxt.setText(salry);
                hiddenEducationLinearLayout.setVisibility(View.GONE);
                salaryTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
               db.updateStaffSalary(id,salry);
            }
        });






        RecyclerView mRecyclerView = findViewById(R.id.StaffRegisteredSubRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StaffRegSubRecyclerAdapter(staffRegisteredSubjectClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void getStaffImage(byte[] img) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        proPic.setImageBitmap(bitmap);
        DataBaseHelper db=new DataBaseHelper(this);
        boolean res= db.updateStaffProPic(id,img);
        if (res){
            Toast.makeText(this, "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void deleteImage() {
        proPic.setImageResource(R.drawable.user);
    }
}