package com.example.assn3.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assn3.models.Note;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NoteViewModel extends ViewModel {
    ObservableArrayList<Note> notes;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    FirebaseFirestore db;

    public NoteViewModel() {
        db = FirebaseFirestore.getInstance();
        saving.setValue(false);
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public MutableLiveData<Note> getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote.setValue(selectedNote);
    }

    public void createNote(String title, String body, String userid) {
        Log.d("NoteViewModel", "stared createNote");
        Note note = new Note(
                title,
                body,
                userid,
                System.currentTimeMillis()
        );
        db
                .collection("notes")
                .document(note.getUserId() + "_" + note.getDateCreated())
                .set(note)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("NoteViewModel", "task was successful");
                    } else {
                        Log.d("NoteViewModel", "task was not successful");
                    }
        });
    }

    public void updateNote(Note note) {

    }

    public void deleteNote(Note note) {

    }

    public void getNotes() {

    }
}
