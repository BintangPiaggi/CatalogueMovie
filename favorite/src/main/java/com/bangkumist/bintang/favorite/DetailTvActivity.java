package com.bangkumist.bintang.favorite;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangkumist.bintang.favorite.db.TvHelper;
import com.bangkumist.bintang.favorite.model.TvResults;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.bangkumist.bintang.favorite.Utils.POSTER_URL;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.CONTENT_URI_TV;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TvColumns.TVLanguage;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TvColumns.TVNAME;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TvColumns.TVOverview;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TvColumns.TVPOSTER_PATH;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.TvColumns.TVVote;

public class DetailTvActivity extends AppCompatActivity {
    public static String EXTRA_DATA_TV = "extraData";

    private boolean isAdd = false;
    private TvResults results;
    TvHelper favoriteHelper;
    @BindView(R.id.date_detail)
    TextView mDate;
    @BindView(R.id.img_detail)
    ImageView mImg;
    @BindView(R.id.lang_detail)
    TextView mLang;
    @BindView(R.id.overview_detail)
    TextView mOver;
    @BindView(R.id.title_detail)
    TextView mTitle;
    @BindView(R.id.vote_detail)
    TextView mVote;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.fbtv)
    FloatingActionButton fabtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        ButterKnife.bind(this);

        results = getIntent().getParcelableExtra(EXTRA_DATA_TV);
        String title = results.getmName();
        String language = results.getmLanguage();
        String average = results.getmVoteC();
        String synopsis = results.getmOverview();
        String image = results.getmPosterPath();
        String dateTime = results.getmFirst();

        loadData();
        mDate.setText(dateTime);
        mTitle.setText(title);
        mLang.setText(language);
        mVote.setText(average);
        mOver.setText(synopsis);
        Picasso.get()
                .load(POSTER_URL + image)
                .into(mImg);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        fabtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd){
                    removeFavorite();
                }else {
                    addFavorite();
                }
                isAdd = !isAdd;
                if (isAdd) fabtv.setImageResource(R.drawable.ic_favorite);
                else fabtv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        });

    }

    private void addFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID,results.getId());
        contentValues.put(TVPOSTER_PATH, results.getmPosterPath());
        contentValues.put(TVOverview, results.getmOverview());
        contentValues.put(TVLanguage, results.getmLanguage());
        contentValues.put(TVVote, results.getmVoteC());
        contentValues.put(TVNAME, results.getmName());



        getContentResolver().insert(CONTENT_URI_TV, contentValues);
        Toast.makeText(this, R.string.add, Toast.LENGTH_LONG).show();
    }

    private void removeFavorite() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI_TV + "/" + results.getId()),
                null,
                null
        );
        Toast.makeText(this, R.string.remove, Toast.LENGTH_LONG).show();
    }

    private void loadData(){
        favoriteHelper = new TvHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_TV + "/" + results.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null){
            if (cursor.moveToFirst()) isAdd = true;
            cursor.close();
        }

        if (isAdd) fabtv.setImageResource(R.drawable.ic_favorite);
        else fabtv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();

        }
        return super.onOptionsItemSelected(item);

    }
}

