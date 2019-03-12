package com.example.bangkumist.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvItems implements Parcelable {
    @SerializedName("results")
    private List<TvResults> mResults;




    public List<TvResults> getmResults() {
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
    private TvItems(Parcel in) {
        this.mResults = in.createTypedArrayList(TvResults.CREATOR);
    }

    public static final Parcelable.Creator<TvItems> CREATOR = new Parcelable.Creator<TvItems>() {
        @Override
        public TvItems createFromParcel(Parcel in) {
            return new TvItems(in);
        }

        @Override
        public TvItems[] newArray(int size) {
            return new TvItems[size];
        }
    };
}
