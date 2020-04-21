package com.example.lastfm;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfm.ArtistInfo.ArtistInfo;
import com.squareup.picasso.Picasso;


public class ArtistInfoFragment extends Fragment implements ArtistInfoView {
    Context context;
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    private ProgressBar progressBar;
    private static final String TAG_ARTIST_NAME = "name";
    private ArtistInfoPresenter presenter = new ArtistInfoPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_info, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.artistProgressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        tvNameView = view.findViewById(R.id.artist_info_name);
        tvPlayCount = view.findViewById(R.id.artist_info_playcount);
        artistImage = view.findViewById(R.id.big_artist_image);
        tvArtistBio = view.findViewById(R.id.artist_info_bio);
        Bundle bundle = getArguments();
        String name = bundle.getString(TAG_ARTIST_NAME);
        presenter.requestArtist(name);
        presenter.onAttach(this);
        return view;
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
    public void showInfo(String artistName,String bio,String playcount,String imageUrl){
        tvNameView.setText(artistName);
        tvArtistBio.setText(bio);
        tvPlayCount.setText(playcount);
        Picasso.get().load(imageUrl).into(artistImage);
        artistImage.setVisibility(imageUrl != null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void executeOnSuccess(ArtistInfo info){
        presenter.parseInfo(info);
        hideLoading();
    }

    @Override
    public void showError() {
        String text = getString(R.string.request_error_message);
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        hideLoading();
    }
}