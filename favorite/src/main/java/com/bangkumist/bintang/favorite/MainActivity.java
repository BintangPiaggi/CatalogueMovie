package com.bangkumist.bintang.favorite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bangkumist.bintang.favorite.adapter.Adapter;
import com.bangkumist.bintang.favorite.adapter.TabAdapter;
import com.bangkumist.bintang.favorite.fragment.MovieFavoriteFragment;
import com.bangkumist.bintang.favorite.fragment.TvFavoriteFragment;

import java.util.Objects;

import static com.bangkumist.bintang.favorite.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    void setViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.populateFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        adapter.populateFragment(new TvFavoriteFragment(), getString(R.string.tv));
        viewPager.setAdapter(adapter);
    }
}