package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final int NUMBER_OF_ARTISTS = 40;
    public static List<Artist> requestedArtists = new ArrayList<>();

    ArtistAdapter artistsAdapter = new ArtistAdapter(this::onArtistClick);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        retrofitRequest();
        final Button button = findViewById(R.id.next_screen_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.next_screen_button:
                        Intent intent = new Intent(MainActivity.this, ArtistScreen.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void retrofitRequest() {
        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtists(NUMBER_OF_ARTISTS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Artist>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(List<Artist> value) {
                            requestedArtists = value;
                            setArtists(requestedArtists);
                        }

                        @Override
                        public void onError(Throwable e) {
                            String requestErrorText = getString(R.string.request_error_message);
                            Toast.makeText(MainActivity.this, requestErrorText, Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private void setArtists(List<Artist> requestedArtists){
        artistsAdapter.setItems(requestedArtists);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter(new ArtistAdapter.OnArtistListener() {
            @Override
            public void onArtistClick(int position) {
                onArtistClick(position);
            }
        });
        artistsRecyclerView.setAdapter(artistsAdapter);
    }

    private void onArtistClick(int position) {
        Intent intent = new Intent(this,ArtistScreen.class);
        intent.putExtra("artistName",requestedArtists.get(position).getArtistName());
        startActivity(intent);
    }
}