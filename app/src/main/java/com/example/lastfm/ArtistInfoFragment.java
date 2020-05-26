package com.example.lastfm;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfm.artist_info.ArtistInfo;
import com.squareup.picasso.Picasso;

public class ArtistInfoFragment extends Fragment implements ArtistInfoView {
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    private ProgressBar progressBar;
    private static final String TAG_ARTIST_NAME = "name";
    private ArtistInfoPresenter presenter = new ArtistInfoPresenter();
    private final String TAG_ARTIST_INFO = "Artist info";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_info, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.artist_progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        tvNameView = view.findViewById(R.id.artist_info_name);
        tvPlayCount = view.findViewById(R.id.artist_info_playcount);
        artistImage = view.findViewById(R.id.artist_info_image);
        tvArtistBio = view.findViewById(R.id.artist_info_bio);
        Bundle bundle = getArguments();
        String name = bundle.getString(TAG_ARTIST_NAME);
        presenter.loadData(name);
        presenter.onAttach(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_ARTIST_INFO, presenter.getSavedData());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setSavedData(savedInstanceState.getParcelable(TAG_ARTIST_INFO));
        }
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    public static ArtistInfoFragment getInstance(String artistName){
        ArtistInfoFragment fragment = new ArtistInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_ARTIST_NAME,artistName);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void hideLoading(){
        progressBar.setVisibility(ProgressBar.GONE);
    }


    @Override
    public void showInfo(ArtistInfo info) {
        tvNameView.setText(info.getName());
        tvArtistBio.setText(info.getBio());
        tvPlayCount.setText(info.getPlaycount());
        Picasso.get().load(info.getImage()).into(artistImage);
        artistImage.setVisibility(info.getImage() != null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError() {
        String text = getString(R.string.request_error_message);
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        hideLoading();
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
}