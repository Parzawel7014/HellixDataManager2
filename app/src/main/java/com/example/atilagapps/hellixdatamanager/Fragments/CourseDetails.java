package com.example.atilagapps.hellixdatamanager.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.SharedViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CourseDetails extends Fragment implements AdapterView.OnItemSelectedListener {

    Button timeButton,subjectButton,confirmButt;
    TextView FromTxtView,amountTextV;
    int amount=0;

    String[] listItems;
    boolean[] checkedItem;
    ArrayList<Integer> mUserItems=new ArrayList<>();

    String[] finalArray;

    SharedViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_course_details, container, false);

        timeButton = v.findViewById(R.id.FromTimeId);
        FromTxtView = v.findViewById(R.id.FromTextViewId);

        subjectButton=v.findViewById(R.id.SubjectButt);
        amountTextV=v.findViewById(R.id.amountText);

        confirmButt=v.findViewById(R.id.ConfirmButton);

        Calendar c = Calendar.getInstance();
        final int hr = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);





        DataBaseHelper db=new DataBaseHelper(getContext());
        listItems= db.getDialogueLabels().toArray(new String[0]);

        checkedItem=new boolean[listItems.length];

        

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
                            if (hourOfDay==12){
                                hour_of_12_hour_format = 12;
                            }else {

                            hour_of_12_hour_format = hourOfDay - 12;}
                            status = "PM";
                        } else {
                            hour_of_12_hour_format = hourOfDay;
                        }

                       FromTxtView.setText(hour_of_12_hour_format + ":"+String.format("%02d",minute) + " " + status );}

                }, hr, min, DateFormat.is24HourFormat(v.getContext()));
                timePickerDialog.show();

            }
        });


        subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialogue();
            }
        });



        confirmButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence batch_timing=FromTxtView.getText();



                viewModel.setBatch(batch_timing);


                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.container2, new ConfirmationFragment()).addToBackStack(null).commit();



            }
        });



        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

    }

    private void OpenDialogue() {

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Subjects");
        mBuilder.setMultiChoiceItems(listItems, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!mUserItems.contains(position)){
                        mUserItems.add(position);
                    }
                    }
                else if(mUserItems.contains(position)){
                    mUserItems.remove(position-1);
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";

                for (int i=0;i<mUserItems.size();i++){
                    item=item+" "+listItems[mUserItems.get(i)];
                    finalArray[i]=listItems[mUserItems.get(i)];

                    amount+=500;
                }
                Toast.makeText(getContext(), "Val "+item, Toast.LENGTH_SHORT).show();
               amountTextV.setText(Integer.toString(amount));

            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<checkedItem.length;i++){
                    checkedItem[i]=false;
                    mUserItems.clear();
                }
            }
        });

        AlertDialog mDialogue=mBuilder.create();
        mDialogue.show();



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();


        // Showing selected spinner item
        if (position != 0) {
            Toast.makeText(parent.getContext(), "You selected: " + label,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}