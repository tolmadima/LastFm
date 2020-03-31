package com.example.lastfm;

public class Artist {
    Artist() {
    }

    private String name;
    private String playcount;
    private String imageurl;

    public Artist(String name, String playCount,String imageUrl) {
        this.name = name;
        this.playcount = playCount;
        this.imageurl = imageUrl;
    }

    public String getArtistName() {
        return name;
    }

    public String getPlayCount() {
        return playcount;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setArtistName(String artistname) {
        name = artistname;
    }

    public void setPlayCount(String artistPlayCount) {
        playcount = artistPlayCount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageurl = imageUrl;
    }
}