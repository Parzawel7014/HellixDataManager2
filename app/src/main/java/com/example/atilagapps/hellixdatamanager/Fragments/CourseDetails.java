package com.example.atilagapps.hellixdatamanager.Fragments;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.atilagapps.hellixdatamanager.R;

import java.util.Calendar;


public class CourseDetails extends Fragment {

    Button timeButton;
    TextView FromTxtView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_course_details, container, false);

        timeButton = v.findViewById(R.id.FromTimeId);
        FromTxtView = v.findViewById(R.id.FromTextViewId);

        Calendar c = Calendar.getInstance();
        final int hr = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hour_of_12_hour_format;
                        String status = "AM";
                        if (hourOfDay > 11) {

                            // If the hour is greater than or equal to 12
                            // Then we subtract 12 from the hour to make it 12 hour format time
                            hour_of_12_hour_format = hourOfDay - 12;
                            status = "PM";
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }
                        FromTxtView.setText(hour_of_12_hour_format + ":" + minute + " " + status);
                    }
                }, hr, min, DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();

            }
        });

        return v;
    }


}