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
import com.example.cs3200finalproject.databinding.FragmentSigninBinding;
import com.example.cs3200finalproject.viewmodels.UserViewModel;


public class SigninFragment extends Fragment {
    String tag = "Mylog SigninFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "signinfragment started");
        FragmentSigninBinding binding = FragmentSigninBinding.inflate(inflater, container, false);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        // check if already signed in
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {

            if (user != null) {
                Log.d(tag, "user is signed in");
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_signinFragment_to_mainFragment);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            binding.errorMsg2.setText(message);
        });

        // if login button is pressed
        binding.loginButton.setOnClickListener(view -> {
            Log.d(tag, "login button pressed");
            viewModel.signIn(
                    binding.editTextTextEmailAddress.getText().toString(),
                    binding.editTextTextPassword.getText().toString());
        });

        binding.signupButton.setOnClickListener(view -> {
           NavHostFragment.findNavController(this)
                    .navigate(R.id.action_signinFragment_to_signupFragment);
           Log.d(tag, "signup button pressed");
        });



        return binding.getRoot();
    }
}