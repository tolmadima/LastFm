package com.example.lastfm.artist_info;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistInfo implements Parcelable {

    public ArtistInfo() {

    }

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

    public ArtistInfo(String name, String playcount, String bio, String image){
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

    protected ArtistInfo(Parcel in){
        this.name = in.readString();
        this.playcount = in.readString();
        this.bio = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<ArtistInfo> CREATOR = new Parcelable.Creator<ArtistInfo>() {
        @Override
        public ArtistInfo createFromParcel(Parcel source) {
            return new ArtistInfo(source);
        }
        @Override
        public ArtistInfo[] newArray(int size) {
            return new ArtistInfo[size];
        }
    };

}
