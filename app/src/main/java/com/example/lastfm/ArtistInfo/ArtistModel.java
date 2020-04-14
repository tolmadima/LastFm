package com.example.lastfm.ArtistInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistModel {

    public ArtistModel(String name, String playcount, String bio, String image) {
        this.name = name;
        this.playcount = playcount;
        this.bio = bio;
        this.image = image;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
