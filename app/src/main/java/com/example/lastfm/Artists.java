package com.example.lastfm;

public class Artists {
    Artists() {
    }

    private String name;
    private String playcount;


    public Artists(String artistName, String artistPlayCount) {
        this.name = artistName;
        this.playcount = artistPlayCount;
    }

    public String getArtistName() {
        return name;
    }

    public String getPlayCount() {
        return playcount;
    }

    public void setArtistName(String artistname) {
        name = artistname;
    }

    public void setPlayCount(String artistPlayCount) {
        playcount = artistPlayCount;
    }
}