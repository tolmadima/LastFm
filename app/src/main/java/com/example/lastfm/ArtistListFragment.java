package com.example.lastfm;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class ArtistListFragment extends Fragment implements ArtistListView {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArtistAdapter artistsAdapter;
    private ProgressBar progressBar;
    private ListPresenter presenter = new ListPresenter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_list,container,false);
        initRecyclerView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.listProgressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        artistsRequest();
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                artistsRequest();
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

    public void finishProgressBar(){
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
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
        presenter.onClick(position);
    }

    @Override
    public void createView(Artist artist) {
        Fragment fragment = ArtistInfoFragment.getInstance(
                artist.getArtistName());
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void artistsRequest() {
        presenter.retrofitRequest();
    }

    @Override
    public void executeOnError(){
        hideRefreshing();
        String requestErrorText = getString(R.string.request_error_message);
        Toast.makeText(getContext(), requestErrorText, Toast.LENGTH_LONG).show();
        finishProgressBar();
    }

    @Override
    public void executeOnSuccess(List<Artist> artists){
        showArtists(artists);
        hideRefreshing();
        finishProgressBar();
    }

    private void showArtists(List<Artist> requestedArtists){
        artistsAdapter.addItems(requestedArtists);
    }

}