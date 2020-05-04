package com.example.atilagapps.hellixdatamanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atilagapps.hellixdatamanager.Fragments.CourseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> name = new MutableLiveData<>();
    private MutableLiveData<CharSequence> address = new MutableLiveData<>();
    private MutableLiveData<CharSequence> phone = new MutableLiveData<>();
    private MutableLiveData<CharSequence> GenderText = new MutableLiveData<>();
    private MutableLiveData<CharSequence> CastText = new MutableLiveData<>();
    private MutableLiveData<CharSequence> emailTxt = new MutableLiveData<>();
    private MutableLiveData<CharSequence> EduTxt = new MutableLiveData<>();


    public void setName(CharSequence Name) {
        name.setValue(Name);
    }

    public void setAddress(CharSequence Address) {
        address.setValue(Address);
    }

    public void setPhone(CharSequence Phone) {
        phone.setValue(Phone);
    }

    public void setEmailTxt(CharSequence Email){
        emailTxt.setValue(Email);
    }

    public  void setEduTxt(CharSequence Edu){
        EduTxt.setValue(Edu);
    }

    public void setGenderText(CharSequence genderText){
        GenderText.setValue(genderText);
    }

    public void setCastText(CharSequence castText){
        CastText.setValue(castText);
    }




    public LiveData<CharSequence> getName() {
        return name;
    }

    public LiveData<CharSequence> getAddress() {
        return address;
    }

    public LiveData<CharSequence> getPhone() {
        return phone;
    }


    public LiveData<CharSequence> getCastValue(){
        return CastText;
    }

    public LiveData<CharSequence> getGenderValue(){
        return GenderText;
    }

    public LiveData<CharSequence> getEmailValue(){
        return emailTxt;
    }
    public LiveData<CharSequence> getEduValue(){
        return EduTxt;
    }



    /*public LiveData<CharSequence> getBatch(){
        return batch;
    }*/


}
