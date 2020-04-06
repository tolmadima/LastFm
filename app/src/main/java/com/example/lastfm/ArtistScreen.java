package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.jar.Attributes;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.example.lastfm.MainActivity.REQUEST_TYPE;

public class ArtistScreen extends AppCompatActivity {
    Context context;
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    private String toastError = "Ошибка получения артиста";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_screen);
         tvNameView = findViewById(R.id.big_artist_name);
         tvPlayCount = findViewById(R.id.big_artist_playcount);
         artistImage = findViewById(R.id.big_artist_image);
         tvArtistBio = findViewById(R.id.big_artist_bio);
         Intent intent = getIntent();
         String name = intent.getStringExtra("artistName");
         LastFMClient client = MainActivity.generateServiceSingleton.getLastFMClient();
         Single<ArtistInfo> call = client
                .getArtistInfo(name, "b4ab3bf82dcb495e182e04cfc1f12b7b", REQUEST_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        call.subscribe(new SingleObserver<ArtistInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(ArtistInfo value) {
                ArtistData info = value.getArtist();
                String artistName = info.getName();
                Bio artistBio = info.getBio();
                Stats artistStat = info.getStats();
                String playcount = artistStat.getPlaycount();
                String bio = artistBio.getContent();
                Image url = info.getImage().get(3);
                String imageUrl = url.getText();
                loadArtist(artistName,playcount,bio,imageUrl);
            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(context, toastError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadArtist(String name, String bio, String playcount, String imageUrl){
        tvNameView.setText(name);
        tvArtistBio.setText(bio);
        tvPlayCount.setText(playcount);
        Picasso.get().load(imageUrl).into(artistImage);
        artistImage.setVisibility(imageUrl != null ? View.VISIBLE : View.GONE);
    }
}