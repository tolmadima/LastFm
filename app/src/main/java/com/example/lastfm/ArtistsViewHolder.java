package com.example.lastfm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ArtistsViewHolder extends RecyclerView.ViewHolder  {
    private TextView tvName;
    private TextView tvPlayCount;
    private ImageView ivImage;

    public ArtistsViewHolder(View itemView){
        super (itemView);
//            ivImage = itemView.findViewById(R.id.artist_image);
        tvName = itemView.findViewById(R.id.artist_name);
        tvPlayCount = itemView.findViewById(R.id.artist_playcount);
    }

    public void bind(Artists artists) {
        tvName.setText(artists.getArtistName());
        tvPlayCount.setText(artists.getPlayCount());
//            String newsPhotoUrl = artists.getImage();
//            Picasso.get().load(newsPhotoUrl).into(ivImage);
//
//            ivImage.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);
    }
}