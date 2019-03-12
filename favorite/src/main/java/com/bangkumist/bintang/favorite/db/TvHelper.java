package com.bangkumist.bintang.favorite.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.bangkumist.bintang.favorite.db.DatabaseContract.TABLE_FAVORITE;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TABLE_TV;

public class TvHelper {
    private static String DATABASE_TABLE = TABLE_TV;
    private Context context;

    SQLiteDatabase database;

    public TvHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper databaseHelper = new dbHelper(context);
        database = databaseHelper.getWritableDatabase();
    }
}
