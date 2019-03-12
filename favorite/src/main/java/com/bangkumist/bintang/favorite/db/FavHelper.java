package com.bangkumist.bintang.favorite.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.bangkumist.bintang.favorite.db.DatabaseContract.TABLE_FAVORITE;

public class FavHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;

    SQLiteDatabase database;

    public FavHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper databaseHelper = new dbHelper(context);
        database = databaseHelper.getWritableDatabase();
    }
}

