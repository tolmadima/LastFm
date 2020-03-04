package com.example.lastfm;

public class ArtistsData {
    private String ArtistName;
    private String Image;
    private String PlayCount;


    ArtistsData(String ArtistsData, String Image, String PlayCount){
        this.ArtistName = ArtistsData;
        this.Image = Image;
        this.PlayCount = PlayCount;
    }

    public String getArtistName(){return ArtistName;}
    public String getImage(){return Image;}
    public String getPlayCount(){return PlayCount;}

    public void setArtistName(String artistname) {ArtistName = artistname;}
    public void setPlayCount(String playcount) {PlayCount = playcount;}
    public void setImage(String image) {Image = image;}
}
