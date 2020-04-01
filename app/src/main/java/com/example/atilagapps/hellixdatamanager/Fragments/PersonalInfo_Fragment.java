package com.example.atilagapps.hellixdatamanager.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.SharedViewModel;

public class PersonalInfo_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public EditText editTextName, editTextAddress, editTextPhone;
    private Spinner spinner;
    DataBaseHelper dbRef;
    RadioButton radioButton;
    String Cast_text, Gender_text;
    CharSequence name,address,Phone;
    SharedViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_info_, container, false);





        Button NextBtn = v.findViewById(R.id.btn1Id);
        spinner = v.findViewById(R.id.spinnerId);
        dbRef = new DataBaseHelper(getContext());
        editTextName = v.findViewById(R.id.editTextNameId);
        editTextAddress = v.findViewById(R.id.editTextAddressId);
        editTextPhone = v.findViewById(R.id.editTextPhoneId);
        RadioGroup radioGroup = v.findViewById(R.id.radioGroupId);
        int selectedBtn = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(selectedBtn);
        Gender_text = radioButton.getText().toString();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.Caste, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editTextName.getText();
                address = editTextAddress.getText();
                Phone = editTextPhone.getText();

                viewModel.setName(name);
                viewModel.setAddress(address);
                viewModel.setPhone(Phone);
                viewModel.setCastText(Cast_text);
                viewModel.setGenderText(Gender_text);

                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.container2, new CourseDetails()).addToBackStack(null).commit();

            }
        });

        return v;
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