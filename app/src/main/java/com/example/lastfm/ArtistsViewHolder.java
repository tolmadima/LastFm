package com.example.lastfm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private TextView tvName;
    private TextView tvPlayCount;
    private ImageView ivImage;
    private ArtistAdapter.OnArtistListener mOnArtistListener;

    public ArtistsViewHolder(View itemView, ArtistAdapter.OnArtistListener onArtistListener){
        super (itemView);
//            ivImage = itemView.findViewById(R.id.artist_image);
        tvName = itemView.findViewById(R.id.tv_artist_name);
        tvPlayCount = itemView.findViewById(R.id.tv_artist_playcount);
        mOnArtistListener = onArtistListener;

        itemView.setOnClickListener(this);
    }

    public void bind(Artist artist) {
        tvName.setText(artist.getArtistName());
        tvPlayCount.setText(artist.getPlayCount());
    }

    @Override
    public void onClick(View v) {
        mOnArtistListener.onArtistClick(getAdapterPosition());
    }
}