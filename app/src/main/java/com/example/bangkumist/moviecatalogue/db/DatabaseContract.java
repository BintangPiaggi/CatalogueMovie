package com.example.bangkumist.moviecatalogue.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE = "fav";
    private static String SCHEME = "content";
    public static String TABLE_TV = "tv";

    public static final class MovieColumns implements BaseColumns {
        public static String POSTER_PATH = "poster_path";
        public static String TITLE = "title";
        public static String Language = "language";
        public static String Overview = "Overview";
        public static String Vote = "Vote";


    }

    public static final class TvColumns implements BaseColumns {
        public static String TVPOSTER_PATH = "tvposter_path";
        public static String TVNAME = "title";
        public static String TVLanguage = "language";
        public static String TVOverview = "Overview";
        public static String TVVote = "Vote";
    }

    public static final String AUTHORITY = "com.example.bangkumist.moviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
    .authority(AUTHORITY)
    .appendPath(TABLE_TV)
    .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}