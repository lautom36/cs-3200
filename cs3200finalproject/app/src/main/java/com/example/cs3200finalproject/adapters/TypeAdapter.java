package com.example.cs3200finalproject.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs3200finalproject.databinding.TypeListItemBinding;
import com.example.cs3200finalproject.models.MyTypes;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    ObservableArrayList<MyTypes> types;
    OnTypeSelectedListener listener;

    public TypeAdapter(ObservableArrayList<MyTypes> types, OnTypeSelectedListener listener) {
        this.types = types;
        this.listener = listener;
        types.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MyTypes>>() {
            @Override
            public void onChanged(ObservableList<MyTypes> sender) { notifyDataSetChanged(); }

            @Override
            public void onItemRangeChanged(ObservableList<MyTypes> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<MyTypes> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<MyTypes> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition,toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<MyTypes> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TypeListItemBinding binding = TypeListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.ViewHolder holder, int position) {
        holder.getBinding().typeListName.setText(types.get(position).getType());
        holder.itemView.setOnClickListener(view -> {
            this.listener.onSelected(types.get(position));
        });
    }

    @Override
    public int getItemCount() { return types.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TypeListItemBinding binding;
        public ViewHolder(@NonNull TypeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public TypeListItemBinding getBinding() { return binding; }
    }


    public static interface OnTypeSelectedListener {
        public void onSelected(MyTypes type);
    }
}
