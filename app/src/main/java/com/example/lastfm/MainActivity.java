package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    private static final Integer NumberOfArtists = 10;

    private static ArtistAdapter artistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("DefaultLocale") String url = String.format("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=%d&api_key=%s&format=json",NumberOfArtists,APP_ID);

        initRecyclerView();
        new GetTopArtistTask(artistsAdapter).execute(url);
    }

    private static class GetTopArtistTask extends AsyncTask<String, Void, List<ArtistsData>> {

        GetTopArtistTask(ArtistAdapter artistAdapter) { }
        ArtistsData artistData = new ArtistsData();
        List<ArtistsData> lsArtist = new ArrayList<>();

        @Override
        protected List<ArtistsData> doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(builder.toString());
                JSONObject main = topLevel.getJSONObject("artists");
                JSONArray artist = main.getJSONArray("artist");
                Gson gson = new Gson();
                int Number = NumberOfArtists;
                for(int i=0; i<Number; i++){
                    JSONObject artistObj = artist.getJSONObject(i);
                    artistData = gson.fromJson(String.valueOf(artistObj), ArtistsData.class);
                    lsArtist.add(i,artistData);
                }

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return lsArtist;
        }

        @Override
        protected void onPostExecute(List<ArtistsData> lsArtist) {
            super.onPostExecute(lsArtist);
            loadArtists(lsArtist);
        }
    }

    public static void loadArtists(List<ArtistsData> artistData){
        artistsAdapter.setItems(artistData);
    }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }
}
