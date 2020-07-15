package com.example.atilagapps.hellixdatamanager.Charts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    ChartInterface CmListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_chart, container, false);

        View v = inflater.inflate(R.layout.fragment_chart, container, false);

        DataBaseHelper db=new DataBaseHelper(v.getContext());

        double MonthlyExpense=db.getAllExpenses();
      //  double x=MonthlyExpense;
        double monthly=db.getAllIncome();
        double TotalRegAmount=db.getAllRegAmount();


        double extraIncome=db.getAllExtraIncome();

        double extraExpense=db.getAllExtraExpense();

        double Income=monthly+TotalRegAmount+extraIncome;
        double Expense=MonthlyExpense+extraExpense;




        double Profit=Income-Expense;

        int retVal=Double.compare(Profit, 0.0);

        String profitStat;

        if (retVal<0){
            profitStat="Loss";
        }else {
            profitStat="Profit";
        }

        CmListener.getProfitType(profitStat);

        CmListener.getChartIncome(Income);
        CmListener.getChartExpense(Expense);
        CmListener.getChartProfit(Profit);
        CmListener.getTuitionIncome(monthly);
        CmListener.getRegIncome(TotalRegAmount);
        CmListener.getExtraIncome(extraIncome);
        CmListener.getTeacherSal(MonthlyExpense);
        CmListener.getExtraExpense(extraExpense);




        AnyChartView anyChartView=v.findViewById(R.id.pieChartId);
        anyChartView.setProgressBar(v.findViewById(R.id.progressBarId));

        Pie pie= AnyChart.pie();
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
            CmListener = (ChartInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface ChartInterface{

        void getTuitionIncome(double C_Tui_Income);
        void getRegIncome(double C_Reg_Income);
        void getExtraIncome(double C_Extra_Income);
        void getChartIncome(double C_income);


        void getTeacherSal(double C_Teacher_Expense);
        void getExtraExpense(double C_Extra_Expense);
        void getChartExpense(double C_expense);


        void getProfitType(String profitType);

        void getChartProfit(double C_profit);

    }
}