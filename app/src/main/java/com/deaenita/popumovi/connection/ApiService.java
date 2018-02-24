package com.deaenita.popumovi.connection;

import com.deaenita.popumovi.Model.ListMovieModel;
import com.deaenita.popumovi.Model.ListTrailerModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by USER on 09/12/2017.
 */



public interface ApiService {
    @GET("movie/popular?api_key=f29f678b3d46fc343965cf9121be8ec2&language=en-US&page=1")
    Call<ListMovieModel> ambilFilmPopuler();

    @GET("movie/top_rated?api_key=f29f678b3d46fc343965cf9121be8ec2&language=en-US&page=1")
    Call<ListMovieModel> ambilTopRate();

    @GET("movie/639/videos?api_key=bfcc1f88effce12d8b802ed55c126aec&language=en-US&page=1")
    Call<ListTrailerModel> ambilTrailer();



}
