package com.bangkumist.bintang.favorite.fragment;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangkumist.bintang.favorite.R;
import com.bangkumist.bintang.favorite.adapter.Adapter;
import com.bangkumist.bintang.favorite.adapter.TvFavoriteAdapter;

import java.util.Objects;

import static com.bangkumist.bintang.favorite.db.DatabaseContract.CONTENT_URI;
import static com.bangkumist.bintang.favorite.db.DatabaseContract.CONTENT_URI_TV;

public class TvFavoriteFragment extends Fragment {
    private TvFavoriteAdapter adapterFavorite;
    private Cursor list;
    private RecyclerView recyclerView;

    public TvFavoriteFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_fav_tv);

        new loadData().execute();
        showRecycler();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void showRecycler() {
        adapterFavorite = new TvFavoriteAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapterFavorite);
        adapterFavorite.setListMovie(list);
        recyclerView.setHasFixedSize(true);
    }

    @SuppressLint("StaticFieldLeak")
    private class loadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(
                    CONTENT_URI_TV,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            adapterFavorite.setListMovie(list);
            adapterFavorite.notifyDataSetChanged();
        }
    }
}
