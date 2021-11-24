package com.example.assn3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assn3.adapters.NotesAdapter;
import com.example.assn3.databinding.FragmentMainBinding;
import com.example.assn3.viewmodels.NoteViewModel;
import com.example.assn3.viewmodels.UserViewModel;


public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);

        NoteViewModel noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            binding.logoutButton.setOnClickListener(view -> {
                UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
                viewModel.logout();

                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_mainFragment_to_loginFragment2);
                Log.d("LoginFragment", "signup button pressed");
            });

            binding.fab.setOnClickListener(view -> {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_mainFragment_to_noteFragment);
                noteViewModel.setSelectedNote(null);
            });

            binding.notes.setAdapter(
                    new NotesAdapter(
                            noteViewModel.getNotes(user.uid),
                            note -> {
                                noteViewModel.setSelectedNote(note);
                                NavHostFragment.findNavController(this)
                                        .navigate(R.id.action_mainFragment_to_noteFragment);
                            })
            );
            binding.notes.setLayoutManager(new LinearLayoutManager(getContext()));

        });





        return binding.getRoot();
    }
}
