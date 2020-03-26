package com.example.lastfm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvName;
    private TextView tvPlayCount;
    private ImageView ivImage;

    ArtistAdapter.OnArtistListener onArtistListener;

    public ArtistsViewHolder(View itemView, ArtistAdapter.OnArtistListener onArtistListener){
        super (itemView);
//            ivImage = itemView.findViewById(R.id.artist_image);
        tvName = itemView.findViewById(R.id.artist_name);
        tvPlayCount = itemView.findViewById(R.id.artist_playcount);
        this.onArtistListener = onArtistListener;

        itemView.setOnClickListener(this);
    }

    public void bind(Artist artist) {
        tvName.setText(artist.getArtistName());
        tvPlayCount.setText(artist.getPlayCount());
//            String newsPhotoUrl = artists.getImage();
//            Picasso.get().load(newsPhotoUrl).into(ivImage);
//
//            ivImage.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        onArtistListener.onArtistClick(getAdapterPosition());
    }
}