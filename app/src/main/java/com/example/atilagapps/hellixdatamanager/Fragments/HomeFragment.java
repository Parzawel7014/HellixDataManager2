package com.example.atilagapps.hellixdatamanager.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atilagapps.hellixdatamanager.R;

public class HomeFragment extends Fragment {

    private CardView addStudentCardView, cardView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        addStudentCardView = v.findViewById(R.id.AddStudentCardId);
        cardView2 = v.findViewById(R.id.cardView2Id);

        addStudentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.containerId, new PersonalInfo_Fragment()).addToBackStack(null).commit();
            }
        });


        return v;
    }
}