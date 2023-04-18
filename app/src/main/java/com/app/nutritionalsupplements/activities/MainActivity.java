package com.app.nutritionalsupplements.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.app.nutritionalsupplements.Device;
import com.app.nutritionalsupplements.R;
import com.app.nutritionalsupplements.adapters.ProductAdapter;
import com.app.nutritionalsupplements.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    MenuItem searchBar;
    MenuItem loginItem;
    MenuItem logoutItem;
    private ArrayList<Product> mItemList;
    private ProductAdapter mAdapter;
    private static final int GRID_NUMBER = 1;
    private CollectionReference mItems;
    private int numberOfProducts = 20;

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
        if (!Device.hasInternetConnection(this)) return;
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
        Log.e(LOG_TAG, "onDestroy has run...");
        Log.e(LOG_TAG, "Logged out successfully!");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the configuration change is due to a screen orientation change
        if (newConfig.orientation != Configuration.ORIENTATION_PORTRAIT) {
            // Don't sign out the user
            return;
        }

        // Sign out the user
        mAuth.signOut();
    }

    private void initializeData() {
        Log.e(LOG_TAG, "_____INITIALIZE DATA METHOD CALLED_____");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_NUMBER));
        mItemList = new ArrayList<>();
        mAdapter = new ProductAdapter(this, mItemList, checkLoginStatus());
        mRecyclerView.setAdapter(mAdapter);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Products");
        readProducts();
        uploadProductsIfCollectionIsEmpty();
    }

    public void uploadProductsIfCollectionIsEmpty() {
        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("Products");

        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() == 0) {
                    createProducts();
                }
            } else {
                Log.e(LOG_TAG, "Error getting collection: " + task.getException());
            }
        });
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
        mAuth.signOut();
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

        searchBar = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterProducts(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // mAdapter.getFilter().filter(s);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            readProducts();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterProducts(String s) {
        setLimitNumDependingOnNetworkConnectionType();

        mItemList.clear();
        mItems
                .orderBy("nameInLowerCase")
                .startAt(s.toLowerCase())
                .endAt(s.toLowerCase() + "\uf8ff")
                .limit(numberOfProducts)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e(LOG_TAG, "queryDocumentSnapshots üres: " + queryDocumentSnapshots.isEmpty());

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Log.e(LOG_TAG, document.getId() + " => " + document.getData());
                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        mItemList.add(product);
                    }
                    mAdapter.notifyDataSetChanged();
                });
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

    private void setLimitNumDependingOnNetworkConnectionType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                this.numberOfProducts = 4;
            }
        }

    }

//    CRUD operations

    @SuppressLint("NotifyDataSetChanged")
    private void createProducts() {
        @SuppressLint("Recycle") TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.product_images);
        String[] itemsList = getResources().getStringArray(R.array.product_names);
        @SuppressLint("Recycle") TypedArray itemsRate = getResources().obtainTypedArray(R.array.product_rates);
        String[] itemsPrice = getResources().getStringArray(R.array.product_prices);

        for (int i = 0; i < itemsList.length; i++) {
            mItems.add(new Product(
                    itemsImageResource.getResourceId(i, 0),
                    itemsList[i],
                    itemsRate.getFloat(i, 0),
                    itemsPrice[i] + " Ft",
                    0));
        }
        itemsImageResource.recycle();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readProducts() {
        setLimitNumDependingOnNetworkConnectionType();

        mItemList.clear();
        mItems.orderBy("cartedCount", Query.Direction.DESCENDING).limit(this.numberOfProducts).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        mItemList.add(product);
                    }

                    if (mItemList.size() == 0) readProducts();

                    mAdapter.notifyDataSetChanged();
                });
    }

    public void updateProduct(@NonNull Product product) {
        mItems.document(product._getId()).update("cartedCount", product.getCartedCount() + 1).
                addOnFailureListener(failure ->
                        Toast.makeText(
                                this,
                                product._getId() + " product can't be updated!", Toast.LENGTH_SHORT).show()
                );
        readProducts();
    }

    public void deleteProduct(@NonNull Product product) {
        DocumentReference ref = mItems.document(product._getId());

        ref.delete().addOnSuccessListener(success ->
                        Log.d(LOG_TAG, "Successfully deleted product: " + product._getId()))
                .addOnFailureListener(failure ->
                        Toast.makeText(
                                this,
                                product._getId() + " product can't be deleted!", Toast.LENGTH_SHORT
                        ).show()
                );

        readProducts();
    }
}