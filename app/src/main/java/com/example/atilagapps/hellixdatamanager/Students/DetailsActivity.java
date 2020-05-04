package com.example.atilagapps.hellixdatamanager.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {


    private RegBatchesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        FindStudent findStudent=intent.getParcelableExtra("StudentName");

        assert findStudent != null;
        String name=findStudent.getmStudentName();
        String id=findStudent.getmStudentID();
        TextView nameText=findViewById(R.id.DetailNameTextID);
        TextView idText=findViewById(R.id.IDTextViewId);
        TextView phoneTxt=findViewById(R.id.PhoneId);
        TextView emailTxt=findViewById(R.id.EmailId);
        TextView genderTxt=findViewById(R.id.GenderId);
        TextView addressTxt=findViewById(R.id.AddressId);
        TextView eduTxt=findViewById(R.id.EducationId);
        TextView castTxt=findViewById(R.id.CastId);
        Toolbar toolbar=findViewById(R.id.DetailsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseHelper db=new DataBaseHelper(this);

        ArrayList<RegSubClass> regSubClasses=new ArrayList<>();
        regSubClasses=db.getAllRegisteredBathes(id);

        String regId=db.getRegId(id);


        nameText.setText(name);
        idText.setText(regId);

        ArrayList<StudentInfo> data=new ArrayList<>();

        data=db.getStudentDetails(id);

        String phone=data.get(0).getmStudentContact();
        String email=data.get(0).getmStudentEmail();
        String gender=data.get(0).getmStudentGender();
        String address=data.get(0).getmStudentAddress();
        String Education=data.get(0).getmStudentEducation();
        String cast=data.get(0).getmStudentCast();

        phoneTxt.setText(phone);
        if (email==null){
            emailTxt.setText("-");
        }else{
        emailTxt.setText(email);}
        genderTxt.setText(gender);
        addressTxt.setText(address);
        if (Education==null){
            eduTxt.setText("-");
        }else {
        eduTxt.setText(Education);}
        if (cast==null){
            castTxt.setText("-");
        }else {
            castTxt.setText(cast);
        }



        RecyclerView mRecyclerView = findViewById(R.id.RegisteredSubRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter=new RegBatchesAdapter(regSubClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    }



}