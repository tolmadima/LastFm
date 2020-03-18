package com.example.lastfm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistsViewHolder> {

    private List<Artists> artistsList = new ArrayList<>();

    public void setItems(List<Artists> artists){
        artistsList.addAll(artists);
        notifyDataSetChanged();
    }

    public void clearItems(){
        artistsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder( ViewGroup parent,
                                              int viewType) {
        View viewArtists = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view, parent, false);
        Log.e("View","Sozdali view dly holdera");
        return new ArtistsViewHolder(viewArtists);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        holder.bind(artistsList.get(position));
        Log.e("Bind", "ZabindiliDatu po pozicii" + position);
    }

    @Override
    public int getItemCount() {
        return artistsList.size();
    }

}
