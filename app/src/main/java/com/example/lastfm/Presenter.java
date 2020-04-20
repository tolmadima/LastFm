package com.example.lastfm;

import androidx.fragment.app.Fragment;

import java.util.List;

public class Presenter {
    public void onClick(int position, List<Artist> requestedArtists){
        Fragment fragment = ArtistInfoFragment.getInstance(
                requestedArtists.get(position).getArtistName());
        ArtistListFragment.getInstance().createView(fragment);
    }
}
