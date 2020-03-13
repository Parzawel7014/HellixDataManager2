package com.example.atilagapps.hellixdatamanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Students.FindStudentActivity;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;


public class StudentFragment extends Fragment {

    CardView addStudentCard,FindStudentCard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_student, container, false);

        addStudentCard=v.findViewById(R.id.AddStudentCardId);
        FindStudentCard=v.findViewById(R.id.FindStudentCardId);


        addStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), StudentAddActivity.class));
            }
        });

        FindStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FindStudentActivity.class));
            }
        });





        return v;
    }
}