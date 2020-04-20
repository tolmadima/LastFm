package com.example.lastfm;


import java.util.List;

public class Presenter {

    private ArtistListView view;
    private List<Artist> artists;

    public void onClick(int position){
        view.createView(artists.get(position));
    }

    public void onAttach(ArtistListView view){
        this.view = view;
    }

    public void onDetach(){
        view = null;
    }
}
