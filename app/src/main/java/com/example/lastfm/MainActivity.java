package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.JsonObject;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        Log.e("Thread", Thread.currentThread().getName());
        LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
                    Observable<List<Artist>> call = lastFMClient.getArtists(NUMBER_OF_ARTISTS, APP_ID, "json").subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
                    call.subscribe(new Observer<List<Artist>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
    
                        }

                        @Override
                        public void onNext(List<Artist> value) {
                            Log.e("Thread", Thread.currentThread().getName());
                            requestedArtists = value;
                            setArtists(requestedArtists);
                            System.out.println("data setted");

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

//Vse chto ne sdelal
//Trello