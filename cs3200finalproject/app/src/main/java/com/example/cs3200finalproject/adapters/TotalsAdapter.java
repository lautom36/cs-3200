package com.example.cs3200finalproject.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs3200finalproject.databinding.MainListItemBinding;
import com.example.cs3200finalproject.models.MyTotal;
import com.example.cs3200finalproject.models.MyTransaction;
import com.example.cs3200finalproject.models.MyTypes;

public class TotalsAdapter extends RecyclerView.Adapter<TotalsAdapter.ViewHolder> {
    ObservableArrayList<MyTotal> totals;


    public TotalsAdapter(ObservableArrayList<MyTotal> totals) {
        this.totals = totals;
        totals.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MyTotal>>() {
            @Override
            public void onChanged(ObservableList<MyTotal> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<MyTotal> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<MyTotal> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<MyTotal> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition,toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<MyTotal> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainListItemBinding binding = MainListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int tst = totals.get(position).getTotal();
        holder.getBinding().totalTypeSpent.setText("$" + totals.get(position).getTotal());
        holder.getBinding().typeSpent.setText(totals.get(position).getTotalType());
    }

    @Override
    public int getItemCount() {
        return totals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MainListItemBinding binding;
        public ViewHolder(@NonNull MainListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public MainListItemBinding getBinding() {
            return binding; }
    }

    public static interface OnTotalSelectedListener {
        public void onSelected(MyTotal total);
    }
}
