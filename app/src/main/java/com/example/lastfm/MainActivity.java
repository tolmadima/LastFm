package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    public static final Integer NUMBER_OF_ARTISTS = 40;
    private static final String REQUEST_TYPE = "json";
    private static final String PARSER_PARAM = "artists";
    private final String TAG = "Retrofit Error tracking";
    public static List<Artists> artistsDataList = new ArrayList<>();

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
    private List<Artists> retrofitRequest() {
        LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
        Call<List<Artists>> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS, APP_ID, REQUEST_TYPE);
        call.enqueue(new Callback<List<Artists>>() {
            @Override
            public void onResponse(Call<List<Artists>> call, Response<List<Artists>> response) {
                try {
                    List<Artists> list = response.body();
                    System.out.println(list);
                    setArtists(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<Artists>> call, Throwable t) {
                Log.e(TAG,"Error ;(");
            }
        });

        return artistsDataList;
    }

    private void setArtists(List<Artists> artistData){
        artistsAdapter.setItems(artistData);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
