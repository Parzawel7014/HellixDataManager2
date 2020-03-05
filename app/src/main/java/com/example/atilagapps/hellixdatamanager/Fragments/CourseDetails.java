package com.example.atilagapps.hellixdatamanager.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.atilagapps.hellixdatamanager.R;


public class CourseDetails extends Fragment implements TimePickerDialog.OnTimeSetListener {

    Button timeButton;
    TextView FromTxtView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_course_details,container,false);

        timeButton=v.findViewById(R.id.FromTimeId);
        FromTxtView=v.findViewById(R.id.FromTextViewId);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker=new TimePickerFragment();
                timepicker.show(getFragmentManager(),"time picker");
            }
        });

        return v;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        FromTxtView.setText(hourOfDay+minute);
    }
}