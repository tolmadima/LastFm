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
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {

        private ArrayList NewsDataSet;
        public Context mContext;
        private int lastPosition = -1;
        public static class MyViewHolder extends RecyclerView.ViewHolder  {
            TextView textHeader;
            TextView textText;
            ImageView imageNews;

            public MyViewHolder(View itemView){
                super (itemView);
                this.imageNews = (ImageView)itemView.findViewById(R.id.news_image);
                this.textHeader = (TextView)itemView.findViewById(R.id.news_header);
                this.textText = (TextView)itemView.findViewById(R.id.news_text);
            }
        }



        public NewsAdapter(Context context, ArrayList news){
            this.NewsDataSet= news;
            mContext=context;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_view, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {

            final TextView textViewName = holder.textHeader;
            final TextView textViewUniverse = holder.textText;
            ImageView imageViewNews = holder.imageNews;
            textViewName.setText(NewsDataSet.get(listPosition).getHeader());
            textViewUniverse.setText(NewsDataSet.get(listPosition).getText());

            String src = NewsDataSet.get(listPosition).getImage();
            Picasso.with(mContext)
                    .load("file:///android_asset/images/"+src+".jpg")
                    .resize(300, 300)
                    .into(imageViewNews);
        }

        @Override
        public int getItemCount() {
            return NewsDataSet.size();
        }

}
