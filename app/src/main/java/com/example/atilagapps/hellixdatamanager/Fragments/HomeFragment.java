package com.example.atilagapps.hellixdatamanager.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.Charts.ChartActivity;
import com.example.atilagapps.hellixdatamanager.Charts.IncomeExpenseActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.Reciept.CreatePDF;
import com.example.atilagapps.hellixdatamanager.SendSMS.SendSMSActivity;
import com.example.atilagapps.hellixdatamanager.StaffManager.All_Staff;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.TuitionFeesActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private CardView StudentCardView, fees,batch,sms,staff,gant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        StudentCardView = v.findViewById(R.id.StudentCardId);
        fees = v.findViewById(R.id.FeesCardViewId);
        batch=v.findViewById(R.id.BatchesCardViewId);
        sms=v.findViewById(R.id.StaffCardViewId);
        batch=v.findViewById(R.id.BatchesCardViewId);
        staff=v.findViewById(R.id.StaffManagerCardId);
        gant=v.findViewById(R.id.GantChartCardViewId);

        DataBaseHelper db=new DataBaseHelper(v.getContext());






        StudentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(v.getContext(), StudentActivity.class));

            }
        });

        batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), BatchesActivity.class));
            }
        });

        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), TuitionFeesActivity.class));
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SendSMSActivity.class));
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), All_Staff.class));
            }
        });


        gant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ChartActivity.class));
            }
        });




        return v;
    }
}