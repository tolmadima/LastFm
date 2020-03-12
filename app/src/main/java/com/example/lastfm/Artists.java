package com.example.lastfm;

public class Artists {
    Artists(){
    }
    private String name;
//    private String image;
    private String playcount;


    public Artists(String artistName, String artistImage, String artistPlayCount){
        this.name = artistName;
//        this.image = artistImage;
        this.playcount = artistPlayCount;
    }

    public String getArtistName(){
        return name;
    }
//    public String getImage(){
//      return image;
//  }
    public String getPlayCount(){
        return playcount;
    }

    public void setArtistName(String artistname) {
        name = artistname;
    }
    public void setPlayCount(String artistPlayCount) {
        playcount = artistPlayCount;
    }
//  public void setImage(String artistImage) {
//    image = artistImage;
//  }
}
