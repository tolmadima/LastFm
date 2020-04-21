package com.example.lastfm;


import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistListPresenter {

    private final int NUMBER_OF_ARTISTS = 40;

    private ArtistListView view;
    private List<Artist> artists;
    private LastFMClient client;

    ArtistListPresenter(){
        client = ServiceGenerator.getInstance().getLastFMClient();
        loadData();
    }

    public void onClickArtist(int position){
        view.openArtist(artists.get(position));
    }

    public void onAttach(ArtistListView view){
        this.view = view;
        this.view.showList(artists);
    }

    public void loadData() {
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
                        if(view != null) {
                            view.showList(artists);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError();
                    }
                });
    }

    public void onDetach(){
        view = null;
    }

    public void onRefresh(){
        loadData();
    }

}
