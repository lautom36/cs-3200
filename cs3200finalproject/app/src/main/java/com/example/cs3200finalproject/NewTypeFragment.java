package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs3200finalproject.databinding.FragmentNewTypeBinding;
import com.example.cs3200finalproject.models.MyTypes;
import com.example.cs3200finalproject.viewmodels.TypeViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;

public class NewTypeFragment extends Fragment {
    private boolean isSaving = false;
    private String type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNewTypeBinding binding = FragmentNewTypeBinding.inflate(inflater, container, false);
        TypeViewModel typeViewModel = new ViewModelProvider(getActivity()).get(TypeViewModel.class);
        UserViewModel userViewModel= new ViewModelProvider(getActivity()).get(UserViewModel.class);

        MyTypes selectedType = typeViewModel.getSelectedType().getValue();

        if (selectedType != null) {
            binding.typeEditText.setText(selectedType.getType());
            binding.newTypeDeleteButton.setVisibility(View.VISIBLE);
            binding.newTypeDeleteButton.setOnClickListener(view -> {
                typeViewModel.deleteType(selectedType);
            });
        } else {
            binding.newTypeDeleteButton.setEnabled(false);
            binding.newTypeDeleteButton.setVisibility(View.GONE);
        }
            userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                if (user == null) return;
                binding.newTypeSaveButton.setOnClickListener(view -> {
                    binding.newTypeSaveButton.setEnabled(false);
                    binding.newTypeErrorMsg.setText("");

                    if (binding.typeEditText.getText().toString().equals("")) {
                        binding.newTypeErrorMsg.setText("Type cannot be blank");
                        binding.newTypeSaveButton.setEnabled(true);
                    } else {
                        binding.newTypeErrorMsg.setText("");
                        if (selectedType == null) {
                            typeViewModel.createType(
                                    user.uid,
                                    binding.typeEditText.getText().toString()
                            );
                        } else {
                            typeViewModel.updateType(
                                    selectedType,
                                    binding.typeEditText.getText().toString()
                            );
                        }
                    }
                });
            });

        typeViewModel.getSaving().observe(getViewLifecycleOwner(), saving -> {
            if (!isSaving && saving ) {
                isSaving = true;
            }
            else if (isSaving && !saving) {
                typeViewModel.sortList();
                NavHostFragment.findNavController(this).navigateUp();
            }
        });


        return binding.getRoot();
    }
}