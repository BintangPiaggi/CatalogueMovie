package com.example.bangkumist.moviecatalogue.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieItems implements Parcelable {
    @SerializedName("results")
    private List<Results> mResults;




    public List<Results> getmResults() {
        return mResults;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mResults);
    }
    private MovieItems(Parcel in) {
        this.mResults = in.createTypedArrayList(Results.CREATOR);
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
