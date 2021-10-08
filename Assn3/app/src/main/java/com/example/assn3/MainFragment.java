package com.example.assn3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn3.databinding.FragmentMainBinding;
import com.example.assn3.models.User;
import com.example.assn3.repositories.UserRepository;
import com.example.assn3.viewmodels.UserViewModel;


public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.logoutButton.setOnClickListener(view -> {
            UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
            viewModel.logout();

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_mainFragment_to_loginFragment2);
            Log.d("LoginFragment", "signup button pressed");
        });

        return binding.getRoot();
    }
}