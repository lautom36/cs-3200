package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.adapters.TransactionAdapter;
import com.example.cs3200finalproject.databinding.FragmentNewTransactionBinding;
import com.example.cs3200finalproject.databinding.FragmentPastTransactionBinding;
import com.example.cs3200finalproject.viewmodels.TransactionViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;

public class PastTransactionFragment extends Fragment {
    String tag = " MyLog PastTransactionFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPastTransactionBinding binding = FragmentPastTransactionBinding.inflate(inflater, container, false);

        TransactionViewModel transactionViewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            binding.NewTransactionButton.setOnClickListener(view -> {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_pastTransactionFragment_to_newTransactionFragment);
                transactionViewModel.setSelectedTransaction(null);
            });

            binding.transactions.setAdapter(
                    new TransactionAdapter(
                            transactionViewModel.getTransactions(user.uid),
                            transaction -> {
                                transactionViewModel.setSelectedTransaction(transaction);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_pastTransactionFragment_to_newTransactionFragment);
                            })
            );
            binding.transactions.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        return binding.getRoot();
    }
}