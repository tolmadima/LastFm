package com.example.lastfm;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        initFragmentLast();
    }

    private void initFragmentLast(){
        transaction = manager.beginTransaction();
        transaction.add(R.id.list_fragment, new ArtistListFragment()).commit();
    }

    @Override
    public void onUpdateInfo(String name) {

    }
}
