package com.example.cs3200finalproject.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs3200finalproject.models.MyTransaction;
import com.example.cs3200finalproject.models.MyTypes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TypeViewModel extends ViewModel {
    String DB_NAME = "types";
    String tag = "Mylog TypeViewModel";
    ObservableArrayList<MyTypes> types;
    MutableLiveData<Boolean> saving = new MutableLiveData<>();
    MutableLiveData<MyTypes> selectedType = new MutableLiveData<>();
    FirebaseFirestore db;

    public TypeViewModel() {
        db = FirebaseFirestore.getInstance();
        saving.setValue(false);
    }

    public MutableLiveData<Boolean> getSaving() { return saving; }

    public MutableLiveData<MyTypes> getSelectedType() { return selectedType; }

    public void setSelectedType(MyTypes selectedType) {
        this.selectedType.setValue(selectedType);
    }

    public  void createType(String userID, String type) {
        saving.setValue(true);
        Log.d(tag, "started createType");
        MyTypes newType = new MyTypes(
                userID,
                type,
                System.currentTimeMillis());
        db
                .collection(DB_NAME)
                .document(newType.getUserId() + "_" + newType.getDateCreated())
                .set(newType)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(tag, "task was successful");
                        types.add(newType);
                    } else {
                        Log.d(tag, "task was not successful");
                    }
                    saving.setValue(false);
                });
    }

    public void updateType(MyTypes type, String typeName) {
        saving.setValue(true);
        Log.d(tag, "started update note");
        type.setType(typeName);
        db
                .collection(DB_NAME)
                .document(type.getUserId() + "_" + type.getDateCreated())
                .set(type)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(tag, "task was successful");
                        int i = types.indexOf(type);
                        types.set(i, type);
                        sortList();
                    } else {
                        Log.d(tag, "task was not successful");
                    }
                    saving.setValue(false);
                });
    }

    public void sortList() { types.sort(Comparator.comparing(MyTypes::getDateCreated).reversed()); }

    public void deleteType(MyTypes type) {
        saving.setValue(true);
        db.collection(DB_NAME)
                .document(type.getUserId() + "_"+ type.getDateCreated())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        types.remove(type);
                        sortList();
                    }
                    saving.setValue(false);
                });
    }

    public ObservableArrayList<MyTypes> getTypes(String userId) {
        Log.d(tag, "getTypes Started");
        if (types == null) {
            types = new ObservableArrayList<>();
            loadTypes(userId);
        }
        Log.d(tag, "getTypes finished");
        return types;
    }

    public void loadTypes (String userId) {
        db.collection(DB_NAME)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        types.addAll(task.getResult().toObjects(MyTypes.class));
                        sortList();
                    }
                });
        Log.d(tag, "load types finished");
    }


}
