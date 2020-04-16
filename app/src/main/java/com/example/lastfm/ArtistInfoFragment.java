package com.example.lastfm;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastfm.artist_info.ArtistData;
import com.example.lastfm.artist_info.ArtistInfo;
import com.example.lastfm.artist_info.Bio;
import com.example.lastfm.artist_info.Image;
import com.example.lastfm.artist_info.Stats;
import com.squareup.picasso.Picasso;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class ArtistInfoFragment extends Fragment {
    private TextView tvNameView;
    private TextView tvPlayCount;
    private ImageView artistImage;
    private TextView tvArtistBio;
    //Вызывается массив картинок размер которых варьируется от 0-4
    //0 - самое маленькое разрешение
    //4 - самое большое разрешение
    private static final int PICTURE_SIZE = 3;
    private ProgressBar progressBar;
    private static final String TAG_ARTIST_NAME = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_artist_info, container, false);
        tvNameView = view.findViewById(R.id.artist_info_name);
        tvPlayCount = view.findViewById(R.id.artist_info_playcount);
        artistImage = view.findViewById(R.id.big_artist_image);
        tvArtistBio = view.findViewById(R.id.artist_info_bio);
        Bundle bundle = getArguments();
        String name = bundle.getString(TAG_ARTIST_NAME);
        requestArtist(name);
        return view;
    }

    private void requestArtist(String name){

        LastFMClient client = ServiceGenerator.getInstance().getLastFMClient();
        client.getArtistInfo(name)
                .subscribeOn(Schedulers.io())
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
        ArtistData data = info.getArtist();
        String artistName = data.getName();
        Bio artistBio = data.getBio();
        Stats artistStat = data.getStats();
        String playcount = artistStat.getPlaycount();
        String bio = artistBio.getContent();
        Image url = data.getImage().get(PICTURE_SIZE);
        String imageUrl = url.getText();
        tvNameView.setText(artistName);
        tvArtistBio.setText(bio);
        tvPlayCount.setText(playcount);
        Picasso.get().load(imageUrl).into(artistImage);
        artistImage.setVisibility(imageUrl != null ? View.VISIBLE : View.GONE);
    }
}