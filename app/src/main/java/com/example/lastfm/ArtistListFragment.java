package com.example.lastfm;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistListFragment extends Fragment {
    private final int NUMBER_OF_ARTISTS = 40;
    private List<Artist> requestedArtists = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArtistAdapter artistsAdapter = new ArtistAdapter(this::onArtistClick);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.artist_list_fragment,null);
        initRecyclerView(view);
        retrofitRequest();
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofitRequest();
            }
        });
        return view;
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
                        }
                    });
    }

    private void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showArtists(List<Artist> requestedArtists){
        artistsAdapter.addItems(requestedArtists);
    }

    private void initRecyclerView(View view) {
        RecyclerView artistsRecyclerView = view.findViewById(R.id.rv_artists);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        artistsAdapter = new ArtistAdapter(new ArtistAdapter.OnArtistListener() {
            @Override
            public void onArtistClick(int position) {
               ArtistListFragment.this.onArtistClick(position);
            }
        });
        artistsRecyclerView.setAdapter(artistsAdapter);
    }

    private void onArtistClick(int position) {
        String text = requestedArtists.get(position).getArtistName();
    }
}