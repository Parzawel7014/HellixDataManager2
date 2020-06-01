package com.example.atilagapps.hellixdatamanager.Charts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChartSharedViewModel extends ViewModel {

    private MutableLiveData<CharSequence> From_day = new MutableLiveData<>();
    private MutableLiveData<CharSequence> From_month = new MutableLiveData<>();
    private MutableLiveData<CharSequence> From_Year = new MutableLiveData<>();
    private MutableLiveData<CharSequence> To_day = new MutableLiveData<>();
    private MutableLiveData<CharSequence> To_month = new MutableLiveData<>();
    private MutableLiveData<CharSequence> To_Year = new MutableLiveData<>();


    public void setFrom_day(CharSequence F_day){
        From_day.setValue(F_day);
    }


    public void setFrom_month(CharSequence F_month){
        From_day.setValue(F_month);
    }

    public void setFrom_Year(CharSequence F_year){
        From_day.setValue(F_year);
    }


    public void setTo_day(CharSequence T_day){
        From_day.setValue(T_day);
    }


    public void setTo_month(CharSequence T_month){
        From_day.setValue(T_month);
    }

    public void setTo_Year(CharSequence T_year){
        From_day.setValue(T_year);
    }





    public LiveData<CharSequence> getFrom_day(){
        return From_day;
    }
    public LiveData<CharSequence> getFrom_month(){
        return From_month;
    }
    public LiveData<CharSequence> getFrom_year(){
        return From_Year;
    }
    public LiveData<CharSequence> getTo_day(){
        return To_day;
    }
    public LiveData<CharSequence> getTo_month(){
        return To_month;
    }
    public LiveData<CharSequence> getTo_year(){
        return To_Year;
    }



}
