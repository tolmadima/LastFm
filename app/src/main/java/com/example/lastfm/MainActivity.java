package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArtistAdapter.OnArtistListener {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    public static final Integer NUMBER_OF_ARTISTS = 40;
    private static final String REQUEST_TYPE = "json";
    private static final String PARSER_PARAM = "artists";
    private final String TAG = "Retrofit Error tracking";
    public static List<Artist> requestedArtists = new ArrayList<>();

    ArtistAdapter artistsAdapter = new ArtistAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        retrofitRequest();
        final Button button = findViewById(R.id.next_screen_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
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
    private List<Artist> retrofitRequest() {
        LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
        Call<List<Artist>> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS, APP_ID, REQUEST_TYPE);
        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                try {
                    List<Artist> list = response.body();
                    System.out.println(list);
                    setArtists(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Log.e(TAG,"Error ;(");
            }
        });

        return requestedArtists;
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

    @Override
    public void onArtistClick(int position) {
        Log.i("Click", String.valueOf(position));
    }
}
