package com.example.assn2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn2.databinding.FragmentNotSquishBinding;
import com.example.assn2.databinding.FragmentSquishBinding;


public class NotSquishFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNotSquishBinding binding = FragmentNotSquishBinding.inflate(inflater, container, false);

        binding.yesButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_notSquishFragment_to_legFragment);
        });
        return binding.getRoot();
    }
}