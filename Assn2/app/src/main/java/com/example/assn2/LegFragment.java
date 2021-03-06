package com.example.assn2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn2.databinding.FragmentLegBinding;


public class LegFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLegBinding binding = FragmentLegBinding.inflate(inflater, container, false);

        binding.noButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_legFragment_to_sizeFragment);
        });

        binding.yesButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_legFragment_to_squishFragment);
        });
        return binding.getRoot();
    }
}