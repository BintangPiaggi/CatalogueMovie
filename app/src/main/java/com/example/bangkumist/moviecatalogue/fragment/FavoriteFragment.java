package com.example.bangkumist.moviecatalogue.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.adapter.TabAdapter;
import com.example.bangkumist.moviecatalogue.fragmentfavorite.FavoriteMovieFragment;
import com.example.bangkumist.moviecatalogue.fragmentfavorite.FavoriteTvFragment;


public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    void setViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());
        adapter.populateFragment(new FavoriteMovieFragment(), getString(R.string.movie));
        adapter.populateFragment(new FavoriteTvFragment(), getString(R.string.tv));
        viewPager.setAdapter(adapter);
    }
}