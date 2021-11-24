package com.example.cs3200finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cs3200finalproject.databinding.FragmentNewTransactionBinding;
import com.example.cs3200finalproject.databinding.FragmentSigninBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewTransactionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNewTransactionBinding binding = FragmentNewTransactionBinding.inflate(inflater, container, false);

        //get the spinner from the xml.
        Spinner dropdown = binding.TransactionType;
        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        return binding.getRoot();
    }
}