package com.example.lastfm;

import com.example.lastfm.artist_info.ArtistInfo;
import com.example.lastfm.artist_info.dto.ArtistInfoDto;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArtistInfoPresenter {

    private ArtistInfoView view;
    private LastFMClient client;
    private boolean loading;
    private String loadParam;
    private ArtistInfo artistInfo;

    ArtistInfoPresenter(){
        client = ServiceGenerator.getInstance().getLastFMClient();
        loading = true;
        if(view != null) {
            view.setLoading(loading);
        }
        loadData(loadParam);
    }

    public void loadData(String name){
        loadParam = name;
        client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ArtistInfoDto, ArtistInfo>() {
                    @Override
                    public ArtistInfo apply(ArtistInfoDto artistInfoDto) throws Exception {
                        ArtistInfo artistInfo = new ArtistMapper().map(artistInfoDto);
                        return artistInfo;
                    }
                })
                .subscribe(new SingleObserver<ArtistInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ArtistInfo info) {
                        artistInfo = info;
                        loading = false;
                        if(view != null) {
                            view.setLoading(loading);
                            view.showInfo(artistInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading = false;
                        if(view != null) {
                            view.showError();
                        }
                    }
                });
    }

    public void onAttach(ArtistInfoView view){
        this.view = view;
        this.view.setLoading(loading);
        this.view.showInfo(artistInfo);
    }

    public void onDetach(){
        view = null;
    }

    public ArtistInfo getSavedData(){
        return artistInfo;
    }

    public void setSavedData(ArtistInfo artistInfo){
        this.artistInfo = artistInfo;
    }
}
