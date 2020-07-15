package com.example.atilagapps.hellixdatamanager.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.atilagapps.hellixdatamanager.Batches.BatchesActivity;
import com.example.atilagapps.hellixdatamanager.Batches.NewBatchAddActivity;
import com.example.atilagapps.hellixdatamanager.Batches.SubjectAdapter;
import com.example.atilagapps.hellixdatamanager.Charts.AddIncomeExenseActivity;
import com.example.atilagapps.hellixdatamanager.Charts.ChartActivity;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.SendSMS.SendSMSActivity;
import com.example.atilagapps.hellixdatamanager.StaffManager.All_Staff;
import com.example.atilagapps.hellixdatamanager.Students.StudentAddActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.TuitionFeesActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private CardView StudentCardView, fees,batch,sms,staff,gant,backUp;


    CardView Q_AddStuCard,Q_AddFeeCard,Q_NewBatchCard,Q_ExtraExpCard;



    @SuppressLint("ResourceAsColor")
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

        Q_AddStuCard=v.findViewById(R.id.quickAccessAddStudentId);
        Q_AddFeeCard=v.findViewById(R.id.quickAccessAddFeesId);
        Q_NewBatchCard=v.findViewById(R.id.quickAccessNewBatchId);
        Q_ExtraExpCard=v.findViewById(R.id.quickAccessExtraExpenseId);

        DataBaseHelper db=new DataBaseHelper(v.getContext());

        ArrayList<SubjectAdapter> subjectAdapters=new ArrayList<>();
        subjectAdapters= db.getDialogueLabelsAdapter();

        final ArrayList<SubjectAdapter> finalSubjectAdapters = subjectAdapters;



        Q_AddStuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (finalSubjectAdapters.isEmpty()){
                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(v.getContext());

                    //  AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(v.getContext());
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setMessage("First Add Batches. \nClick on Ok to add Batches");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(v.getContext(),NewBatchAddActivity.class));

                            // dialog.dismiss();
                        }
                    });
                    reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                    reconfirmBuilder.show();
                }
                else {

                    startActivity(new Intent(v.getContext(), StudentAddActivity.class));
                     }



                    //startActivity(new Intent(v.getContext(), StudentAddActivity.class));
            }
        });

        Q_AddFeeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddFeesActivity.class));
            }
        });

        Q_NewBatchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), NewBatchAddActivity.class));
            }
        });

        Q_ExtraExpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddIncomeExenseActivity.class));
            }
        });






     /*   AnyChartView anyChartView = v.findViewById(R.id.any_chart_view);
     //   anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        ArrayList<RemainingStuBatchClass> BatchesName=new ArrayList<>();

        BatchesName=db.getBatchesChartInfo();

        if (BatchesName.isEmpty()){
       //     anyChartView.setVisibility(View.GONE);
        }

        List<DataEntry> data = new ArrayList<>();

        for (int i=0;i<BatchesName.size();i++){
        String bName=BatchesName.get(i).getBatchName();
        String bTime=BatchesName.get(i).getBatchTime();
        String FinalB=bName+"("+bTime+")";

         data.add(new ValueDataEntry(FinalB,BatchesName.get(i).getCount()));
        }



        Column column = cartesian.column(data);
        anyChartView.setBackgroundColor(R.color.colorPrimary);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Pending Fees (Students Count) ");
        //cartesian.title("Pull to Refresh");

        cartesian.yScale().minimum(0);
        cartesian.yScale().maximum(100);


        cartesian.yAxis(0).labels().format("{%value}");

//        cartesian.yAxis(0).labels().format(function());

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Batches");
        cartesian.yAxis(0).title("Count");
       cartesian.xAxis(0).labels().width(40).wordWrap("break-word").wordBreak("break-all");

       // anyChartView.setBackgroundColor("#f3e5f5");

        anyChartView.setChart(cartesian);
*/




        StudentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


           /*     if (finalSubjectAdapters.isEmpty()){
                    MaterialAlertDialogBuilder reconfirmBuilder=new MaterialAlertDialogBuilder(v.getContext());

                    //  AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(v.getContext());
                    reconfirmBuilder.setTitle("Alert");
                    reconfirmBuilder.setMessage("First Add Batches. \nClick on Ok to add Batches");
                    reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(v.getContext(),NewBatchAddActivity.class));

                            // dialog.dismiss();
                        }
                    });
                    reconfirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // AlertDialog cnfDialogue = reconfirmBuilder.create();
                    reconfirmBuilder.show();
                }
                else {*/

                    startActivity(new Intent(v.getContext(), StudentActivity.class));
               // }

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