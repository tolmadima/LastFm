package com.example.lastfm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class ArtistListFragment extends Fragment implements ArtistListView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private final int NUMBER_OF_ARTISTS = 20;
    private List<Artist> requestedArtists;
    private static final String TAG_ARTIST_NAME = "name";

    private ArtistAdapter artistsAdapter;
    private ProgressBar progressBar;
    private ArtistListPresenter presenter = new ArtistListPresenter();
    private final String TAG_ARTISTS = "Artist list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_list,container,false);
        initRecyclerView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.list_progress_bar);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
        presenter.onAttach(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void setLoading(boolean loading) {
        int visibility;
        if (loading) {
            visibility = ProgressBar.VISIBLE;
        }else{
            visibility = ProgressBar.INVISIBLE;
        }
        progressBar.setVisibility(visibility);
    }

    public static ArtistListFragment getInstance(){
        return new ArtistListFragment();
    }

    private void initRecyclerView(View view) {
        RecyclerView artistsRecyclerView = view.findViewById(R.id.rv_artists);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        artistsAdapter = new ArtistAdapter(new ArtistAdapter.OnArtistListener() {
            @Override
            public void onArtistClick(int position) {
               ArtistListFragment.this.onArtistClick(position);
            }
        });
        artistsRecyclerView.setAdapter(artistsAdapter);
    }

    private void onArtistClick(int position) {
        presenter.onClickArtist(position);
    }

    @Override
    public void openArtist(Artist artist) {
        Fragment fragment = ArtistInfoFragment.getInstance(
                artist.getArtistName());
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void showError(){
        String requestErrorText = getString(R.string.request_error_message);
        Toast.makeText(getContext(), requestErrorText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(List<Artist> artists){
        artistsAdapter.addItems(artists);
    }

}