package com.example.assn3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn3.databinding.FragmentLoginBinding;
import com.example.assn3.models.User;
import com.example.assn3.viewmodels.UserViewModel;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // check if already signed in
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_loginFragment2_to_mainFragment);
            }
        });

        // if login button is pressed
        binding.loginButton.setOnClickListener(view -> {
            // TODO: validate the strings
            viewModel.signIn(
                    binding.editTextTextEmailAddress.getText().toString(),
                    binding.editTextTextPassword.getText().toString());
        });

        // if logged in go to the main screen
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_loginFragment2_to_mainFragment);
            }
        });

        binding.signupButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment2_to_signupFragment);
            Log.d("LoginFragment", "signup button pressed");
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}