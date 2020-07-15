package com.example.atilagapps.hellixdatamanager;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class TuitionEdit implements Parcelable {

    String TuiName,TuiAdd,TuiPhone,TuiEmail,TuiAdmin1,TuiAdmin2;
    Bitmap Img;

    public TuitionEdit(String tuiName, String tuiAdd, String tuiPhone, String tuiEmail, String tuiAdmin1, String tuiAdmin2, Bitmap img) {
        TuiName = tuiName;
        TuiAdd = tuiAdd;
        TuiPhone = tuiPhone;
        TuiEmail = tuiEmail;
        TuiAdmin1 = tuiAdmin1;
        TuiAdmin2 = tuiAdmin2;
        Img = img;
    }


    protected TuitionEdit(Parcel in) {
        TuiName = in.readString();
        TuiAdd = in.readString();
        TuiPhone = in.readString();
        TuiEmail = in.readString();
        TuiAdmin1 = in.readString();
        TuiAdmin2 = in.readString();
        Img = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<TuitionEdit> CREATOR = new Creator<TuitionEdit>() {
        @Override
        public TuitionEdit createFromParcel(Parcel in) {
            return new TuitionEdit(in);
        }

        @Override
        public TuitionEdit[] newArray(int size) {
            return new TuitionEdit[size];
        }
    };

    public String getTuiName() {
        return TuiName;
    }

    public void setTuiName(String tuiName) {
        TuiName = tuiName;
    }

    public String getTuiAdd() {
        return TuiAdd;
    }

    public void setTuiAdd(String tuiAdd) {
        TuiAdd = tuiAdd;
    }

    public String getTuiPhone() {
        return TuiPhone;
    }

    public void setTuiPhone(String tuiPhone) {
        TuiPhone = tuiPhone;
    }

    public String getTuiEmail() {
        return TuiEmail;
    }

    public void setTuiEmail(String tuiEmail) {
        TuiEmail = tuiEmail;
    }

    public String getTuiAdmin1() {
        return TuiAdmin1;
    }

    public void setTuiAdmin1(String tuiAdmin1) {
        TuiAdmin1 = tuiAdmin1;
    }

    public String getTuiAdmin2() {
        return TuiAdmin2;
    }

    public void setTuiAdmin2(String tuiAdmin2) {
        TuiAdmin2 = tuiAdmin2;
    }

    public Bitmap getImg() {
        return Img;
    }

    public void setImg(Bitmap img) {
        Img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TuiName);
        dest.writeString(TuiAdd);
        dest.writeString(TuiPhone);
        dest.writeString(TuiEmail);
        dest.writeString(TuiAdmin1);
        dest.writeString(TuiAdmin2);
        dest.writeParcelable(Img, flags);
    }
}
