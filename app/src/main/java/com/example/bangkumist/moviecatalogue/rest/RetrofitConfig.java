package com.example.bangkumist.moviecatalogue.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bangkumist.moviecatalogue.utils.Utils.API_URL;

public class RetrofitConfig {

    private static Retrofit getClient() {
        return new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }
    public static ApiService getApiServices(){
        return getClient().create(ApiService.class);
    }
}
