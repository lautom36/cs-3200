package com.example.assn3.viewmodels;

import android.util.Log;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.assn3.models.Note;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;

public class NoteViewModel extends ViewModel {
    ObservableArrayList<Note> notes;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
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
        errorMessage.postValue("");
        saving.setValue(true);
        Log.d("NoteViewModel", "stared createNote");
        Note note = new Note(
                userid,
                title,
                body,
                System.currentTimeMillis()
        );
        db
                .collection("notes")
                .document(note.getUserId() + "_" + note.getDateCreated())
                .set(note)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("NoteViewModel", "task was successful");
                        notes.add(note);
                    } else {
                        Log.d("NoteViewModel", "task was not successful");

                    }
                    saving.setValue(false);
        });
    }

    public void updateNote(Note note, String title, String body) {
        saving.setValue(true);
        Log.d("NoteViewModel", "stared updateNote");
        note.setTitle(title);
        note.setBody(body);
        db
                .collection("notes")
                .document(note.getUserId() + "_" + note.getDateCreated())
                .set(note)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("NoteViewModel", "task was successful");
                        int i = notes.indexOf(note);
                        notes.set(i, note);
                        notes.sort(Comparator.comparing(Note::getDateCreated).reversed());
                    } else {
                        Log.d("NoteViewModel", "task was not successful");
                    }
                    saving.setValue(false);
                });
    }

    public void sortList() {
        notes.sort(Comparator.comparing(Note::getDateCreated).reversed());
    }

    public void deleteNote(Note note) {
        saving.setValue(true);
        db.collection("notes")
                .document(note.getUserId() + "_" + note.getDateCreated())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes.remove(note);
                        notes.sort(Comparator.comparing(Note::getDateCreated).reversed());
                    }
                    saving.setValue(false);
                });
    }

    public ObservableArrayList<Note> getNotes(String userId) {
        if (notes == null) {
            notes = new ObservableArrayList<>();
            loadNotes(userId);
        }
        return notes;
    }

    private void loadNotes (String userId) {
        db.collection("notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes.addAll(task.getResult().toObjects(Note.class));
                        notes.sort(Comparator.comparing(Note::getDateCreated).reversed());


                    } else {
                        // handle error
                    }
                });
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
