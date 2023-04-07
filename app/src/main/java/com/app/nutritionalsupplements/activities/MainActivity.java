package com.app.nutritionalsupplements.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.app.nutritionalsupplements.R;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private static String PREF_KEY;
    private SharedPreferences preferences;
    EditText usernameET;
    EditText userPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", usernameET.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initializeData() {
        PREF_KEY = getApplicationContext().getPackageName();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        usernameET = findViewById(R.id.editTextTextPersonName);
        userPasswordET = findViewById(R.id.editTextTextPassword);
    }

    public void login(View view) {
        String userNameStr = usernameET.getText().toString();
        String userPasswordStr = userPasswordET.getText().toString();

        Log.i(LOG_TAG, "Logged in: " + userNameStr + ", password: " + userPasswordStr);
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        // TODO
        startActivity(intent);
    }
}