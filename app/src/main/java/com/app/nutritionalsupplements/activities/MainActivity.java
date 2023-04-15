package com.app.nutritionalsupplements.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.nutritionalsupplements.R;
import com.app.nutritionalsupplements.adapters.ProductAdapter;
import com.app.nutritionalsupplements.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseAuth auth;
    private FirebaseUser user;
    MenuItem loginItem;
    MenuItem logoutItem;
    private ArrayList<Product> mItemList;
    private ProductAdapter mAdapter;
    private static final int GRID_NUMBER = 1;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        Log.e(LOG_TAG, "OnCreate has run...");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(LOG_TAG, "onStart has run...");
        initializeData(); // initialize onStart because it changes after the login
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(LOG_TAG, "onResume has run...");
        invalidateOptionsMenu(); // force the system to call onPrepareOptionsMenu() again
    }

    @Override
    protected void onPause() {
        Log.e(LOG_TAG, "onPause has run...");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.signOut();
        Log.e(LOG_TAG, "onDestroy has run...");
        Log.e(LOG_TAG, "Logged out successfully!");
    }

    private void initializeData() {
        Log.e(LOG_TAG, "_____INITIALIZE DATA METHOD CALLED_____");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_NUMBER));
        mItemList = new ArrayList<>();
        mAdapter = new ProductAdapter(this, mItemList, checkLoginStatus());
        mRecyclerView.setAdapter(mAdapter);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Products");
        queryData();
        uploadProductsIfCollectionIsEmpty();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void queryData() {
        mItemList.clear();

        mItems.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots) {
                Product product = document.toObject(Product.class);
                mItemList.add(product);
            }

            if (mItemList.size() == 0) queryData();

            mAdapter.notifyDataSetChanged();
        });
    }

    public void uploadProductsIfCollectionIsEmpty() {
        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("Products");

        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() == 0) {
                    uploadProductsToDatabase();
                }
            } else {
                Log.e(LOG_TAG, "Error getting collection: " + task.getException());
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void uploadProductsToDatabase() {
        @SuppressLint("Recycle") TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.product_images);
        String[] itemsList = getResources().getStringArray(R.array.product_names);
        @SuppressLint("Recycle") TypedArray itemsRate = getResources().obtainTypedArray(R.array.product_rates);
        String[] itemsPrice = getResources().getStringArray(R.array.product_prices);

        for (int i = 0; i < itemsList.length; i++) {
            mItems.add(new Product(
                    itemsImageResource.getResourceId(i, 0),
                    itemsList[i],
                    itemsRate.getFloat(i, 0),
                    itemsPrice[i] + " Ft"));
        }
        itemsImageResource.recycle();
    }

    public void openLoginActivity() {
        Log.e(LOG_TAG, "openLoginActivity has run...");
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void logout() {
        Log.e(LOG_TAG, "logout has run...");
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkLoginStatus() {
        Log.e(LOG_TAG, "checkLoginStatus has run...");
        if (user == null) {
            Log.e(LOG_TAG, "Null user detected!");
            return false;
        }
        Log.e(LOG_TAG, "Authenticated user detected!" + user.getEmail());
        return true;
    }

//    Setting up Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(LOG_TAG, "onCreateOptionsMenu has run...");
        getMenuInflater().inflate(R.menu.shop_list_menu, menu);
        loginItem = menu.findItem(R.id.login);
        logoutItem = menu.findItem(R.id.logout);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                openLoginActivity();
                Log.e(LOG_TAG, "Login clicked!");
                return true;
            case R.id.logout:
                logout();
                Log.e(LOG_TAG, "Logout clicked!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e(LOG_TAG, "onPrepareOptionsMenu has run...");
        loginItem = menu.findItem(R.id.login);
        logoutItem = menu.findItem(R.id.logout);

        setLoginItemsVisibility();

        return super.onPrepareOptionsMenu(menu);
    }

    private void setLoginItemsVisibility() {
        Log.e(LOG_TAG, "setLoginItemsVisibility has run...");
        boolean isLoggedIn = checkLoginStatus();

        // Set the visibility of the login and logout menu items
        loginItem.setVisible(!isLoggedIn);
        logoutItem.setVisible(isLoggedIn);
    }
}