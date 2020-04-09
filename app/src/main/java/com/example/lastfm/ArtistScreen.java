package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfm.ArtistInfo.ArtistData;
import com.example.lastfm.ArtistInfo.ArtistInfo;
import com.example.lastfm.ArtistInfo.Bio;
import com.example.lastfm.ArtistInfo.Image;
import com.example.lastfm.ArtistInfo.Stats;
import com.squareup.picasso.Picasso;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class ArtistScreen extends AppCompatActivity {
    Context context;
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    private final int picSize = 3;

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
         LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
         Single<ArtistInfo> call = client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        call.subscribe(new SingleObserver<ArtistInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(ArtistInfo value) {
                showArtist(value);
            }

            @Override
            public void onError(Throwable e) {
                String text = getString(R.string.request_error_get_info);
                Toast.makeText(ArtistScreen.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showArtist(ArtistInfo value){
        ArtistData info = value.getArtist();
        String artistName = info.getName();
        Bio artistBio = info.getBio();
        Stats artistStat = info.getStats();
        String playcount = artistStat.getPlaycount();
        String bio = artistBio.getContent();
        Image url = info.getImage().get(picSize);
        String imageUrl = url.getText();
        tvNameView.setText(artistName);
        tvArtistBio.setText(bio);
        tvPlayCount.setText(playcount);
        Picasso.get().load(imageUrl).into(artistImage);
        artistImage.setVisibility(imageUrl != null ? View.VISIBLE : View.GONE);
    }
}