package com.example.lastfm;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMClient {

    @GET("?method=chart.gettopartists")
    Call<List<Artist>> numberArtists(@Query("limit")int numberArtists, @Query("api_key")String key, @Query("format")String format);
}



