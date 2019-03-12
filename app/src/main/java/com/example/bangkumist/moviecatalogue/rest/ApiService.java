package com.example.bangkumist.moviecatalogue.rest;

import com.example.bangkumist.moviecatalogue.model.MovieItems;
import com.example.bangkumist.moviecatalogue.model.TvItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie")
    Call<MovieItems>getMovie(@Query("api_key") String apiKey);
    @GET("discover/tv")
    Call<TvItems>getTv(@Query("api_key") String apiKey);
    @GET("search/movie")
    Call<MovieItems>getSearchMovie(@Query("query") String query, @Query("api_key") String apiKey);
    @GET("search/tv")
    Call<TvItems>getSearchTv(@Query("query") String query, @Query("api_key") String apiKey);
}
