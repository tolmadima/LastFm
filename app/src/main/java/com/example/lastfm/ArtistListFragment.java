package com.example.lastfm;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArtistListFragment extends Fragment {
    public ArtistListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_list, container, false);
        RecyclerView rvArtists =view.findViewById(R.id.rv_artists);
        rvArtists.setLayoutManager(new LinearLayoutManager(rvArtists.getContext()));
        return view;
    }
}

