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
import com.example.lastfm.artist_info.dto.ArtistInfoDto;
import com.squareup.picasso.Picasso;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArtistInfoFragment extends Fragment {
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    private ProgressBar progressBar;
    private static final String TAG_ARTIST_NAME = "name";
    private ArtistInfo artistInfo;
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
        requestArtist(name);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_ARTIST_INFO, artistInfo);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            artistInfo = savedInstanceState.getParcelable(TAG_ARTIST_INFO);
        }
    }

    private void requestArtist(String name){

        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArtistInfoDto, ArtistInfo>() {

                    @Override
                    public ArtistInfo apply(ArtistInfoDto artistInfoDto) throws Exception {
                        ArtistInfo artistInfo = new ArtistMapper().map(artistInfoDto);
                        return artistInfo;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArtistInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(ArtistInfo info) {
                showInfo(info);
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                String text = getString(R.string.request_error_message);
                Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });
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

    private void showInfo(ArtistInfo info){
        tvNameView.setText(info.getName());
        tvArtistBio.setText(info.getBio());
        tvPlayCount.setText(info.getPlaycount());
        Picasso.get().load(info.getImage()).into(artistImage);
        artistImage.setVisibility(info.getImage() != null ? View.VISIBLE : View.GONE);
    }
}