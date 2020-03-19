package com.example.atilagapps.hellixdatamanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;

public class HomeFragment extends Fragment {

    private CardView StudentCardView, fees,batch,staff;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        StudentCardView = v.findViewById(R.id.StudentCardId);
        fees = v.findViewById(R.id.FeesCardViewId);
        batch=v.findViewById(R.id.BatchesCardViewId);
        staff=v.findViewById(R.id.StaffCardViewId);
        batch=v.findViewById(R.id.BatchesCardViewId);

        StudentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.containerId, new StudentFragment()).addToBackStack(null).commit();
            }
        });

        batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), BatchesActivity.class));
            }
        });



        return v;
    }
}