package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Service;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    private static final Integer NUMBER_OF_ARTISTS = 10;
    private static final String PARSER_PARAM = "artists";
    private static ArtistAdapter artistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint("DefaultLocale") String url = String.format("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=%d&api_key=%s&format=json", NUMBER_OF_ARTISTS,APP_ID);
        initRecyclerView();
        new GetTopArtistTask().execute(url);
    }

    private static class GetTopArtistTask extends AsyncTask<String, Void, List<Artists>> {

        Artists artistData = new Artists();
        List<Artists> artistsDataList = new ArrayList<>();

        @Override
        protected List<Artists> doInBackground(String... strings) {

            try {
                LastFMClient lastFMClient = ServiceGenerator.createService(LastFMClient.class);
                Call<List<Artists>> call = lastFMClient.numberArtists(10,"b4ab3bf82dcb495e182e04cfc1f12b7b","json");
                List<Artists> task = call.execute().body();
                System.out.println(task);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return artistsDataList;
        }

        @Override
        protected void onPostExecute(List<Artists> artistsDataList) {
            super.onPostExecute(artistsDataList);
            setArtists(artistsDataList);
        }
    }

    public static void setArtists(List<Artists> artistData){
        artistsAdapter.setItems(artistData);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
