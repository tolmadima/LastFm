package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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
import java.util.Collection;

public class MainActivity extends AppCompatActivity {



    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";
    private static Integer NumberOfArtists = 10;


    private ArtistAdapter artistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("DefaultLocale") String url = String.format("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=%d&api_key=%s&format=json",NumberOfArtists,APP_ID);

        initRecyclerView();
        loadArtists();
        TextView textView = findViewById(R.id.asynctask_view);
        new GetTopArtistTask(textView).execute(url);
    }

    private static class GetTopArtistTask extends AsyncTask<String, Void, ArrayList<String>> {
        private TextView tvTop;

        GetTopArtistTask(TextView textView) {
            this.tvTop = textView;
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            final ArrayList<String> listofartists = new ArrayList<String>();
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
                JSONArray artist = (JSONArray) main.get("artist");
                if (artist != null) {
                    for (int i=0;i<artist.length();i++){
                        listofartists.add(artist.getString(i));
                        System.out.println(artist.getString(i));
                    }
                }




                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return listofartists;
        }

        @Override
        protected void onPostExecute(ArrayList<String> listofartists) {
            super.onPostExecute(listofartists);
        }
    }

        private void loadArtists(){
        ArrayList<ArtistsData> artists = getArtists();
        artistsAdapter.setItems(artists);
    }

        private ArrayList<ArtistsData> getArtists(){
           return ;
        }

    private void initRecyclerView() {
        RecyclerView artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistsAdapter = new ArtistAdapter();
        artistsRecyclerView.setAdapter(artistsAdapter);
    }


}
