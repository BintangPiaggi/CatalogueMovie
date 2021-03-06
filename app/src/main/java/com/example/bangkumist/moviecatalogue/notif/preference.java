package com.example.bangkumist.moviecatalogue.notif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class preference {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    private final static String PREFERENCE = "reminderPreferences";
    private final static String KEY_DAILY = "DailyReminder";
    private final static String KEY_MESSAGE_Release = "messageRelease";
    private final static String KEY_MESSAGE_DAILY = "messageDaily";


    @SuppressLint("CommitPrefEdits")
    public preference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setTimeRelease(String time){
        editor.putString(KEY_DAILY,time);
        editor.commit();
    }
    public void setReleaseMessage (String message){
        editor.putString(KEY_MESSAGE_Release,message);
    }
    public void setTimeDaily(String time){
        editor.putString(KEY_DAILY,time);
        editor.commit();
    }
    public void setDailyMessage(String message){
        editor.putString(KEY_MESSAGE_DAILY,message);
    }
}
