package com.example.cs3200finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.cs3200finalproject.databinding.ActivityMainBinding;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Mylog MainActivity", "main activity started");
        super.onCreate(savedInstanceState);

        // get rid of title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // ad mob things
        MobileAds.initialize(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}