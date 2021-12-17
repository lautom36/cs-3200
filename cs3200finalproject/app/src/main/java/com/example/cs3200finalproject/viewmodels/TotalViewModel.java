package com.example.cs3200finalproject.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs3200finalproject.models.MyTotal;
import com.example.cs3200finalproject.models.MyTransaction;
import com.example.cs3200finalproject.models.MyTypes;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TotalViewModel extends ViewModel {
    String tag = "Mylog TotalViewModel";
    String Transaction_DB_Name = "transactions";
    String Type_DB_Name = "types";
    ObservableArrayList<MyTotal> totals;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    FirebaseFirestore db;
    ObservableArrayList<MyTransaction> transactions;
    ObservableArrayList<MyTypes> types;
    String[] defaultTypes = new String[]{"Food", "Rent", "Fun"};
    //FirebaseFirestore transactions_db;

    public TotalViewModel() {
        Log.d(tag, "TotalViewModel created");
        db = FirebaseFirestore.getInstance();
        //transactions_db = FirebaseFirestore.getInstance();
        saving.setValue(false);
    }

    public MutableLiveData<Boolean> getSaving() { return saving; }

    public ObservableArrayList<MyTotal> getTotals(String uid) {
        //if (totals == null) {
            totals = new ObservableArrayList<>();
            loadTotals(uid);
        //}
        return totals;
    }

    public void loadTotals(String uid) {
        Log.d(tag, "loadTotals started");
        // get all the types
        ArrayList<MyTypes> types = new ArrayList<>();
        ArrayList<MyTransaction> transactions = new ArrayList<>();

        // get all the types
        db.collection(Type_DB_Name)
                .whereEqualTo("userId", uid)
                .get()
                .addOnCompleteListener(task -> {
                    boolean test = task.isSuccessful();
                    if(task.isSuccessful()) {
                        Log.d(tag, "loadTotals types query successful");
                        types.addAll(task.getResult().toObjects(MyTypes.class));

                        // get all the type names
                        ArrayList<String> typeNames = new ArrayList<>();
                        for (MyTypes type : types) { typeNames.add(type.getType()); }
                        // add the default types
                        typeNames.add("Food");
                        typeNames.add("Rent");
                        typeNames.add("Fun");


                        // get all the transactions
                        db.collection(Transaction_DB_Name)
                                .whereEqualTo("userId", uid)
                                .get()
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        Log.d(tag, "loadTotals transactions query successful");
                                        transactions.addAll(task2.getResult().toObjects(MyTransaction.class));
                                        int asdf = task2.getResult().size();
                                    }
                                    // add totals together
                                    Log.d(tag, "started adding totals");
                                    for (String type : typeNames) {
                                        int total = 0;

                                        for (MyTransaction transaction : transactions) {
                                            if (transaction.getType().equals(type)) {
                                                String amount = transaction.getAmount();
                                                total += Integer.parseInt(amount);
                                            }
                                        }
                                        MyTotal newTotal = new MyTotal(type, total, uid);
                                        totals.add(newTotal);
                                    }
                                });
                    }
                });
    }

}
