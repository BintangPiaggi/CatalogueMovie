package com.example.bangkumist.moviecatalogue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.notif.DailyReceiver;
import com.example.bangkumist.moviecatalogue.notif.ReleaseReceiver;
import com.example.bangkumist.moviecatalogue.notif.preference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.btn_local)
    Button btnLocal;
    @BindView(R.id.switch_daily)
    Switch switchDaily;
    @BindView(R.id.switch_release)
    Switch switchRelease;

    DailyReceiver dailyReceiver;
    ReleaseReceiver ReleaseReceiver;
    preference notificationPreference;
    SharedPreferences spReleaseReminder, spDailyReminder;
    SharedPreferences.Editor edtReleaseReminder, edtDailyReminder;

    String TYPE_DAILY = "reminderDaily";
    String TYPE_RELEASE = "reminderRelease";
    String DAILY_REMINDER = "dailyReminder";
    String RELEASE_REMINDER = "releaseReminder";
    String KEY_RELEASE = "Release";
    String KEY_DAILY_REMINDER = "Daily";

    String timeDaily = "07:00";
    String timeRelease = "08:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        dailyReceiver = new DailyReceiver();
        ReleaseReceiver = new ReleaseReceiver();
        notificationPreference = new preference(this);
        setPreference();

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });


    }
    private void releaseOn() {
        String message = getResources().getString(R.string.release_movie_message);
        notificationPreference.setTimeRelease(timeRelease);
        notificationPreference.setReleaseMessage(message);
        ReleaseReceiver.setAlarm(SettingActivity.this, TYPE_RELEASE, timeRelease, message);
    }

    private void releaseOff() {
        ReleaseReceiver.CancelNotif(SettingActivity.this);
    }
    private void dailyOn() {
        String message = getResources().getString(R.string.daily);
        notificationPreference.setTimeDaily(timeDaily);
        notificationPreference.setDailyMessage(message);
        dailyReceiver.setAlarm(SettingActivity.this, TYPE_DAILY, timeDaily, message);
    }

    private void dailyOff() {
        dailyReceiver.CancelNotif(SettingActivity.this);
    }
    private void setPreference() {
        spReleaseReminder = getSharedPreferences(RELEASE_REMINDER, MODE_PRIVATE);
        boolean checkUpcomingReminder = spReleaseReminder.getBoolean(KEY_RELEASE, false);
        switchRelease.setChecked(checkUpcomingReminder);
        spDailyReminder = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkDailyReminder = spDailyReminder.getBoolean(KEY_DAILY_REMINDER, false);
        switchDaily.setChecked(checkDailyReminder);
    }
    @OnCheckedChanged(R.id.switch_daily)
    public void setDaily(boolean isChecked) {
        edtDailyReminder = spDailyReminder.edit();
        if (isChecked) {
            edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, true);
            edtDailyReminder.commit();
            dailyOn();
        } else {
            edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, false);
            edtDailyReminder.commit();
            dailyOff();
        }
    }
    @OnCheckedChanged(R.id.switch_release)
    public void setRelease(boolean isChecked) {
        edtReleaseReminder = spReleaseReminder.edit();
        if (isChecked) {
            edtReleaseReminder.putBoolean(KEY_RELEASE, true);
            edtReleaseReminder.commit();
            releaseOn();
        } else {
            edtReleaseReminder.putBoolean(KEY_RELEASE, false);
            edtReleaseReminder.commit();
            releaseOff();
        }
    }
}
