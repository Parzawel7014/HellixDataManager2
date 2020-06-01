package com.example.atilagapps.hellixdatamanager.Fragments;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageClass implements Parcelable {
    byte[] img;

    public ImageClass(byte[] img) {
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    protected ImageClass(Parcel in) {
        img = in.createByteArray();
    }

    public static final Creator<ImageClass> CREATOR = new Creator<ImageClass>() {
        @Override
        public ImageClass createFromParcel(Parcel in) {
            return new ImageClass(in);
        }

        @Override
        public ImageClass[] newArray(int size) {
            return new ImageClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(img);
    }
}
