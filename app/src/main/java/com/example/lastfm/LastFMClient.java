package com.example.lastfm;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LastFMClient {

    @GET("?method=chart.gettopartists")
    Call<List<Artists>> numberArtists(@Query("limit")int numberArtists, @Query("api_key")String key, @Query("format")String format);
}



