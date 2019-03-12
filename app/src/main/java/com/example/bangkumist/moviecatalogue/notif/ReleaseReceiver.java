package com.example.bangkumist.moviecatalogue.notif;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.activity.MainActivity;
import com.example.bangkumist.moviecatalogue.model.MovieItems;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.example.bangkumist.moviecatalogue.rest.ApiService;
import com.example.bangkumist.moviecatalogue.rest.RetrofitConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bangkumist.moviecatalogue.utils.Utils.API_KEY;

public class ReleaseReceiver extends BroadcastReceiver {
    int NOTIFICATION_ID = 3;
    @Override
    public void onReceive(final Context context, Intent intent) {
        getReleaseMovie(context);
    }
    private void getReleaseMovie(final Context context){
        ApiService api = RetrofitConfig.getApiServices();
        final Call<MovieItems> mCall = api.getMovie(API_KEY);

        mCall.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                List<Results> results = Objects.requireNonNull(response.body()).getmResults();
                String title = results.get(0).getmTitle();
                String message = results.get(0).getmOverview();
                int id = results.get(0).getId();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                final String now = dateFormat.format(date);
                for (Results movie: results){
                    if(movie.getmRelease().equals(now)){
                        showNotif(context, title, message, id);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                Log.d("getMovies", "onFailure: " + t.toString());
            }
        });
    }
    private void showNotif(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "channel_02";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }
    public void CancelNotif(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }

    public void setAlarm(Context context, String type, String time, String message) {
        CancelNotif(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReleaseReceiver.class);
        intent.putExtra("message",message);
        intent.putExtra("type",type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        Objects.requireNonNull(alarmManager).setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
