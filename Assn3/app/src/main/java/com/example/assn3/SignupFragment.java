package com.example.assn3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn3.databinding.FragmentSignupBinding;
import com.example.assn3.viewmodels.UserViewModel;


public class SignupFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignupBinding binding = FragmentSignupBinding.inflate(inflater, container, false);
        Log.d("SignupFragment", "here");
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            binding.errorMsg.setText(message);
        });

        binding.button1.setOnClickListener(view -> {
            Log.d("SignupFragment", "signup button pressed");
            // TODO : validate stuff

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