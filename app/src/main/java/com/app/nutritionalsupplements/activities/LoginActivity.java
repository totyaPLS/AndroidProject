package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static String PREF_KEY;
    private FirebaseAuth auth;
    private SharedPreferences preferences;
    EditText usernameET;
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
        editor.putString("username", usernameET.getText().toString());
        editor.apply();
    }

    private void initializeData() {
        PREF_KEY = getApplicationContext().getPackageName();
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        usernameET = findViewById(R.id.editTextTextPersonName);
        userPasswordET = findViewById(R.id.editTextTextPassword);
    }

    public void login(View view) {
        String userNameStr = usernameET.getText().toString();
        String userPasswordStr = userPasswordET.getText().toString();

        auth.signInWithEmailAndPassword(userNameStr, userPasswordStr).addOnCompleteListener(this, task -> {
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
    }
}