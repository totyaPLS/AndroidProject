package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        Log.e(LOG_TAG, "OnCreate has run...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeData();

        if (user == null) {
            Log.e(LOG_TAG, "Null user detected!");
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        } else {
            Log.e(LOG_TAG, "Authenticated user detected!" + user.getEmail());
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

    public void openLoginActivity(View view) {
        if (user == null) {
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