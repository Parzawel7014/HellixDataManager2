package com.example.atilagapps.hellixdatamanager.Charts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
import com.example.atilagapps.hellixdatamanager.R;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AddFeesActivity;
import com.example.atilagapps.hellixdatamanager.TuitionFess.AmountDialogueClass;

import java.util.ArrayList;
import java.util.List;


public class UpdatedChartFragment extends Fragment {
    String FromDayVal;
    String fromMonthVal;
    String fromYearVal;

    String ToDayVal;
    String ToMonthVal;
    String ToYearVal;


    UpdateChartListener UListener;

    ChartSharedViewModel chartSharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_updated_chart, container, false);

        assert getArguments() != null;
    /*    FromDayVal=getArguments().getString("FromDayVal");
        fromMonthVal=getArguments().getString("fromMonthVal");
        fromYearVal=getArguments().getString("fromYearVal");
        ToDayVal=getArguments().getString("ToDayVal");
        ToMonthVal=getArguments().getString("ToMonthVal");
        ToYearVal=getArguments().getString("ToYearVal");*/
        DataBaseHelper db = new DataBaseHelper(v.getContext());

        String[] DateArray = getArguments().getStringArray("DateArray");
        String[] ToDateArray = getArguments().getStringArray("ToDateArray");

        assert DateArray != null;
        FromDayVal = DateArray[0];
        fromMonthVal = DateArray[1];
        fromYearVal = DateArray[2];


        assert ToDateArray != null;
        ToDayVal = ToDateArray[0];
        ToMonthVal = ToDateArray[1];
        ToYearVal = ToDateArray[2];

        double MonthlyExpense = db.getAllExpensesInPeriod(FromDayVal, fromMonthVal, fromYearVal, ToDayVal, ToMonthVal, ToYearVal);

        double monthly = db.getAllIncomeInPeriod(FromDayVal, fromMonthVal, fromYearVal, ToDayVal, ToMonthVal, ToYearVal);
        double TotalRegAmount = db.getAllRegAmountInPeriod(FromDayVal, fromMonthVal, fromYearVal, ToDayVal, ToMonthVal, ToYearVal);

        double extraIncome = db.getExtraIncomeInPeriod(FromDayVal, fromMonthVal, fromYearVal, ToDayVal, ToMonthVal, ToYearVal);
        double extraExpense = db.getExtraExpenseInPeriod(FromDayVal, fromMonthVal, fromYearVal, ToDayVal, ToMonthVal, ToYearVal);


        double Income = monthly + TotalRegAmount + extraIncome;
        double Expense = MonthlyExpense + extraExpense;

        double Profit = Income - Expense;

        int retVal=Double.compare(Profit, 0.0);

        String profitStat;

        if (retVal<0){
            profitStat="Loss";
        }else {
            profitStat="Profit";
        }

        UListener.getProfitType(profitStat);

        UListener.getExpense(Expense);
        UListener.getIncome(Income);
        UListener.getProfit(Profit);


        UListener.getTuitionIncome(monthly);
        UListener.getRegIncome(TotalRegAmount);
        UListener.getExtraIncome(extraIncome);
        UListener.getTeacherSal(MonthlyExpense);
        UListener.getExtraExpense(extraExpense);


        AnyChartView anyChartView = v.findViewById(R.id.UpdatedPieChartId);
        anyChartView.setProgressBar(v.findViewById(R.id.updatedProgressBarId));


        if (Income == 0 && Expense == 0) {
            AlertDialog.Builder reconfirmBuilder = new AlertDialog.Builder(getContext());
            reconfirmBuilder.setTitle("Alert");
            reconfirmBuilder.setMessage("No Data Found!");
            reconfirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog cnfDialogue = reconfirmBuilder.create();
            cnfDialogue.show();

        }


        Pie pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener() {
            @Override
            public void onClick(Event event) {

            }
        });


        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Expenses", Expense));
        data.add(new ValueDataEntry("Incomes", Income));
        //  data.add(new ValueDataEntry("Bananas", 7216301));
        //data.add(new ValueDataEntry("Grapes", 1486621));
        //data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);

        pie.title("Income/Expense Record");

        pie.labels().position("outside");

        // pie.legend().title().enabled(true);
        // pie.legend().title()
        //       .text("Retail channels")
        //     .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);


        return v;


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            UListener = (UpdateChartListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface UpdateChartListener {

        void getTuitionIncome(double C_Tui_Income);

        void getRegIncome(double C_Reg_Income);

        void getExtraIncome(double C_Extra_Income);


        void getTeacherSal(double C_Teacher_Expense);

        void getExtraExpense(double C_Extra_Expense);

        void getProfit(double profit);

        void getIncome(double income);
        void getProfitType(String profitType);

        void getExpense(double expense);
    }
}