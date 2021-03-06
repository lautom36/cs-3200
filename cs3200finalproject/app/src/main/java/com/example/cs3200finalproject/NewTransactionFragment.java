package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cs3200finalproject.adapters.TypeAdapter;
import com.example.cs3200finalproject.databinding.FragmentNewTransactionBinding;
import com.example.cs3200finalproject.databinding.FragmentSigninBinding;
import com.example.cs3200finalproject.models.MyTransaction;
import com.example.cs3200finalproject.models.MyTypes;
import com.example.cs3200finalproject.viewmodels.TransactionViewModel;
import com.example.cs3200finalproject.viewmodels.TypeViewModel;
import com.example.cs3200finalproject.viewmodels.UserViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewTransactionFragment extends Fragment {
    private boolean isSaving = false;
    private String type;
    String tag = "MyLog NewTransactionFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNewTransactionBinding binding = FragmentNewTransactionBinding.inflate(inflater, container, false);

        TransactionViewModel transactionViewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        TypeViewModel typeViewModel = new ViewModelProvider(getActivity())
                .get(TypeViewModel.class);

        MyTransaction selectedTransaction = transactionViewModel.getSelectedTransaction().getValue();

        // if a existing note is clicked, add values to the screen
        if (selectedTransaction != null) {
            binding.TransactionType.setPrompt(selectedTransaction.getType());
            binding.TransactionAmount.setText(selectedTransaction.getAmount());
            binding.newTransactionDescripton.setText(selectedTransaction.getDescription());
            binding.DeleteTransactionButton.setVisibility(View.VISIBLE);
            binding.DeleteTransactionButton.setOnClickListener(view -> {
                transactionViewModel.deleteTransaction(selectedTransaction);
            });
        } else {
            binding.DeleteTransactionButton.setEnabled(false);
            binding.DeleteTransactionButton.setVisibility(View.GONE);
        }



        transactionViewModel.getSaving().observe(getViewLifecycleOwner(), saving -> {
            if (!isSaving && saving) {
                isSaving = true;
            }
            else if (isSaving && !saving) {
                transactionViewModel.sortList();
                NavHostFragment.findNavController(this).navigateUp();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) return;
            binding.newTransactionSaveButton.setOnClickListener(view -> {
                binding.newTransactionSaveButton.setEnabled(false);
                binding.newTransactionErrorMessage.setText("");

                if (binding.TransactionAmount.getText().toString().equals("")) {
                    binding.newTransactionErrorMessage.setText("Amount cannot be blank");
                    binding.newTransactionSaveButton.setEnabled(true);
                } else {
                    binding.newTransactionErrorMessage.setText("");
                    if (selectedTransaction == null) {
                        transactionViewModel.createTransaction(
                                user.uid,
                                binding.TransactionAmount.getText().toString(),
                                binding.TransactionType.getSelectedItem().toString(),
                                binding.newTransactionDescripton.getText().toString()
                        );
                    } else {
                        transactionViewModel.updateTransaction(
                                selectedTransaction,
                                binding.TransactionAmount.getText().toString(),
                                binding.newTransactionDescripton.getText().toString(),
                                binding.TransactionType.getSelectedItem().toString()
                        );
                    }

                }
            });
        });


        userViewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
            if (user == null) return;

            //get the spinner from the xml.
            Spinner dropdown = binding.TransactionType;
            //create a list of items for the spinner.
            ArrayList<String> list = new ArrayList<>();
            list.add("Food");
            list.add("Rent");
            list.add("Fun");

            ObservableArrayList<MyTypes> test = typeViewModel.getTypes(user.uid);
            //test.addOnListChangedCallback();


            Log.d(tag, "getTypes returned");
            for (MyTypes type : test) {
                Log.d(tag, "added: "+ type.getType());
                list.add(type.getType());
            }
            Log.d(tag, "all types added");
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
            //set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter);

        });




        return binding.getRoot();
    }
}