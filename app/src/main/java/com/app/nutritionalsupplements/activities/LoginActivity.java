package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nutritionalsupplements.Functions;
import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static String PREF_KEY;
    private FirebaseAuth auth;
    private SharedPreferences preferences;
    EditText emailET;
    EditText userPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailET.getText().toString());
        editor.apply();
    }

    private void initializeData() {
        PREF_KEY = getApplicationContext().getPackageName();
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        emailET = findViewById(R.id.editTextTextPersonName);
        userPasswordET = findViewById(R.id.editTextTextPassword);
    }

    public void login(View view) {
        if (!Functions.deviceHasInternetConnection(this)) return;

        String emailStr = emailET.getText().toString();
        String userPasswordStr = userPasswordET.getText().toString();

        if (emailStr.equals("") || userPasswordStr.equals("")) {
            Toast.makeText(this, "Please fill every field!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(emailStr, userPasswordStr).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.e(LOG_TAG, "User logged in successfully!");
                finish();
            } else {
                Log.e(LOG_TAG, "User didn't log in successfully :(");
                Toast.makeText(LoginActivity.this,
                        "User didn't log in successfully: " + Objects.requireNonNull(task.getException()).getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
        finish(); // FIXME: it shouldn't finish here. It has to finish after the registration was successful
    }
}