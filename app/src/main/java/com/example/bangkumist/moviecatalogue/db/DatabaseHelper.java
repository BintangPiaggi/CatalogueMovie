package com.example.bangkumist.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Language;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Overview;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.MovieColumns.Vote;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TABLE_FAVORITE;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TABLE_TV;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVLanguage;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVNAME;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVOverview;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVPOSTER_PATH;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TvColumns.TVVote;

public class DatabaseHelper extends SQLiteOpenHelper {

     private static int DATABASE_VERSION = 14;

     private static String DATABASE_NAME = "dbKatalogFavorite";

     private static final String SQL_CREATE_TABLE_MOVIE =
            String.format(
                    "CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)" ,
                    TABLE_FAVORITE,
                    _ID,
                    POSTER_PATH,
                    Language,
                    Overview,
                    Vote,
                    TITLE
                    );
    private static final String SQL_CREATE_TABLE_TV =
            String.format(
                    "CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)" ,
                    TABLE_TV,
                    _ID,
                    TVPOSTER_PATH,
                    TVLanguage,
                    TVOverview,
                    TVVote,
                    TVNAME
            );


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAVORITE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}