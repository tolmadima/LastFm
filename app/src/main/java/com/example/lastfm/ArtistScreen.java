package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.example.lastfm.MainActivity.REQUEST_TYPE;

public class ArtistScreen extends AppCompatActivity {

    TextView tvNameView;
    TextView tvPlayCount;
    ImageView artistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_screen);
        tvNameView = findViewById(R.id.big_artist_name);
        tvPlayCount = findViewById(R.id.big_artist_playcount);
        Intent intent = getIntent();
        String name = intent.getStringExtra("artistName");
        LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
        Single<ArtistInfo> call = lastFMClient.getArtistInfo(name, "b4ab3bf82dcb495e182e04cfc1f12b7b", REQUEST_TYPE).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        call.subscribe(new SingleObserver<ArtistInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(ArtistInfo value) {
                Artist artistInfo = value.getArtist();
                String artistName = artistInfo.getArtistName();
                tvNameView.setText(artistName);
                String playCount = artistInfo.getPlayCount();
                tvPlayCount.setText(playCount);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}