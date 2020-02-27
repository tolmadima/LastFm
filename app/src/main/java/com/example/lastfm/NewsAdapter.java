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
           private TextView tvHeader;
           private TextView tvDescription;
           private ImageView ivNews;



            public NewsViewHolder(View itemView){
                super (itemView);
                ivNews = itemView.findViewById(R.id.news_image);
                tvHeader = itemView.findViewById(R.id.news_header);
                tvDescription = itemView.findViewById(R.id.news_description);
            }



            public void bind(NewsData news) {
                tvHeader.setText(news.getHeader());
                tvDescription.setText(news.getDescription());
                String newsPhotoUrl = news.getImage();
                Picasso.get().load(newsPhotoUrl).into(ivNews);

                ivNews.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);
            }


        }



        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder( ViewGroup parent,
                                               int viewType) {
            View viewNews = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_view, parent, false);
            NewsViewHolder newsViewHolder = new NewsViewHolder(viewNews);
            return newsViewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            holder.bind(newsList.get(position));
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
