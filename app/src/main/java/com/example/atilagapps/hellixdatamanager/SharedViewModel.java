package com.example.atilagapps.hellixdatamanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> name=new MutableLiveData<>();
    private MutableLiveData<CharSequence> address=new MutableLiveData<>();
   private MutableLiveData<CharSequence> phone=new MutableLiveData<>();
    private MutableLiveData<CharSequence> batch=new MutableLiveData<>();
    private MutableLiveData<CharSequence> subject=new MutableLiveData<>();

    public void setName(CharSequence Name){
        name.setValue(Name);
    }
   public void setAddress(CharSequence Address){
        address.setValue(Address);

    }
    public void setPhone(CharSequence Phone){
        phone.setValue(Phone);

    }


    public void setBatch(CharSequence Batch){
        batch.setValue(Batch);

    }

    public void setSubject(CharSequence Subject){
        phone.setValue(Subject);

    }

    //public LiveData<CharSequence>
    public LiveData<CharSequence> getName(){
        return name;
    }
   public LiveData<CharSequence> getAddress(){
        return address;
    }
    public LiveData<CharSequence> getPhone(){
        return phone;
    }

    public LiveData<CharSequence> getBatch(){
        return batch;
    }
    public LiveData<CharSequence> getSubject(){
        return subject;
    }
}
