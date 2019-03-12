package com.example.bangkumist.moviecatalogue.activity;

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
import com.example.bangkumist.moviecatalogue.adapter.TvAdapter;
import com.example.bangkumist.moviecatalogue.model.MovieItems;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.example.bangkumist.moviecatalogue.model.TvItems;
import com.example.bangkumist.moviecatalogue.model.TvResults;
import com.example.bangkumist.moviecatalogue.rest.ApiService;
import com.example.bangkumist.moviecatalogue.rest.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bangkumist.moviecatalogue.utils.Utils.API_KEY;

public class SearchTvActivity extends AppCompatActivity {

    public static String QUERY_TV = "query";
    RecyclerView recyclerView;
    ProgressBar pb;
    TvAdapter adapter;
    List<TvResults> mResult = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);
        recyclerView = findViewById(R.id.rv_search_tv);
        pb = findViewById(R.id.progress_search_tv);
        toolbar = findViewById(R.id.toolbar_search_tv);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String query =  intent.getStringExtra(QUERY_TV);

        showRecycler();
        restApi(query);

    }
    private void showRecycler(){
        adapter = new TvAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                TvResults results = mResult.get(position);

                Intent intent = new Intent(recyclerView.getContext(), DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_DATA_TV, results);
                startActivity(intent);
            }
        });
    }
    private void restApi(String query){
        ApiService api = RetrofitConfig.getApiServices();
        final Call<TvItems> mCall = api.getSearchTv(query, API_KEY);

        mCall.enqueue(new Callback<TvItems>() {
            @Override
            public void onResponse(@NonNull Call<TvItems> call, @NonNull Response<TvItems> response) {
                if (response.body() != null) {
                    pb.setVisibility(View.GONE);
                    mResult = Objects.requireNonNull(Objects.requireNonNull(response).body()).getmResults();
                }
                adapter.setFilmItems(mResult);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<TvItems> call, @NonNull Throwable t) {
                Toast.makeText(SearchTvActivity.this, "Whoopsss",Toast.LENGTH_SHORT).show();
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

