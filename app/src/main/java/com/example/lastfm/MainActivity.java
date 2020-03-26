package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    public static final Integer NUMBER_OF_ARTISTS = 40;
    public static final String PARSER_PARAM = "artists";
    private final String TAG = "Retrofit Error tracking";
    public static List<Artist> requestedArtists = new ArrayList<>();

    ArtistAdapter artistsAdapter = new ArtistAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        retrofitRequest();
    }
    private void retrofitRequest() {
                    LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
                    Observable<List<Artist>> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS, APP_ID, "json").subscribeOn(Schedulers.computation());
                    call.subscribe(new Observer<List<Artist>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Artist> value) {
                            requestedArtists = value;
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Log.i("Rx", "request is finished");
                        }
    });
        setArtists(requestedArtists);
    }

    private void setArtists(List<Artist> requestedArtists){
        artistsAdapter.setItems(requestedArtists);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
