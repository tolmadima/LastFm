package com.example.lastfm;

public class ArtistsData {
    private String ArtistName;
    private String Image;
    private String PlayCount;


    public ArtistsData(String artistsName, String image, String playCount){
        this.ArtistName = artistsName;
        this.Image = image;
        this.PlayCount = playCount;
    }

    public String getArtistName(){return ArtistName;}
    public String getImage(){return Image;}
    public String getPlayCount(){return PlayCount;}

    public void setArtistName(String artistname) {ArtistName = artistname;}
    public void setPlayCount(String playcount) {PlayCount = playcount;}
    public void setImage(String image) {Image = image;}
}
