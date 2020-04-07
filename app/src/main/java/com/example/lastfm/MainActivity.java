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
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ArtistAdapter.OnArtistListener {
    public static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    public static final Integer NUMBER_OF_ARTISTS = 40;
    public static final String PARSER_PARAM = "artists";
    public static final String REQUEST_TYPE = "json";
    public static List<Artist> artists = new ArrayList<>();
    private String toastError = "Ошибка получения списка артистов";
    Context context;

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
                        Intent intent = new Intent(MainActivity.this, ArtistInfoActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void retrofitRequest() {
        LastFMClient client = generateServiceSingleton.getLastFMClient();
                    Single<List<Artist>> call = client
                            .getArtists(NUMBER_OF_ARTISTS, APP_ID, REQUEST_TYPE)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                    call.subscribe(new SingleObserver<List<Artist>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(List<Artist> value) {
                            artists = value;
                            setArtists(artists);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, toastError, Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private void setArtists(List<Artist> requestedArtists){
        artistsAdapter.addItems(requestedArtists);
    }

    private void initRecyclerView() {
        RecyclerView rvArtists = findViewById(R.id.rvArtists);
        rvArtists.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter(this);
        rvArtists.setAdapter(artistsAdapter);
    }

    @Override
    public void onArtistClick(int position) {
        Intent intent = new Intent(this, ArtistInfoActivity.class);
        Context context = getApplicationContext();
        Toast.makeText(context,"position = " + position, Toast.LENGTH_LONG).show();
        intent.putExtra("artistName", artists.get(position).getArtistName());
        startActivity(intent);
    }

    public static final class generateServiceSingleton {
        private static LastFMClient lastFMClient;
        public static LastFMClient getLastFMClient(){
            if (lastFMClient == null) {
            lastFMClient = ServiceGenerator.createService(LastFMClient.class);
            }
            return lastFMClient;
        }
    }
}