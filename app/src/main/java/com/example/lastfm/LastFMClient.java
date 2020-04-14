package com.example.lastfm;

import com.example.lastfm.ArtistInfo.ArtistInfoDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMClient {

    @GET("?method=chart.gettopartists")
    Single<List<Artist>> getArtists(@Query("limit")int numberArtists);
    @GET("?method=artist.getinfo")
    Single<ArtistInfoDto> getArtistInfo(@Query("artist")String name);
}



