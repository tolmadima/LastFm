package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.example.lastfm.NewsData;
import com.example.lastfm.NewsAdapter;


import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private ImageView newsImage;
    private TextView textHeader;
    private TextView textDescription;


    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initRecyclerView();

        loadNews();
    }

        private void loadNews(){
            Collection<NewsData> news = getNews();
            newsAdapter.setItems(news);
        }


        private Collection<NewsData> getNews(){
           return Arrays.asList(
                   new NewsData("RandomNewsTitle","https://www.w3schools.com/w3images/lights.jpg","LUL")
           );
        }
    private void initRecyclerView() {
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter();
        newsRecyclerView.setAdapter(newsAdapter);
    }







}