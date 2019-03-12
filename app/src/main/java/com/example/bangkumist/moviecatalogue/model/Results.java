package com.example.bangkumist.moviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Language;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Overview;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Vote;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.getColumnInt;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.getColumnString;

import com.google.gson.annotations.SerializedName;

public class Results implements Parcelable {
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("backdrop_path")
    private String mBackdrop;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("original_language")
    private String mLanguage;
    @SerializedName("vote_count")
    private String mVoteC;
    @SerializedName("release_date")
    private String mRelease;
    @SerializedName("id")
    private Integer id;


    public Results(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.mOverview = getColumnString(cursor, Overview);
        this.mLanguage = getColumnString(cursor, Language);
        this.mVoteC = getColumnString(cursor, Vote);
        this.mPosterPath = getColumnString(cursor, POSTER_PATH);
        this.mTitle = getColumnString(cursor, TITLE);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getmPosterPath() {
        return mPosterPath;
    }


    public String getmBackdrop() {
        return mBackdrop;
    }


    public String getmOverview() {
        return mOverview;
    }


    public String getmTitle() {
        return mTitle;
    }


    public String getmLanguage() {
        return mLanguage;
    }


    public String getmVoteC() {
        return mVoteC;
    }


    public String getmRelease() {
        return mRelease;
    }




    private Results(Parcel in) {
        mPosterPath = in.readString();
        mBackdrop = in.readString();
        mOverview = in.readString();
        mTitle = in.readString();
        mLanguage = in.readString();
        mVoteC = in.readString();
        mRelease = in.readString();
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mBackdrop);
        dest.writeString(mOverview);
        dest.writeString(mTitle);
        dest.writeString(mLanguage);
        dest.writeString(mVoteC);
        dest.writeString(mRelease);
        dest.writeValue(id);
    }

    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}
