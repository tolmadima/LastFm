package com.example.lastfm;

import com.example.lastfm.ArtistInfo.ArtistInfo;

public interface ArtistInfoView {
    void executeOnSuccess(ArtistInfo artists);
    void showError();
    void showInfo(String artistName,String bio,String playcount,String imageUrl);
}
