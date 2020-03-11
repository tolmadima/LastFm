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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistsViewHolder> {

    private List<ArtistsData> artistsList = new ArrayList<>();


    public void setItems(List<ArtistsData> artists){
        artistsList.addAll(artists);
        notifyDataSetChanged();
    }



    public void clearItems(){
        artistsList.clear();
        notifyDataSetChanged();
    }



    class ArtistsViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvName;
        private TextView tvPlayCount;
        private ImageView ivImage;



        public ArtistsViewHolder(View itemView){
            super (itemView);
            ivImage = itemView.findViewById(R.id.artist_image);
            tvName = itemView.findViewById(R.id.artist_name);
            tvPlayCount = itemView.findViewById(R.id.artist_playcount);
        }



        public void bind(ArtistsData artists) {
            tvName.setText(artists.getArtistName());
            tvPlayCount.setText(artists.getPlayCount());
//            String newsPhotoUrl = artists.getImage();
//            Picasso.get().load(newsPhotoUrl).into(ivImage);
//
//            ivImage.setVisibility(newsPhotoUrl != null ? View.VISIBLE : View.GONE);
        }


    }



    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder( ViewGroup parent,
                                              int viewType) {
        View viewArtists = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view, parent, false);
        return new ArtistsViewHolder(viewArtists);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        holder.bind(artistsList.get(position));
    }


    @Override
    public int getItemCount() {
        return artistsList.size();
    }

}
