package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    public static final Integer NUMBER_OF_ARTISTS = 40;
    public static final String PARSER_PARAM = "artists";
    private final String TAG = "Retrofit Error tracking";
    public static List<Artist> artistsDataList = new ArrayList<>();

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
        Call<List<Artist>> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS, APP_ID, "json");
        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                try {
                    artistsDataList = response.body();
                    setArtists(artistsDataList);
                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setArtists(List<Artist> artistData){
        artistsAdapter.setItems(artistData);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
