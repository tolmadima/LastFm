
package com.example.lastfm.ArtistInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ArtistInfoDto {

    @SerializedName("artist")
    @Expose
    private ArtistDataDto artist;

    public ArtistDataDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDataDto artist) {
        this.artist = artist;
    }

}


