package com.example.lastfm;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

        private List<NewsData> newsList = new ArrayList<>();


        public void setItems(Collection<NewsData> news){
            newsList.addAll(news);
            notifyDataSetChanged();
        }



        public void clearItems(){
            newsList.clear();
            notifyDataSetChanged();
        }



        class NewsViewHolder extends RecyclerView.ViewHolder  {
           private TextView textHeader;
           private TextView textDescription;
           private ImageView imageNews;



            public NewsViewHolder(View itemView){
                super (itemView);
                imageNews = itemView.findViewById(R.id.news_image);
                textHeader = itemView.findViewById(R.id.news_header);
                textDescription = itemView.findViewById(R.id.news_description);
            }



            public void bind(NewsData news) {
                textHeader.setText(news.getHeader());
                textDescription.setText(news.getDescription());
                String newsPhotoUrl = news.getImage();
                Picasso.get().load(newsPhotoUrl).into(imageNews);

                imageNews.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);
            }


        }



        private NewsAdapter newsAdapter;


        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_view, parent, false);
            NewsViewHolder newsViewHolder = new NewsViewHolder(view);
            return newsViewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
