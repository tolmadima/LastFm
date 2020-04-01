package com.example.lastfm;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMClient {

    @GET("?method=chart.gettopartists")
    Single<List<Artist>> getArtists(@Query("limit")int numberArtists, @Query("api_key")String key, @Query("format")String format);
    @GET("?method=artist.getinfo")
    Single<List<ArtistInfo>> getArtistInfo(@Query("artist")String name,@Query("api_key")String key,@Query("format")String format);
}



