package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    private static final String TAG = "Retrofit Error tracking";

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
                Log.i(TAG,"Using Client is ok");
                Call<JsonObject> call = lastFMClient.numberArtists(NUMBER_OF_ARTISTS,APP_ID,"json");
                Log.i(TAG,"Call Sucessfull");
                Response<JsonObject> task = call.execute();
                Log.i(TAG, "Body = 0");
                JsonObject response = task.body();
                Log.e(TAG, "Body contain : "+new Gson().toJson(task.body()) );
                System.out.println(response);
                Log.i(TAG, "Task is ok");
                Gson gson = new Gson();
                assert response != null;
                JsonObject parsing = response.getAsJsonObject(PARSER_PARAM);
                JsonArray artist = parsing.getAsJsonArray("artist");
                JSONArray artistJson = new JSONArray(gson.toJson(artist));
                for(int i=0; i<NUMBER_OF_ARTISTS; i++) {
                    JSONObject artistObj = artistJson.getJSONObject(i);
                    artistData = gson.fromJson(String.valueOf(artistObj), Artists.class);
                    artistsDataList.add(i, artistData);
                }
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
