package com.example.cs3200finalproject.viewmodels;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs3200finalproject.models.MyTotal;
import com.example.cs3200finalproject.models.MyTransaction;
import com.example.cs3200finalproject.models.MyTypes;
import com.google.common.base.Strings;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TotalViewModel extends ViewModel {
    String Transaction_DB_Name = "transactions";
    String Type_DB_Name = "types";
    ObservableArrayList<MyTotal> totals;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    FirebaseFirestore db;
    //FirebaseFirestore transactions_db;

    public TotalViewModel() {
        db = FirebaseFirestore.getInstance();
        //transactions_db = FirebaseFirestore.getInstance();
        saving.setValue(false);
    }

    public MutableLiveData<Boolean> getSaving() { return saving; }

    public ObservableArrayList<MyTotal> getTotals(String uid) {
        if (totals == null) {
            totals = new ObservableArrayList<>();
            loadTotals(uid);
        }
        return totals;
    }

    public void loadTotals(String uid) {
        // get all the types
        ArrayList<MyTypes> types = new ArrayList<>();
        db.collection(Type_DB_Name)
                .whereEqualTo("userId", uid)
                .get()
                .addOnCompleteListener(task -> {
                    boolean test = task.isSuccessful();
                    if(task.isSuccessful()) {
                        types.addAll(task.getResult().toObjects(MyTypes.class));
                    }
                });

        // get all the type names
        ArrayList<String> typeNames = new ArrayList<>();
        for (MyTypes type : types) {
            typeNames.add(type.getType());
        }
        // add the default types
        typeNames.add("Food");
        typeNames.add("Rent");
        typeNames.add("Fun");

        // for each type query the transaction table and add all the values together
        for (String type : typeNames) {
            int total = 0;
            ArrayList<MyTransaction> transactions = new ArrayList<>();
            db.collection(Transaction_DB_Name)
                    .whereEqualTo("userId", uid)
                    .whereEqualTo("type", type)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            transactions.addAll(task.getResult().toObjects(MyTransaction.class));
                        }
                    });
            for (MyTransaction transaction : transactions) {
                total += Integer.parseInt(transaction.getAmount());
            }
            //if (total != 0) {
                MyTotal newTotal = new MyTotal(type, total, uid);
                totals.add(newTotal);
            //}
        }
    }
}
