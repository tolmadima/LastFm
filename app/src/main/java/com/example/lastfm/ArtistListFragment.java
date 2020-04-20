package com.example.lastfm;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.time.LocalDateTime;
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
    private final String TAG_ARTIST_NAME = "name";

    private ArtistAdapter artistsAdapter;
    private ProgressBar progressBar;
    public static final String APP_PREFERENCES_TIME = "Time";
    public static final String APP_PREFERENCES_DATE = "Date";
    public static final String PREFERENCE = "mysettings";
    private static SharedPreferences pref;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_list,container,false);
        initRecyclerView(view);
        loadDate();
        progressBar = (ProgressBar) view.findViewById(R.id.listProgressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        retrofitRequest();
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrofitRequest();
            }
        });
        saveDate();
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
                            finishProgressBar();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            hideRefreshing();
                            String requestErrorText = getString(R.string.request_error_message);
                            Toast.makeText(getContext(), requestErrorText, Toast.LENGTH_LONG).show();
                            finishProgressBar();
                        }
                    });
    }

    private void finishProgressBar(){
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    private void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showArtists(List<Artist> requestedArtists){
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
    private void saveDate(){
        AndroidThreeTen.init(getContext());
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        Log.i("Tage", String.valueOf(time));
        Log.i("Tage", String.valueOf(today));
        pref = getContext().getSharedPreferences(PREFERENCE, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(APP_PREFERENCES_DATE, String.valueOf(today));
        editor.putString(APP_PREFERENCES_TIME, String.valueOf(time));
        editor.apply();
    }
    private void loadDate(){
        pref = getContext().getSharedPreferences(PREFERENCE,(getContext().MODE_PRIVATE));
        String savedText = pref.getString(APP_PREFERENCES_DATE, "");
        String savedTime = pref.getString(APP_PREFERENCES_TIME, "");
        Log.i("Date","Last time you been here " + savedText);
        Log.i("Date","Last time you been here " + savedTime);
    }

}