package com.example.lastfm;

import com.example.lastfm.artist_info.ArtistInfo;

public interface ArtistInfoView {
    void showInfo(ArtistInfo info);
    void showError();
    void setLoading(boolean loading);
}
