package com.example.lastfm;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistMapper implements Parcelable {

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

    private String name;
    private String playcount;
    private String bio;
    private String image;

    public ArtistMapper(String name, String playcount, String bio, String image){
        this.name = name;
        this.playcount = playcount;
        this.bio = bio;
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.playcount);
        dest.writeString(this.bio);
        dest.writeString(this.image);
    }

    protected ArtistMapper(Parcel in){
        this.name = in.readString();
        this.playcount = in.readString();
        this.bio = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<ArtistMapper> CREATOR = new Parcelable.Creator<ArtistMapper>() {
        @Override
        public ArtistMapper createFromParcel(Parcel source) {
            return new ArtistMapper(source);
        }
        @Override
        public ArtistMapper[] newArray(int size) {
            return new ArtistMapper[size];
        }
    };

}
