package com.example.lastfm;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    Artist() {
    }

    private String name;
    private String playcount;

    public Artist(String name, String playCount) {
        this.name = name;
        this.playcount = playCount;
    }

    protected Artist(Parcel in) {
        name = in.readString();
        playcount = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(playcount);
    }
}