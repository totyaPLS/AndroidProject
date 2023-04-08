package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseAuth auth;
    private FirebaseUser user;

    Button loginButton;
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        setUserAsGuestByDefault();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user == null || user.isAnonymous()) {
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        } else {
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializeData() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void setUserAsGuestByDefault() {
        if (user == null) {
            auth.signInAnonymously().addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    user = auth.getCurrentUser();
                    Log.e(LOG_TAG, "Anonymous user logged in successfully!");
                } else {
                    Log.e(LOG_TAG, "Anonymous user didn't log in successfully :(");
                    Toast.makeText(MainActivity.this,
                            "Anonymous user didn't log in successfully: " + Objects.requireNonNull(task.getException()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else if (user.isAnonymous()){
            Log.e(LOG_TAG, "Authenticated Anonymous user!" + user.getEmail());
        } else {
            Log.e(LOG_TAG, "Authenticated user!" + user.getEmail());
        }
    }

    public void openLoginActivity(View view) {
        assert user != null;
        if (user.isAnonymous()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void logout(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.signOut();
        Log.e(LOG_TAG, "Logged out successfully!");
    }
}