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
    private static final int SECRET_KEY = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}