package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.adapters.TypeAdapter;
import com.example.cs3200finalproject.databinding.FragmentAllTypesBinding;
import com.example.cs3200finalproject.viewmodels.TypeViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;


public class AllTypesFragment extends Fragment {
    String tag = "MyLog AllTypesFragment";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag, "started AllTypesFragment");
        FragmentAllTypesBinding binding = FragmentAllTypesBinding.inflate(inflater, container, false);

        TypeViewModel typeViewModel = new ViewModelProvider(getActivity()).get(TypeViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            binding.newTypeButton.setOnClickListener(view -> {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_allTypesFragment_to_newTypeFragment);
                typeViewModel.setSelectedType(null);
            });

            binding.typesRecyclerView.setAdapter(
                    new TypeAdapter(
                            typeViewModel.getTransactions(user.uid),
                            type -> {
                                typeViewModel.setSelectedType(type);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_allTypesFragment_to_newTypeFragment);
                            }
                    )
            );
            binding.typesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        return binding.getRoot();

    }
}