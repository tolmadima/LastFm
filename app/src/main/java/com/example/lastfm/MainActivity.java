package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    private static final Integer NUMBER_OF_ARTISTS = 40;
    private static final String PARSER_PARAM = "artists";
    private static ArtistAdapter artistsAdapter;
    private static final String TAG = "Retrofit Error tracking";
    private static List<Artists> artistsDataList = new ArrayList<>();
    private static Artists artistData = new Artists();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        retrofitRequest();
    }
    private static List<Artists> retrofitRequest() {
        LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
        Call<JsonObject> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS, APP_ID, "json");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject json = response.body();
                    Gson gson = new Gson();
                    assert response != null;
                    JsonObject parsing = json.getAsJsonObject(PARSER_PARAM);
                    JsonArray artist = parsing.getAsJsonArray("artist");
                    JSONArray artistJson = new JSONArray(gson.toJson(artist));
                    for (int i = 0; i < NUMBER_OF_ARTISTS; i++) {
                        JSONObject artistObj = artistJson.getJSONObject(i);
                        artistData = gson.fromJson(String.valueOf(artistObj), Artists.class);
                        artistsDataList.add(i, artistData);
                    }
                        setArtists(artistsDataList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG,"Error ;(");
            }
        });

        return artistsDataList;
    }

    private static void setArtists(List<Artists> artistData){
        artistsAdapter.setItems(artistData);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
