package com.example.bangkumist.moviecatalogue.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bangkumist.moviecatalogue.activity.MainActivity;
import com.example.bangkumist.moviecatalogue.activity.SearchMovieActivity;
import com.example.bangkumist.moviecatalogue.adapter.ItemClickSupport;
import com.example.bangkumist.moviecatalogue.model.MovieItems;
import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.activity.DetailActivity;
import com.example.bangkumist.moviecatalogue.adapter.FilmAdapter;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.example.bangkumist.moviecatalogue.rest.ApiService;
import com.example.bangkumist.moviecatalogue.rest.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bangkumist.moviecatalogue.activity.SearchMovieActivity.QUERY_MOVIE;
import static com.example.bangkumist.moviecatalogue.utils.Utils.API_KEY;


public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    FilmAdapter adapter;
    List<Results> mResult = new ArrayList<>();
    ProgressBar progressBar;
    Context mContext;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.rv_movie);
        setHasOptionsMenu(true);


        showRecycler();
        restApi();

        return view;
    }
    private void showRecycler(){
        adapter = new FilmAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Results results = mResult.get(position);

                Intent intent = new Intent(recyclerView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DATA, results);
                startActivity(intent);
            }
        });
    }
    private void restApi(){
        ApiService api = RetrofitConfig.getApiServices();
        final Call<MovieItems> mCall = api.getMovie(API_KEY);

        mCall.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(@NonNull Call<MovieItems> call, @NonNull Response<MovieItems> response) {
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    mResult = Objects.requireNonNull(Objects.requireNonNull(response).body()).getmResults();
                }
                adapter.setFilmItems(mResult);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Whoopsss",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mResult = savedInstanceState.getParcelableArrayList("KEY");
        } else {
            restApi();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("KEY", new ArrayList<Parcelable>(adapter.getList()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search_movie).getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchMovieActivity.class);
                intent.putExtra(QUERY_MOVIE, query);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}

