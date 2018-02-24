package com.deaenita.popumovi.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 09/12/2017.
 */

public class RetrofitConfig {
    private static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }
    public static ApiService getApiService(){
        return getRetrofit().create(ApiService.class);
    }
}
