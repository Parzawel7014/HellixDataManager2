package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Staff_Class implements Parcelable {

    String staff_name;
    String staff_subject_name;
    Bitmap staff_image;
    String id;


    public Staff_Class(String id,String staff_name, String staff_subject_name,Bitmap staff_image) {
        this.staff_name = staff_name;
        this.staff_subject_name = staff_subject_name;
        this.staff_image=staff_image;
        this.id=id;
    }

    protected Staff_Class(Parcel in) {
        staff_name = in.readString();
        staff_subject_name = in.readString();
        staff_image = in.readParcelable(Bitmap.class.getClassLoader());
        id = in.readString();
    }

    public static final Creator<Staff_Class> CREATOR = new Creator<Staff_Class>() {
        @Override
        public Staff_Class createFromParcel(Parcel in) {
            return new Staff_Class(in);
        }

        @Override
        public Staff_Class[] newArray(int size) {
            return new Staff_Class[size];
        }
    };

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_subject_name() {
        return staff_subject_name;
    }

    public void setStaff_subject_name(String staff_subject_name) {
        this.staff_subject_name = staff_subject_name;
    }

    public Bitmap getStaff_image() {
        return staff_image;
    }

    public void setStaff_image(Bitmap staff_image) {
        this.staff_image = staff_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(staff_name);
        dest.writeString(staff_subject_name);
        dest.writeParcelable(staff_image, flags);
        dest.writeString(id);
    }
}
