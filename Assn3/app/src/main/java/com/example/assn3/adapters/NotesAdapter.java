package com.example.assn3.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assn3.databinding.NoteListItemBinding;
import com.example.assn3.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    ObservableArrayList<Note> notes;
    OnNoteSelectedListener listener;

    public static interface OnNoteSelectedListener {
        public void onSelected(Note note);
    }

    public NotesAdapter(ObservableArrayList<Note> notes, OnNoteSelectedListener listener) {
        this.notes = notes;
        this.listener = listener;
        notes.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Note>>() {

            @Override
            public void onChanged(ObservableList<Note> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Note> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Note> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Note> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Note> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart,itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteListItemBinding binding = NoteListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date date = new Date(notes.get(position).getDateCreated());
        DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
        String formattedDate = df.format(date);

        holder.getBinding().noteListTitle.setText(notes.get(position).getTitle());
        holder.getBinding().noteListDate.setText(formattedDate);
        holder.itemView.setOnClickListener(view -> {
            this.listener.onSelected(notes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NoteListItemBinding binding;
        public ViewHolder(@NonNull NoteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public NoteListItemBinding getBinding() {
            return binding;
        }
    }
}
