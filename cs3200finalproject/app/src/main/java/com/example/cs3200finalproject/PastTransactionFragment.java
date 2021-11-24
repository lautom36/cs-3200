package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.databinding.FragmentNewTransactionBinding;
import com.example.cs3200finalproject.databinding.FragmentPastTransactionBinding;

public class PastTransactionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPastTransactionBinding binding = FragmentPastTransactionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}