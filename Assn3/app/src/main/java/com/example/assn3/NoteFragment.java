package com.example.assn3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assn3.databinding.FragmentNoteBinding;
import com.example.assn3.viewmodels.NoteViewModel;
import com.example.assn3.viewmodels.UserViewModel;

import java.util.Locale;

public class NoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNoteBinding binding = FragmentNoteBinding.inflate(inflater, container, false);
        NoteViewModel noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) return;
            binding.saveButton.setOnClickListener(view -> {
                binding.saveButton.setEnabled(false); // turn off button
                noteViewModel.createNote(
                        binding.noteTitle.getText().toString(),
                        binding.noteBody.getText().toString(),
                        user.uid
                        );
            });
        });


        return binding.getRoot();
    }
}
