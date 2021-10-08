package com.example.assn3.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assn3.models.User;
import com.example.assn3.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class UserViewModel extends ViewModel{
    UserRepository repository;
    MutableLiveData<User> user = new MutableLiveData<>();
    MutableLiveData<Boolean> emailError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();


    public UserViewModel() {
        errorMessage.setValue("");
        repository = new UserRepository();
        repository.setAuthStateChangedListener(user -> {
            this.user.postValue(user);
        });
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void signIn(String email, String password) {
        errorMessage.postValue("");
        if (email == null || email.isEmpty()) {
            errorMessage.postValue("Email cannot be empty");
            return;
        }
        if (password == null || password.isEmpty()) {
            errorMessage.postValue("Password cannot be empty");
            return;
        }

        repository.signIn(
                email,
                password,
                e -> errorMessage.postValue(e.getMessage())
        );
    }

    public void signUp(String email, String password) {
        errorMessage.postValue("");
        if (email == null || email.isEmpty()) {
            errorMessage.postValue("Email cannot be empty");
            return;
        }
        if (password == null || password.isEmpty()) {
            errorMessage.postValue("Password cannot be empty");
            return;
        }
        repository.signUp(
                email,
                password,
                e -> errorMessage.postValue(e.getMessage())
        );
    }

    public void logout() {
        repository.logout();
    };
}