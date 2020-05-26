package com.example.lastfm;


import android.widget.ProgressBar;

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
    private boolean loading;
    private boolean refreshing;

    ArtistListPresenter(){
        client = ServiceGenerator.getInstance().getLastFMClient();
        loading = true;
        if(view != null) {
            view.setLoading(loading);
        }
        loadData();
    }

    public void onClickArtist(int position){
        if(view != null) {
            view.openArtist(artists.get(position));
        }
    }

    public void onAttach(ArtistListView view){
        this.view = view;
        this.view.setRefreshing(refreshing);
        this.view.setLoading(loading);
        this.view.showData(artists);
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
                        loading = false;
                        refreshing = false;
                        if(view != null) {
                            view.showData(artists);
                            view.setLoading(loading);
                            view.setRefreshing(refreshing);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loading = false;
                        refreshing = false;
                        if(view != null) {
                            view.showError();
                            view.setLoading(loading);
                            view.setRefreshing(refreshing);
                        }
                    }
                });
    }

    public void onDetach(){
        view = null;
    }

    public void onRefresh(){
        refreshing = true;
        loadData();
    }

}
