package com.example.bangkumist.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.bangkumist.moviecatalogue.db.FavoriteHelper;
import com.example.bangkumist.moviecatalogue.db.TvHelper;

import java.util.Objects;

import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.AUTHORITY;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.CONTENT_URI;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.CONTENT_URI_TV;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TABLE_FAVORITE;
import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.TABLE_TV;

public class provider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static  final int TV = 3;
    private static final int TV_ID = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, MOVIE);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE+ "/#",
                MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_TV, TV);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_TV + "/#",
                TV_ID) ;
    }

    private FavoriteHelper movieHelper;
    private TvHelper tvHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new FavoriteHelper(getContext());
        movieHelper.open();
        tvHelper = new TvHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        long added;
        Uri _uri = null;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                if (added > 0){
                    _uri = ContentUris.withAppendedId(CONTENT_URI, added);
                }
                break;
            case TV:
                added = tvHelper.insertProvider(contentValues);
                if (added > 0){
                    _uri = ContentUris.withAppendedId(CONTENT_URI_TV, added);
                }
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated =  movieHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
