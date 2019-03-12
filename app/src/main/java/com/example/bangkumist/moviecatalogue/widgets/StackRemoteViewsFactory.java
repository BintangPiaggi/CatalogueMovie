package com.example.bangkumist.moviecatalogue.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.model.Results;

import java.util.concurrent.ExecutionException;

import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.CONTENT_URI;
import static com.example.bangkumist.moviecatalogue.utils.Utils.POSTER_URL;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    int mAppWidgetId;
    private Cursor cursor;



    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }



    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                CONTENT_URI, null, null, null, null
        );
    }

    private Results getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Error");
        }

        return new Results(cursor);
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Results mResults = getItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(context)
                    .asBitmap()
                    .load(POSTER_URL + mResults.getmPosterPath())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.imgWidget, bmp);

        Bundle bundle = new Bundle();
        bundle.putInt(AppsWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imgWidget, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
