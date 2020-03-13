package com.example.atilagapps.hellixdatamanager.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.R;

public class ConfirmationFragment extends Fragment {



    TextView nameText,addressText,phoneText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_confirmation, container, false);

        nameText=v.findViewById(R.id.nameTextConfirmId);
        addressText=v.findViewById(R.id.addressTextConfirmId);
        phoneText=v.findViewById(R.id.PhoneTextConfirmId);

       Bundle bundle=getArguments();
        String name=bundle.getString("Name");
        String address=bundle.getString("Address");
        String phone=bundle.getString("Phone");

        nameText.setText(name);
        addressText.setText(address);
        phoneText.setText(phone);

        return v;
    }



}