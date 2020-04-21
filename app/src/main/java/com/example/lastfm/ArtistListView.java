package com.example.lastfm;

import androidx.fragment.app.Fragment;

import java.util.List;

public interface ArtistListView {
    void openArtist(Artist artist);
    void showList(List<Artist> artists);
    void showError();
}
