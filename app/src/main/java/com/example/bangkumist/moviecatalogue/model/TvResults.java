package com.example.bangkumist.moviecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVLanguage;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVNAME;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVOverview;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVPOSTER_PATH;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVVote;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.getColumnInt;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.getColumnString;

import com.google.gson.annotations.SerializedName;

public class TvResults implements Parcelable {
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("backdrop_path")
    private String mBackdrop;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("original_language")
    private String mLanguage;
    @SerializedName("vote_count")
    private String mVoteC;
    @SerializedName("name")
    private String mName;
    @SerializedName("first_air_date")
    private String mFirst;
    @SerializedName("id")
    private Integer id;


    public TvResults(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.mOverview = getColumnString(cursor, TVOverview);
        this.mLanguage = getColumnString(cursor, TVLanguage);
        this.mVoteC = getColumnString(cursor, TVVote);
        this.mPosterPath = getColumnString(cursor, TVPOSTER_PATH);
        this.mName = getColumnString(cursor, TVNAME);
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




    public String getmLanguage() {
        return mLanguage;
    }


    public String getmVoteC() {
        return mVoteC;
    }



    public String getmName() {
        return mName;
    }


    public String getmFirst() {
        return mFirst;
    }


    private TvResults(Parcel in) {
        mPosterPath = in.readString();
        mBackdrop = in.readString();
        mOverview = in.readString();
        mLanguage = in.readString();
        mVoteC = in.readString();
        mName = in.readString();
        mFirst = in.readString();
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
        dest.writeString(mLanguage);
        dest.writeString(mVoteC);
        dest.writeString(mName);
        dest.writeString(mFirst);
        dest.writeValue(id);
    }

    public static final Parcelable.Creator<TvResults> CREATOR = new Parcelable.Creator<TvResults>() {
        @Override
        public TvResults createFromParcel(Parcel in) {
            return new TvResults(in);
        }

        @Override
        public TvResults[] newArray(int size) {
            return new TvResults[size];
        }
    };
}
