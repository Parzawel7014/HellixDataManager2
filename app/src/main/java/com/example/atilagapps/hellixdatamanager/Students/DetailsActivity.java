package com.example.atilagapps.hellixdatamanager.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ImageDialogueClass.ImageDialogueListener {


    private RegBatchesAdapter mAdapter;
    private Spinner spinnerGender, spinnerCast;
    byte[] thumb;
    private Uri imageUri;
    private Bitmap compressor;
     String id;
    String castUpdated, genderUpdated;
    TextView nameText, idText, phoneTxt, emailTxt, genderTxt, addressTxt, eduTxt, castTxt;
    CircularImageView proPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        FindStudent findStudent = intent.getParcelableExtra("StudentName");

        assert findStudent != null;
        String name = findStudent.getmStudentName();
       id = findStudent.getmStudentID();
        nameText = findViewById(R.id.DetailNameTextID);
        idText = findViewById(R.id.IDTextViewId);
        phoneTxt = findViewById(R.id.PhoneId);
        emailTxt = findViewById(R.id.EmailId);
        genderTxt = findViewById(R.id.GenderId);
        addressTxt = findViewById(R.id.AddressId);
        eduTxt = findViewById(R.id.EducationId);
        castTxt = findViewById(R.id.CastId);
        proPic=findViewById(R.id.ProfileImageID);




        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putByteArray("IMG",thumb);
                bundle.putString("ProStuId",id);
                ImageDialogueClass imageDialogueClass=new ImageDialogueClass();
                imageDialogueClass.setArguments(bundle);
                imageDialogueClass.show(getSupportFragmentManager(), null);
            }
        });





        Toolbar toolbar = findViewById(R.id.DetailsToolbar);
        final DataBaseHelper db = new DataBaseHelper(this);

        thumb=db.getStuThumb(id);

        Bitmap img=db.getProImage(id);
        if (img!=null) {
            proPic.setImageBitmap(img);
        }
        ArrayList<RegSubClass> regSubClasses = new ArrayList<>();
        regSubClasses = db.getAllRegisteredBathes(id);

        String regId = db.getRegId(id);
        nameText.setText(name);
        idText.setText(regId);

        ArrayList<StudentInfo> data = new ArrayList<>();
        data = db.getStudentDetails(id);

        String phone = data.get(0).getmStudentContact();
        String email = data.get(0).getmStudentEmail();
        final String gender = data.get(0).getmStudentGender();
        String address = data.get(0).getmStudentAddress();
        String Education = data.get(0).getmStudentEducation();
        String cast = data.get(0).getmStudentCast();

        phoneTxt.setText(phone);
        if (email == null) {
            emailTxt.setText("-");
        } else {
            emailTxt.setText(email);
        }
        genderTxt.setText(gender);
        addressTxt.setText(address);
        if (Education == null) {
            eduTxt.setText("-");
        } else {
            eduTxt.setText(Education);
        }
        if (cast == null) {
            castTxt.setText("-");
        } else {
            castTxt.setText(cast);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RelativeLayout relativeLayout1=findViewById(R.id.rellay1);


        final EditText NameEdit=findViewById(R.id.NameEditTextEditId);
        ImageView nameDone=findViewById(R.id.NameDoneImageId);
        final LinearLayout nameLinearLayout=findViewById(R.id.NameEditButtonId);
        final LinearLayout hiddenNameLinearLayout=findViewById(R.id.HiddenNameLinearLayoutId);
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
                db.updateName(id,Sname);
            }
        });



        final EditText phoneEdit=findViewById(R.id.PhoneEditTextEditId);
        ImageView phoneDone=findViewById(R.id.PhoneDoneImageId);
        LinearLayout phoneLinearLayout=findViewById(R.id.PhoneEditButtonId);
        final LinearLayout hiddenPhoneLinearLayout=findViewById(R.id.HiddenPhoneLinearLayoutId);
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
                 if (phoneTxt.length()<10){
                     Toast.makeText(DetailsActivity.this, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();

                }else {
                     String phoneNum = phoneEdit.getText().toString().trim();
                     phoneTxt.setText(phoneNum);
                     hiddenPhoneLinearLayout.setVisibility(View.GONE);
                     phoneTxt.setVisibility(View.VISIBLE);
                     relativeLayout1.setVisibility(View.VISIBLE);
                     db.updatePhone(id, phoneNum);
                 }
            }
        });


        final EditText emailEdit=findViewById(R.id.EmailEditTextEditId);
        ImageView emailDone=findViewById(R.id.EmailDoneImageId);
        LinearLayout emailLinearLayout=findViewById(R.id.EmailEditButtonId);
        final LinearLayout hiddenEmailLinearLayout=findViewById(R.id.HiddenEmailLinearLayoutId);
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
                db.updateEmail(id,email);
            }
        });



        final EditText AddressEdit=findViewById(R.id.AddressEditTextEditId);
        ImageView AddressDone=findViewById(R.id.AddressDoneImageId);
        LinearLayout AddressLinearLayout=findViewById(R.id.AddressEditButtonId);
        final LinearLayout hiddenAddressLinearLayout=findViewById(R.id.HiddenAddressLinearLayoutId);
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
                db.updateAddress(id,address);
            }
        });




        final EditText EducationEdit=findViewById(R.id.EducationEditTextEditId);
        ImageView EducationDone=findViewById(R.id.EducationDoneImageId);
        LinearLayout EducationLinearLayout=findViewById(R.id.EducationEditButtonId);
        final LinearLayout hiddenEducationLinearLayout=findViewById(R.id.HiddenEducationLinearLayoutId);
        EducationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String education=eduTxt.getText().toString().trim();
                eduTxt.setVisibility(View.GONE);
                EducationEdit.setText(education);
                hiddenEducationLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });
        EducationDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String education=EducationEdit.getText().toString().trim();
                eduTxt.setText(education);
                hiddenEducationLinearLayout.setVisibility(View.GONE);
                eduTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
                db.updateEducation(id,education);
            }
        });







        LinearLayout genderLinearLayout = findViewById(R.id.genderEditButtonId);
        final LinearLayout hiddenGenderLinearLayout = findViewById(R.id.HiddenGenderLinearLayoutId);
        spinnerGender = findViewById(R.id.GenderSpinnerId);
        genderLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderTxt.setVisibility(View.GONE);
                hiddenGenderLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(DetailsActivity.this,
                R.array.Gender, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter1);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idV) {
                genderUpdated = parent.getItemAtPosition(position).toString();
                genderTxt.setText(genderUpdated);
                hiddenGenderLinearLayout.setVisibility(View.GONE);
                genderTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
                db.updateGender(id,genderUpdated);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        LinearLayout castLinearLayout=findViewById(R.id.CastEditButtonId);
        final LinearLayout hiddenCastLinearLayout=findViewById(R.id.HiddenCastLinearLayoutId);
        castLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                castTxt.setVisibility(View.GONE);
                hiddenCastLinearLayout.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.GONE);
            }
        });
        spinnerCast = findViewById(R.id.CastSpinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DetailsActivity.this,
                R.array.Caste, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCast.setAdapter(adapter);
        spinnerCast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idV) {
                castUpdated = parent.getItemAtPosition(position).toString();
                castTxt.setText(castUpdated);
                hiddenCastLinearLayout.setVisibility(View.GONE);
                castTxt.setVisibility(View.VISIBLE);
                relativeLayout1.setVisibility(View.VISIBLE);
                db.updateCast(id,castUpdated);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        RecyclerView mRecyclerView = findViewById(R.id.RegisteredSubRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RegBatchesAdapter(regSubClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getImage(byte[] img) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        proPic.setImageBitmap(bitmap);
        DataBaseHelper db=new DataBaseHelper(this);
       boolean res= db.updateStuProPic(id,img);
       if (res){
           Toast.makeText(this, "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
       }


    }

    @Override
    public void deleteImage() {
        proPic.setImageResource(R.drawable.user);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.update_item_details_act) {
          //  startActivity(new Intent(getApplicationContext(), UpdateStudentDetailsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}