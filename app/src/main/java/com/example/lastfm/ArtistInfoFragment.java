package com.example.lastfm;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lastfm.ArtistInfo.ArtistData;
import com.example.lastfm.ArtistInfo.ArtistInfo;
import com.example.lastfm.ArtistInfo.Bio;
import com.example.lastfm.ArtistInfo.Image;
import com.example.lastfm.ArtistInfo.Stats;
import com.squareup.picasso.Picasso;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistInfoFragment extends Fragment {

    public ArtistInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_info, container, false);
    }
}
