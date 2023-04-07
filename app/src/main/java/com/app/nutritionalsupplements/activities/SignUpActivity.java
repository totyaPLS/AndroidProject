package com.app.nutritionalsupplements.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nutritionalsupplements.R;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = SignUpActivity.class.getName();
    private static String PREF_KEY;
    private SharedPreferences preferences;

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
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        usernameET.setText(username);
    }

    public void register(View view) {
        getTextFromFields();
    }

    private void getTextFromFields() {
        String usernameStr = usernameET.getText().toString();
        String emailStr = emailET.getText().toString();
        String passwordStr = passwordET.getText().toString();
        String passwordAgainStr = passwordAgainET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();
        String addressStr = postalAddressET.getText().toString();
        String occupation = spinner.getSelectedItem().toString();

        if (!passwordStr.equals(passwordAgainStr)) {
            Log.e(LOG_TAG, "The passwords do not match!");
        }

//        Log.i(LOG_TAG, usernameStr + "; " + passwordStr + "; " + phoneNumber + "; " + occupation);
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