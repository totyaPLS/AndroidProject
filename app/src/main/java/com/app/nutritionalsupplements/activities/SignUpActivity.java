package com.app.nutritionalsupplements.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;

public class SignUpActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private static final String PREF_KEY = MainActivity.PACKAGE_NAME;

    EditText username;
    EditText email;
    EditText password;
    EditText passwordAgain;
    EditText postalAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secretKey != 99) {
            finish();
        }

        initializeData();
    }

    private void initializeData() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.password_again);
        postalAddress = findViewById(R.id.postal_address);
    }

    public void register(View view) {
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordAgainStr = passwordAgain.getText().toString();
        String addressStr = postalAddress.getText().toString();

        if (!passwordStr.equals(passwordAgainStr)) {
            Log.e(LOG_TAG, "The passwords do not match!");
            return;
        }

        Log.i(LOG_TAG, usernameStr + "; " + emailStr + "; " + passwordStr + "; " + passwordAgainStr + "; " + addressStr);
    }

    public void back(View view) {
        finish();
    }
}