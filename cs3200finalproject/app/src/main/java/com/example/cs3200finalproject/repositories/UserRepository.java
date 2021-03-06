package com.example.cs3200finalproject.repositories;

import android.util.Log;

import com.example.cs3200finalproject.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository {
    String tag = "Mylog UserRepository";
    FirebaseAuth auth;

    public static interface OnFailureListener {
        public void onFailure(Exception e);
    }

    public static interface OnAuthStateChanged {
        public void onAuthStateChanged(User user);
    }

    public UserRepository() {
        auth = FirebaseAuth.getInstance();
    }

    public void setAuthStateChangedListener(OnAuthStateChanged listener) {
        auth.addAuthStateListener(auth -> {
            listener.onAuthStateChanged(getCurrentUser());
        });
    }

    public User getCurrentUser() {
        User user = new User();
        FirebaseUser fbUser = auth.getCurrentUser();
        if (fbUser == null) return null;

        user.uid = fbUser.getUid();
        user.email = fbUser.getEmail();
        return user;
    }

    public void signIn(String email, String password, OnFailureListener onFailureListener ) {
        Log.d(tag, "signIn started");
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("UserRepository", "signin: no success");
                System.out.println(task.getException().getMessage());
                onFailureListener.onFailure(task.getException());
                // handle the error
            }
            else {
                Log.d("UserRepository", "signin: success");
            }
        });
    }

    public void signUp(String email, String password, OnFailureListener onFailureListener) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("UserRepository", "signup: no success");
                System.out.println(task.getException().getMessage());
                onFailureListener.onFailure(task.getException());
                // handle the error
            }
            else {
                Log.d("UserRepository", "signup: success");
            }

        });
    }

    public void logout() {
        auth.signOut();
    }
}
