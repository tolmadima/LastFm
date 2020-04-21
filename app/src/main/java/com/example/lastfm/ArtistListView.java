package com.example.lastfm;

import androidx.fragment.app.Fragment;

import java.util.List;

public interface ArtistListView {
    void createView(Artist artist);
    void executeOnSuccess(List<Artist> artists);
    void executeOnError();
}
