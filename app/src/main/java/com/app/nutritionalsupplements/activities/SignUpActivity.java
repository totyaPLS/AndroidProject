package com.app.nutritionalsupplements.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;

public class SignUpActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private static String PREF_KEY;
    private SharedPreferences preferences;

    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText passwordAgainET;
    EditText postalAddressET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secretKey != 99) {
            finish();
        }

        initializeData();

        if (PREF_KEY != null) {
            autofillFieldsFromLogin();
        }
    }

    private void initializeData() {
        PREF_KEY = getApplicationContext().getPackageName();

        usernameET = findViewById(R.id.username);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        passwordAgainET = findViewById(R.id.password_again);
        postalAddressET = findViewById(R.id.postal_address);
    }

    private void autofillFieldsFromLogin() {
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        usernameET.setText(username);
    }

    public void register(View view) {
        String usernameStr = usernameET.getText().toString();
        String emailStr = emailET.getText().toString();
        String passwordStr = passwordET.getText().toString();
        String passwordAgainStr = passwordAgainET.getText().toString();
        String addressStr = postalAddressET.getText().toString();

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