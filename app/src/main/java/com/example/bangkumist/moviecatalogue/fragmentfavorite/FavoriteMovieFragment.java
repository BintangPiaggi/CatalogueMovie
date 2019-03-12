package com.example.bangkumist.moviecatalogue.fragmentfavorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.adapter.MovieFavoriteAdapter;

import static com.example.bangkumist.moviecatalogue.db.DatabaseContract.CONTENT_URI;

public class FavoriteMovieFragment extends Fragment {


    private MovieFavoriteAdapter adapterFavorite;
    private Cursor list;
    private RecyclerView recyclerView;
    public FavoriteMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_fav_movie);

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
        adapterFavorite = new MovieFavoriteAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapterFavorite);
        adapterFavorite.setListMovie(list);
        recyclerView.setHasFixedSize(true);
    }

    private class loadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(
                    CONTENT_URI,
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
