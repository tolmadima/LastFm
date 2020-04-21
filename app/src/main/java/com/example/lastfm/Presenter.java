package com.example.lastfm;


import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Presenter {

    private final int NUMBER_OF_ARTISTS = 40;

    private ArtistListView view;
    private List<Artist> artists;

    public void onClick(int position){
        view.createView(artists.get(position));
    }

    public void onAttach(ArtistListView view){
        this.view = view;
    }

    public void retrofitRequest(ArtistListFragment fragment) {
        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtists(NUMBER_OF_ARTISTS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Artist>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<Artist> info) {
                        artists = info;
                        fragment.executeOnSuccess(artists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        fragment.executeOnError();
                    }
                });
    }

    public void onDetach(){
        view = null;
    }

}
