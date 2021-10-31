package com.example.assn3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.assn3.databinding.FragmentNoteBinding;
import com.example.assn3.models.Note;
import com.example.assn3.viewmodels.NoteViewModel;
import com.example.assn3.viewmodels.UserViewModel;

import java.util.Locale;

public class NoteFragment extends Fragment {
    private boolean isSaving = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNoteBinding binding = FragmentNoteBinding.inflate(inflater, container, false);
        NoteViewModel noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        Note selectedNote = noteViewModel.getSelectedNote().getValue();
        if (selectedNote != null) {
            binding.noteTitle.setText(selectedNote.getTitle());
            binding.noteBody.setText(selectedNote.getBody());
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.deleteButton.setOnClickListener(view -> {
                noteViewModel.deleteNote(selectedNote);
            });
        }


        noteViewModel.getSaving().observe(getViewLifecycleOwner(), saving -> {
            if(!isSaving && saving) {
                isSaving = true;
            }
            else if (isSaving && !saving) {
                // go back to main frag
                noteViewModel.sortList();
                NavHostFragment.findNavController(this).navigateUp();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) return;
            binding.saveButton.setOnClickListener(view -> {
                binding.saveButton.setEnabled(false); // turn off button
                binding.newNoteErrorMessage.setText("");

                if (binding.noteTitle.getText().toString().equals("")) {
                    binding.newNoteErrorMessage.setText("Title cannot be blank");
                    binding.saveButton.setEnabled(true);
                } else {
                    binding.newNoteErrorMessage.setText("");
                    if (selectedNote == null) {
                        noteViewModel.createNote(
                                binding.noteTitle.getText().toString(),
                                binding.noteBody.getText().toString(),
                                user.uid
                        );
                    } else {
                        noteViewModel.updateNote(
                                selectedNote,
                                binding.noteTitle.getText().toString(),
                                binding.noteBody.getText().toString()
                        );
                    }
                }
            });
        });


        return binding.getRoot();
    }
}
