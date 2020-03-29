package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistScreen extends AppCompatActivity {

    TextView tvNameView;
    TextView tvPlayCount;
    ImageView artistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_screen);
        tvNameView = findViewById(R.id.big_artist_name);
        tvPlayCount = findViewById(R.id.big_artist_playcount);
        Intent intent = getIntent();

        String name = intent.getStringExtra("artistName");
        String playCount = intent.getStringExtra("artistPlayCount");

        tvNameView.setText(name);
        tvPlayCount.setText(playCount);
    }
}