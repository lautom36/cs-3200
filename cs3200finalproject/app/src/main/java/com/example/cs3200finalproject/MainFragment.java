package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.databinding.FragmentMainBinding;
import com.example.cs3200finalproject.databinding.FragmentSigninBinding;
import com.example.cs3200finalproject.models.User;
import com.example.cs3200finalproject.viewmodels.TransactionViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;

public class MainFragment extends Fragment {
     String tag = "Mylog HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "started HomeFragment");
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);

        TransactionViewModel transactionViewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            binding.logoutButton.setOnClickListener(view -> {
                UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
                viewModel.logout();

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_mainFragment_to_signinFragment);
                Log.d(tag, "signout button pressed");
            });

            binding.PastTransactionButton.setOnClickListener(view -> {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_mainFragment_to_pastTransactionFragment);
                transactionViewModel.setSelectedTransaction(null);
            });


        });

        return binding.getRoot();
    }
}