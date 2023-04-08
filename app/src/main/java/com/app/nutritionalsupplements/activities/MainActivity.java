package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!" + user.getEmail());
            Log.d(LOG_TAG, "User is anonymous: " + user.isAnonymous());
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
        }
    }

    public void openLoginActivity(View view) {
        /*if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }*/
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}