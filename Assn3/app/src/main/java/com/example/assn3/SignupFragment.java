package com.example.assn3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn3.databinding.FragmentSignupBinding;
import com.example.assn3.viewmodels.UserViewModel;


public class SignupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignupBinding binding = FragmentSignupBinding.inflate(inflater, container, false);

        binding.signupButton.setOnClickListener(view -> {
            // TODO : validate stuff
            UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
            viewModel.signUp(
                    binding.emailAddress.getText().toString(),
                    binding.password.getText().toString()
                    );
        });
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
}