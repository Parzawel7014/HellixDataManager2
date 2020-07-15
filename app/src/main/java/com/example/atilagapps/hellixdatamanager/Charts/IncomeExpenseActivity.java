package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.Fragments.CourseDetails;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncomeExpenseActivity extends AppCompatActivity implements UpdatedChartFragment.UpdateChartListener, ChartFragment.ChartInterface {


    EditText fromEditText, toEditText;
    MaterialButton applyButton;
    ChartSharedViewModel chartSharedViewModel;
    TextView incomeTxt,expenseTxt,profitTxt,profType;

    TextView tuiIncomeTxt,regIncomeTxt,extraIncomeTxt;
    TextView teachSal,extraExpense;

    String FromDayVal;
    String fromMonthVal;
    String fromYearVal;


    String ToDayVal;
    String ToMonthVal;
    String ToYearVal;

    double totalIncome,totalExpense,totalProfit;
    double tuitionIncome,RegIncome,extraIncome;
    double teachExpense,extraExp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        DataBaseHelper db = new DataBaseHelper(this);


        fromEditText = findViewById(R.id.fromDateId);
        toEditText = findViewById(R.id.toDateId);
        applyButton = findViewById(R.id.graphApplyButtId);

        incomeTxt=findViewById(R.id.incomeValId);
        expenseTxt=findViewById(R.id.expenseValId);
        profitTxt=findViewById(R.id.profitValId);
        profType=findViewById(R.id.profitOrLossId);

        tuiIncomeTxt=findViewById(R.id.TotalTuitionFeeValId);
        regIncomeTxt=findViewById(R.id.TotalRegFeeValId);
        extraIncomeTxt=findViewById(R.id.TotalExtraValId);

        teachSal=findViewById(R.id.TeachersExpenseValId);
        extraExpense=findViewById(R.id.ExtraExpenseValId);




        Toolbar toolbar=findViewById(R.id.chartToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profit/Loss");
        //getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int finalMonth = month + 1;
        final int day = c.get(Calendar.DAY_OF_MONTH);


        fromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fromEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


        toEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        toEditText.setText(dayOfMonth + "/" + finalMonth + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });



        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.graphFrameLayoutId, new ChartFragment());
        fragmentTransaction.commit();



        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //System.out.println(FromDayVal);
                assert getFragmentManager() != null;
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();


                String FromDateValue = fromEditText.getText().toString();
                String[] DateArray = FromDateValue.split("/", 3);
                FromDayVal = DateArray[0];
                fromMonthVal = DateArray[1];
                fromYearVal = DateArray[2];

                String ToDateValue = toEditText.getText().toString();
                String[] ToDateArray = ToDateValue.split("/", 3);
                ToDayVal = ToDateArray[0];
                ToMonthVal = ToDateArray[1];
                ToYearVal = ToDateArray[2];
                UpdatedChartFragment updatedChartFragment = new UpdatedChartFragment();

                Bundle bundle = new Bundle();
           /*     bundle.putString("FromDayVal", FromDayVal);
                bundle.putString("fromMonthVal", fromMonthVal);
                bundle.putString("fromYearVal", fromYearVal);
                bundle.putString("ToDayVal", ToDayVal);
                bundle.putString("ToMonthVal", ToMonthVal);
                bundle.putString("ToYearVal", ToYearVal);*/
                bundle.putStringArray("DateArray",DateArray);
                bundle.putStringArray("ToDateArray",ToDateArray);


                updatedChartFragment.setArguments(bundle);



                fm.replace(R.id.graphFrameLayoutId, updatedChartFragment);
                fm.commit();


            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getProfit(double profit) {
        totalProfit=profit;
        String prfString=Double.toString(totalProfit);
        profitTxt.setText(prfString);

        int retVal=Double.compare(totalProfit, 0.0);

      //  if(totalProfit>0.0){
        //    profitType.setText("Profit");
        //}else {

        //}

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getIncome(double income) {
        totalIncome=income;
        String incString=Double.toString(totalIncome);
        incomeTxt.setText("Rs."+ incString);

    }

    @Override
    public void getProfitType(String profitType) {
       profType.setText(profitType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getExpense(double expense) {
        totalExpense=expense;
        String expString=Double.toString(totalExpense);
        expenseTxt.setText("Rs."+expString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTuitionIncome(double C_Tui_Income) {

        tuitionIncome=C_Tui_Income;
        String tuiString=Double.toString(tuitionIncome);
        tuiIncomeTxt.setText("Rs."+tuiString);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getRegIncome(double C_Reg_Income) {

        RegIncome=C_Reg_Income;
        String regString=Double.toString(RegIncome);
        regIncomeTxt.setText("Rs."+regString);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getExtraIncome(double C_Extra_Income) {

        extraIncome=C_Extra_Income;
        String extraString=Double.toString(extraIncome);
        extraIncomeTxt.setText("Rs."+extraString);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getChartIncome(double C_income) {
        totalIncome=C_income;
        String incString=Double.toString(totalIncome);
        incomeTxt.setText("Rs."+ incString);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTeacherSal(double C_Teacher_Expense) {

        teachExpense=C_Teacher_Expense;
        String teachString=Double.toString(teachExpense);
        teachSal.setText("Rs."+teachString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getExtraExpense(double C_Extra_Expense) {
        extraExp=C_Extra_Expense;
        String extraString=Double.toString(extraExp);
        extraExpense.setText("Rs."+extraString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getChartExpense(double C_expense) {
        totalExpense=C_expense;
        String expString=Double.toString(totalExpense);
        expenseTxt.setText("Rs."+expString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getChartProfit(double C_profit) {

        totalProfit=C_profit;
        String prfString=Double.toString(totalProfit);
        profitTxt.setText("Rs."+prfString);

    }
}