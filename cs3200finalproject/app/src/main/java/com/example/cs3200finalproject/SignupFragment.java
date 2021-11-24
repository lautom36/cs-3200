package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.databinding.FragmentNewTransactionBinding;
import com.example.cs3200finalproject.databinding.FragmentSignupBinding;
import com.example.cs3200finalproject.viewmodels.UserViewModel;


public class SignupFragment extends Fragment {
    String tag = "SignupFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignupBinding binding = FragmentSignupBinding.inflate(inflater, container, false);
        Log.d(tag, "created");
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // handles error message
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            binding.errorMsg.setText(message);
        });

        binding.button1.setOnClickListener(view -> {
            Log.d(tag, "signup button pressed");

            viewModel.signUp(
                    binding.emailAddress.getText().toString(),
                    binding.password.getText().toString(),
                    binding.confirmEmailAdress.getText().toString(),
                    binding.confirmPassword.getText().toString()
            );
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_signupFragment_to_mainFragment);
            }
        });

        return binding.getRoot();
    }
}