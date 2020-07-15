package com.example.atilagapps.hellixdatamanager.Students;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class FindStudent implements Parcelable {

    private String mStudentName;
    private String mStudentID;
    private String mStudentRegId;
    private String mStudentPhone;
    private String mStudentAddress;
    private String mStudentGender;
    private String mStudentEducation;
    Bitmap mStudentImage;



    public FindStudent(String mStudentName,String Id,String regId,Bitmap studentImage) {

        this.mStudentName = mStudentName;
        this.mStudentID=Id;
        this.mStudentRegId=regId;
        this.mStudentImage=studentImage;
    }


    protected FindStudent(Parcel in)
    {
        mStudentName = in.readString();
        mStudentID=in.readString();
        mStudentRegId=in.readString();
        mStudentImage=in.readParcelable(Bitmap.class.getClassLoader());
    }


    public Bitmap getmStudentImage() {
        return mStudentImage;
    }

    public void setmStudentImage(Bitmap mStudentImage) {
        this.mStudentImage = mStudentImage;
    }

    public String getmStudentRegId() {
        return mStudentRegId;
    }

    public void setmStudentRegId(String mStudentRegId) {
        this.mStudentRegId = mStudentRegId;
    }

    public static final Creator<FindStudent> CREATOR = new Creator<FindStudent>() {
        @Override
        public FindStudent createFromParcel(Parcel in) {
            return new FindStudent(in);
        }

        @Override
        public FindStudent[] newArray(int size) {
            return new FindStudent[size];
        }
    };

    public String getmStudentName() {
        return mStudentName;
    }

    public void setmStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public String getmStudentID() {
        return mStudentID;
    }

    public void setmStudentID(String mStudentID) {
        this.mStudentID = mStudentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStudentName);
        dest.writeString(mStudentID);
        dest.writeString(mStudentRegId);
        dest.writeParcelable(mStudentImage, flags);
    }
}
