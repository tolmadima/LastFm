package com.example.lastfm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistListFragment extends Fragment {
    private final int NUMBER_OF_ARTISTS = 20;
    private List<Artist> requestedArtists;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String TAG_ARTIST_NAME = "name";

    private ArtistAdapter artistsAdapter;
    private ProgressBar progressBar;
    private final String TAG_ARTISTS = "Artist list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_list,container,false);
        initRecyclerView(view);
        Log.i("Request","ViewCreated");
        requestedArtists = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.list_progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        retrofitRequest();
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofitRequest();
                hideRefreshing();
            }
        });
        return view;
    }

    private void retrofitRequest() {
        Log.i("Request","Executing request");
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
                            hideProgressBar();
                            hideRefreshing();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            hideRefreshing();
                            String requestErrorText = getString(R.string.request_error_message);
                            Toast.makeText(getContext(), requestErrorText, Toast.LENGTH_LONG).show();
                            hideProgressBar();
                        }
                    });
    }

    private void hideProgressBar(){
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    private void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_ARTISTS, (ArrayList<? extends Parcelable>) requestedArtists);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            requestedArtists = savedInstanceState.getParcelable(TAG_ARTISTS);
        }
    }

    private void showArtists(List<Artist> requestedArtists){
        artistsAdapter.clearItems();
        artistsAdapter.addItems(requestedArtists);
    }

    public static ArtistListFragment getInstance(){
        return new ArtistListFragment();
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
        Fragment fragment = ArtistInfoFragment.getInstance(
                requestedArtists.get(position).getArtistName());
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}