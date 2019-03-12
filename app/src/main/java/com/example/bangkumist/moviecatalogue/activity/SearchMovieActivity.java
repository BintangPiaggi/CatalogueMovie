package com.example.bangkumist.moviecatalogue.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.adapter.FilmAdapter;
import com.example.bangkumist.moviecatalogue.adapter.ItemClickSupport;
import com.example.bangkumist.moviecatalogue.model.MovieItems;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.example.bangkumist.moviecatalogue.rest.ApiService;
import com.example.bangkumist.moviecatalogue.rest.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bangkumist.moviecatalogue.utils.Utils.API_KEY;

public class SearchMovieActivity extends AppCompatActivity {
    public static String QUERY_MOVIE = "query";
    RecyclerView recyclerView;
    ProgressBar pb;
    FilmAdapter adapter;
    List<Results> mResult = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        recyclerView = findViewById(R.id.rv_search_movie);
        pb = findViewById(R.id.progress_search_movie);
        toolbar = findViewById(R.id.toolbar_search_movie);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String query =  intent.getStringExtra(QUERY_MOVIE);

        showRecycler();
        restApi(query);

    }
    private void showRecycler(){
        adapter = new FilmAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
    private void restApi(String query){
        ApiService api = RetrofitConfig.getApiServices();
        final Call<MovieItems> mCall = api.getSearchMovie(query, API_KEY);

        mCall.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(@NonNull Call<MovieItems> call, @NonNull Response<MovieItems> response) {
                if (response.body() != null) {
                    pb.setVisibility(View.GONE);
                    mResult = Objects.requireNonNull(Objects.requireNonNull(response).body()).getmResults();
                }
                adapter.setFilmItems(mResult);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                Toast.makeText(SearchMovieActivity.this, "Whoopsss",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();

        }

        return super.onOptionsItemSelected(item);

    }
}
