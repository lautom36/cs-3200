package com.example.assn3.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assn3.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class UserViewModel extends ViewModel{
    FirebaseAuth auth;
    MutableLiveData<User> user = new MutableLiveData<>();

    public UserViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void signIn(String userName, String password){

    }

    public void signUp(String userName, String password){

    }

    public void logout(){

    }
}
