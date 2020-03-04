package com.example.lastfm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("name")
    @Expose
    private int name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("playcount")
    @Expose
    private String playcount;

    public int getName() {
        return name;
    }

    public void setName(int id) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getPlaycount() {
        return playcount;
    }

    public void setBody(String playcount) {
        this.playcount = playcount;
    }
}
