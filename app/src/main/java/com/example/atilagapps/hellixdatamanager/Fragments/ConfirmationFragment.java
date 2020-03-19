package com.example.atilagapps.hellixdatamanager.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.SharedViewModel;

public class ConfirmationFragment extends Fragment {



    public TextView nameText,addressText,phoneText,batch_time,subjectText;
    SharedViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_confirmation, container, false);

        nameText=v.findViewById(R.id.nameTextConfirmId);
        addressText=v.findViewById(R.id.addressTextConfirmId);
        phoneText=v.findViewById(R.id.PhoneTextConfirmId);
        batch_time=v.findViewById(R.id.batchTimingId);
        subjectText=v.findViewById(R.id.subjectTextId);



 /*      Bundle bundle=getArguments();
        String name=bundle.getString("Name");
        String address=bundle.getString("Address");
        String phone=bundle.getString("Phone");
        String batch=bundle.getString("Batch");
        String[] sub=bundle.getStringArray("Subject");

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < sub.length; i++) {
            sb.append(sub[i]);
        }
        String str = sb.toString();

        nameText.setText(name);
        addressText.setText(address);
        phoneText.setText(phone);
        batch_time.setText(batch);
        subjectText.setText(str);
*/
        return v;
    }


    public void UpdateTextView(CharSequence name,CharSequence address,CharSequence phone){
        nameText.setText(name);
        addressText.setText(address);
        phoneText.setText(phone);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence name) {
                nameText.setText(name);
            }
        });
        viewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence address) {
                addressText.setText(address);
            }
        });
        viewModel.getPhone().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence phone) {
                phoneText.setText(phone);
            }
        });
        viewModel.getBatch().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence batch) {
                batch_time.setText(batch);
            }
        });

    }



}