package com.example.cs3200finalproject.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs3200finalproject.databinding.TransactionListItemBinding;
import com.example.cs3200finalproject.models.MyTransaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    ObservableArrayList<MyTransaction> transactions;
    OnTransactionSelectedListener listener;



    public TransactionAdapter(ObservableArrayList<MyTransaction> transactions, OnTransactionSelectedListener listener) {
        this.transactions = transactions;
        this.listener = listener;
        transactions.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MyTransaction>>() {

            @Override
            public void onChanged(ObservableList<MyTransaction> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<MyTransaction> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<MyTransaction> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<MyTransaction> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<MyTransaction> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart,itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TransactionListItemBinding binding = TransactionListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date date = new Date(transactions.get(position).getDateCreated());
        DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
        String formattedDate = df.format(date);


        holder.getBinding().transListAmount.setText("$ " + transactions.get(position).getAmount());
        holder.getBinding().transListDate.setText(formattedDate);
        holder.getBinding().transListType.setText(transactions.get(position).getType());
        holder.itemView.setOnClickListener(view -> {
            this.listener.onSelected(transactions.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TransactionListItemBinding binding;
        public ViewHolder(@NonNull TransactionListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public TransactionListItemBinding getBinding() { return binding; }

    }


    public static interface OnTransactionSelectedListener {
        public void onSelected(MyTransaction transaction);
    }
}
