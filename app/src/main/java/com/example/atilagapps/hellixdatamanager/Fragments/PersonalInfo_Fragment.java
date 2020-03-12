package com.example.atilagapps.hellixdatamanager.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;

public class PersonalInfo_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextName, editTextAddress, editTextPhone;
    private Spinner spinner;

    DataBaseHelper dbRef;

    RadioButton radioButton;
    String Cast_text, Gender_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_personal_info_, container, false);

        Button btn = v.findViewById(R.id.btn1Id);


        //RadioButton radioBtnFemale = v.findViewById(R.id.FemaleRadioButtId);

        spinner = v.findViewById(R.id.spinnerId);
        dbRef = new DataBaseHelper(getContext());
        editTextName = v.findViewById(R.id.editTextNameId);
        editTextAddress = v.findViewById(R.id.editTextAddressId);
        editTextPhone = v.findViewById(R.id.editTextPhoneId);

        RadioGroup radioGroup = v.findViewById(R.id.radioGroupId);

        int selectedBtn = radioGroup.getCheckedRadioButtonId();

        radioButton = v.findViewById(selectedBtn);

        Gender_text = radioButton.getText().toString();





        /*radioBtnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add db code
            }
        });

        radioBtnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add db code
            }
        });*/


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.Caste, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertPersonalData();

                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.containerId, new CourseDetails()).addToBackStack(null).commit();
            }
        });


        return v;


    }

    private void insertPersonalData() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String Phone = editTextPhone.getText().toString().trim();


        boolean isInserted = dbRef.insert_PersonalData_Stu_Table(name, address, Phone, Gender_text, Cast_text);


        if (isInserted == true) {
            Toast.makeText(getContext(), "Date inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Data insertion Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Cast_text = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}