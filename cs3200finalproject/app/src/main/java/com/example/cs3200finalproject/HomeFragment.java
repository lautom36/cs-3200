package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.databinding.FragmentSigninBinding;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSigninBinding binding = FragmentSigninBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}