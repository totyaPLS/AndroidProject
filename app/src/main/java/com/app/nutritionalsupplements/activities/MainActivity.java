package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.app.nutritionalsupplements.R;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    public static String PACKAGE_NAME;
    EditText userName;
    EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        userName = findViewById(R.id.editTextTextPersonName);
        userPassword = findViewById(R.id.editTextTextPassword);
    }

    public void login(View view) {
        String userNameStr = userName.getText().toString();
        String userPasswordStr = userPassword.getText().toString();

        Log.i(LOG_TAG, "Logged in: " + userNameStr + ", password: " + userPasswordStr);
    }

    public void registration(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        // TODO
        startActivity(intent);
    }
}