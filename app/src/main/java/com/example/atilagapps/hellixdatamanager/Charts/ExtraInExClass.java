package com.example.atilagapps.hellixdatamanager.Charts;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ExtraInExClass implements Parcelable  {

    String payment_person;
    String payment_amount;
    String payment_date;
  String payment_type_code;
    String payment_type;
    String id;

    public ExtraInExClass(String payment_person, String payment_amount, String payment_date, String id,String payment_type,String payment_type_code) {
        this.payment_person = payment_person;
        this.payment_amount = payment_amount;
        this.payment_date = payment_date;
       // this.payment_image = payment_image;
        this.payment_type_code=payment_type_code;
        this.payment_type=payment_type;
        this.id = id;
    }


    protected ExtraInExClass(Parcel in) {
        payment_person = in.readString();
        payment_amount = in.readString();
        payment_date = in.readString();
       // payment_image = in.readParcelable(Bitmap.class.getClassLoader());
        id = in.readString();
        payment_type=in.readString();
        payment_type_code=in.readString();
    }

    public static final Creator<ExtraInExClass> CREATOR = new Creator<ExtraInExClass>() {
        @Override
        public ExtraInExClass createFromParcel(Parcel in) {
            return new ExtraInExClass(in);
        }

        @Override
        public ExtraInExClass[] newArray(int size) {
            return new ExtraInExClass[size];
        }
    };

    public String getPayment_person() {
        return payment_person;
    }

    public void setPayment_person(String payment_person) {
        this.payment_person = payment_person;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

   /* public Bitmap getPayment_image() {
        return payment_image;
    }

    public void setPayment_image(Bitmap payment_image) {
        this.payment_image = payment_image;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }


    public String getPayment_type_code() {
        return payment_type_code;
    }

    public void setPayment_type_code(String payment_type_code) {
        this.payment_type_code = payment_type_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(payment_person);
        dest.writeString(payment_amount);
        dest.writeString(payment_date);
        //dest.writeParcelable(payment_image, flags);
        dest.writeString(id);
        dest.writeString(payment_type);
        dest.writeString(payment_type_code);
    }
}
