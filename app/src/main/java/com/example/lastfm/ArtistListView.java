package com.example.lastfm;

import java.util.List;

public interface ArtistListView {
    void openArtist(Artist artist);
    void showData(List<Artist> artists);
    void showError();
    void setLoading(boolean loading);
    void setRefreshing(boolean refreshing);
}
