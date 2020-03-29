package com.example.lastfm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistsViewHolder> {
    public ArtistAdapter(OnArtistListener onArtistListener) {
        this.mOnArtistListener = onArtistListener;
    }

    private List<Artist> list = new ArrayList<>();
    private OnArtistListener mOnArtistListener;

    public void setItems(List<Artist> artists){
        list.addAll(artists);
        notifyDataSetChanged();
    }

    public void clearItems(){
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder( ViewGroup parent,
                                              int viewType) {
        View viewArtists = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view, parent, false);
        Log.e("View","Created view");
        return new ArtistsViewHolder(viewArtists, mOnArtistListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        holder.bind(list.get(position));
        Log.e("Bind", "Binded data at pos: " + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnArtistListener{
        void onArtistClick(int position);
    }
}
