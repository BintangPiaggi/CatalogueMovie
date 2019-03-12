package com.example.bangkumist.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TABLE_TV;

public class TvHelper {
    private static String DATABASE_TABLE = TABLE_TV;
    private Context context;

    private SQLiteDatabase database;

    public TvHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE, null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" =?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}


