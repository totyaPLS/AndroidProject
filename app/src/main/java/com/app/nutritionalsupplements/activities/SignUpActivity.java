package com.app.nutritionalsupplements.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private static String PREF_KEY;
    private FirebaseAuth auth;

    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText passwordAgainET;
    EditText phoneNumberET;
    EditText postalAddressET;
    Spinner spinner;

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
        auth = FirebaseAuth.getInstance();

        usernameET = findViewById(R.id.username);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        passwordAgainET = findViewById(R.id.password_again);
        phoneNumberET = findViewById(R.id.mobile_num);
        postalAddressET = findViewById(R.id.postal_address);
        spinner = findViewById(R.id.occupation_spinner);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.occupation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void autofillFieldsFromLogin() {
        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        emailET.setText(email);
    }

    public void register(View view) {
        String usernameStr = usernameET.getText().toString();
        String emailStr = emailET.getText().toString();
        String passwordStr = passwordET.getText().toString();
        String passwordAgainStr = passwordAgainET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();
        String addressStr = postalAddressET.getText().toString();
        String occupation = spinner.getSelectedItem().toString();

        if (!passwordStr.equals(passwordAgainStr)) {
            Log.e(LOG_TAG, "The passwords do not match!");
            // TODO: disable Sign Up button
        }

        registerUserIntoFirebase(emailStr, passwordStr);
    }

    private void registerUserIntoFirebase(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(LOG_TAG, "User created successfully!");
                startShoppingAfterSuccessfulRegistration();
            } else {
                Log.e(LOG_TAG, "User wasn't created successfully :(");
                Toast.makeText(SignUpActivity.this,
                        "User wasn't created successfully: " + Objects.requireNonNull(task.getException()).getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Finishes all opened activities in the stack and opens a new shopping activity.
     */
    private void startShoppingAfterSuccessfulRegistration() {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //TODO
    }
}