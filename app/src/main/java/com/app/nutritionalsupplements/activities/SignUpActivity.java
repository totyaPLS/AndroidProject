package com.app.nutritionalsupplements.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;

public class SignUpActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private static final String PREF_KEY = MainActivity.PACKAGE_NAME;

    EditText username;
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
    }

    public void register(View view) {

    }

    public void back(View view) {
        finish();
    }
}