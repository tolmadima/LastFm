package com.example.lastfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lastfm.artist_info.ArtistInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final int NUMBER_OF_ARTISTS = 40;
    private List<Artist> requestedArtists = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    ArtistAdapter artistsAdapter = new ArtistAdapter(this::onArtistClick);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        retrofitRequest();
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofitRequest();
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
                        public void onSuccess(List<Artist> info) {
                            requestedArtists = info;
                            showArtists(requestedArtists);
                            hideRefreshing();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            hideRefreshing();
                            String requestErrorText = getString(R.string.request_error_message);
                            Toast.makeText(MainActivity.this, requestErrorText, Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showArtists(List<Artist> requestedArtists){
        artistsAdapter.addItems(requestedArtists);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.rv_artists);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter(new ArtistAdapter.OnArtistListener() {
            @Override
            public void onArtistClick(int position) {
                MainActivity.this.onArtistClick(position);
            }
        });
        artistsRecyclerView.setAdapter(artistsAdapter);
    }

    private void onArtistClick(int position) {
        Intent intent = new Intent(MainActivity.this,ArtistInfoActivity.class);
        intent.putExtra("artistName",requestedArtists.get(position).getArtistName());
        startActivity(intent);
    }
}