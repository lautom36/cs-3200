package com.example.cs3200finalproject.viewmodels;

import android.media.Image;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs3200finalproject.models.MyTransaction;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;

public class TransactionViewModel extends ViewModel {
    String DB_NAME = "transactions";
    String tag = "MyLog TransactionViewModel";
    ObservableArrayList<MyTransaction> transactions;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<MyTransaction> selectedTransaction = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    FirebaseFirestore db;

    public TransactionViewModel() {
        db = FirebaseFirestore.getInstance();
        saving.setValue(false);
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public MutableLiveData<MyTransaction> getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(MyTransaction selectedTransaction) {
        this.selectedTransaction.setValue(selectedTransaction);
    }

    public void createTransaction(String userId, String amount, String type, String description) {
        errorMessage.postValue("");
        saving.setValue(true);
        Log.d(tag, "started createTransaction");
        MyTransaction transaction = new MyTransaction(
                userId,
                amount,
                type,
                description,
                System.currentTimeMillis()
        );
        db
                .collection(DB_NAME)
                .document(transaction.getUserId() + "_" + transaction.getDateCreated())
                .set(transaction)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(tag, "task was successful");
                        transactions.add(transaction);
                    } else {
                        Log.d(tag, "task was not successful");
                    }
                    saving.setValue(false);
                });
    }

    public void updateTransaction(MyTransaction transaction, String  amount, String description, String type) {
        saving.setValue(true);
        Log.d(tag, "started update note");
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setType(type);
        db
                .collection(DB_NAME)
                .document(transaction.getUserId() + "_" + transaction.getDateCreated())
                .set(transaction)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(tag, "task was successful");
                        int i = transactions.indexOf(transaction);
                        transactions.set(i, transaction);
                        sortList();
                    } else {
                        Log.d(tag, "task was not successful");
                    }
                    saving.setValue(false);
                });
    }

// for storing image
//
// once i store an image ---------------------------------------------------------------------------------------
//

//    public void createTransaction(String userId, Image image, int amount, String type, String description) {
//        errorMessage.postValue("");
//        saving.setValue(true);
//        Log.d(tag, "started createTransaction");
//        MyTransaction transaction = new MyTransaction(
//                userId,
//                image,
//                amount,
//                type,
//                description
//        );
//        db
//                .collection(DB_NAME)
//                .document(transaction.getUserId() + "_" + transaction.getDateCreated())
//                .set(transaction)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.d(tag, "task was successful");
//                        transactions.add(transaction);
//                    } else {
//                        Log.d(tag, "task was not successful");
//                    }
//                    saving.setValue(false);
//                });
//    }

//    public void updateTransaction(MyTransaction transaction, String  amount, String description, String type, Image image) {
//        saving.setValue(true);
//        Log.d(tag, "started update note");
//        transaction.setAmount(amount);
//        transaction.setDescription(description);
//        transaction.setType(type);
//        transaction.setImage(image);
//        db
//                .collection(DB_NAME)
//                .document(transaction.getUserId() + "_" + transaction.getDateCreated())
//                .set(transaction)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.d(tag, "task was successful");
//                        int i = transactions.indexOf(transaction);
//                        transactions.set(i, transaction);
//                        sortList();
//                    } else {
//                        Log.d(tag, "task was not successful");
//                    }
//                    saving.setValue(false);
//                });
//    }

//
// once i store an image ---------------------------------------------------------------------------------------
//

    public void sortList() {transactions.sort(Comparator.comparing(MyTransaction::getDateCreated).reversed());}

    public void deleteTransaction(MyTransaction transaction) {
        saving.setValue(true);
        db.collection(DB_NAME)
                .document(transaction.getUserId() + "_" + transaction.getDateCreated())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        transactions.remove(transaction);
                        sortList();
                    }
                    saving.setValue(false);
                });
    }

    public ObservableArrayList<MyTransaction> getTransactions(String userId) {
        if (transactions == null) {
            transactions = new ObservableArrayList<>();
            loadTransactions(userId);
        }
        return  transactions;
    }

    public void loadTransactions(String userId) {
        //TODO: handle error
        db.collection(DB_NAME)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        transactions.addAll(task.getResult().toObjects(MyTransaction.class));
                        sortList();
                    }

                });
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
