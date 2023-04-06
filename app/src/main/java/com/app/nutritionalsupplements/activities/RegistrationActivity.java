package com.app.nutritionalsupplements.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = MainActivity.PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secretKey != 99) {
            finish();
        }
    }
}