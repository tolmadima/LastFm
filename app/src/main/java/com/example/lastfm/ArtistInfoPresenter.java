package com.example.lastfm;

import com.example.lastfm.ArtistInfo.ArtistData;
import com.example.lastfm.ArtistInfo.ArtistInfo;
import com.example.lastfm.ArtistInfo.Bio;
import com.example.lastfm.ArtistInfo.Image;
import com.example.lastfm.ArtistInfo.Stats;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistInfoPresenter {

    //Вызывается массив картинок размер которых варьируется от 0-4
    //0 - самое маленькое разрешение
    //4 - самое большое разрешение
    private static final int PICTURE_SIZE = 3;

    private ArtistInfoView view;

    public void requestArtist(String name){
        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArtistInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ArtistInfo info) {
                        view.executeOnSuccess(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                    }
                });
    }

    public void parseInfo(ArtistInfo info) {
        ArtistData data = info.getArtist();
        String artistName = data.getName();
        Bio artistBio = data.getBio();
        Stats artistStat = data.getStats();
        String playcount = artistStat.getPlaycount();
        String bio = artistBio.getContent();
        Image url = data.getImage().get(PICTURE_SIZE);
        String imageUrl = url.getText();
        view.showInfo(artistName,bio,playcount,imageUrl);
    }

    public void onAttach(ArtistInfoView view){
        this.view = view;
    }

    public void onDetach(){
        view = null;
    }

}
